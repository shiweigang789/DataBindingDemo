package com.duoduolicai360.databindingdemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swg on 2017/8/25.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    private String DIR_PATH;
    private static CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private Map<String, String> infos = new HashMap<>();
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler(){}

    public static CrashHandler getInstance(){
        if (mInstance == null){
            mInstance = new CrashHandler();
        }
        return mInstance;
    }

    public void init(Context context, String dirName){
        DIR_PATH = dirName;
        mContext = context;
        mDefaultHandler = new Thread().getUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handlerException(ex) && mDefaultHandler != null){
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                Log.e(TAG, "error : ", e);
            }
            System.exit(0);
            android.os.Process.killProcess(Process.myPid());
        }
    }

    private boolean handlerException(Throwable ex){
        if (ex == null){
            return false;
        }

        collectDeviceInfo(mContext);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();


                Looper.loop();
            }
        }).start();
        saveCatchInfo2File(ex);
        return true;
    }

    public void collectDeviceInfo(Context ctx){
        PackageManager pm = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null){
                infos.put("versionName", packageInfo.versionName != null ? packageInfo.versionName : null);
                infos.put("versionCode", packageInfo.versionCode + "");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "collectDeviceInfo: an error occured when collect packageInfo", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }

    }

    private String saveCatchInfo2File(Throwable ex){
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()){
            sb.append(entry.getKey() + "=" + entry.getValue());
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        sb.append(writer.toString());
        FileOutputStream fos = null;
        try {
            long timestamp = System.currentTimeMillis();
            String time = mDateFormat.format(timestamp);
            String fileName = "crash_" + time + "_" + timestamp + ".log";
            String savePath = getSavePath(mContext, DIR_PATH);
            fos = new FileOutputStream(new File(savePath, fileName));
            fos.write(sb.toString().getBytes());
            return fileName;
        } catch (Exception e){
            Log.e(TAG, "an error occured while writing file...", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getSavePath(Context context, String subDir){
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            file = Environment.getExternalStorageDirectory();
        } else {
            file = context.getFilesDir();
            return getPath(file, subDir);
        }
        return getPath(file, subDir);
    }

    private static String getPath(File f, String subDir) {
        File file = new File(f.getAbsolutePath() + File.separator + subDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public void sendCrashLog2PM(String fileName){
        if (!new File(fileName).exists()){
            return;
        }

        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            while (reader.readLine() != null){
                Log.i("info", reader.readLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
