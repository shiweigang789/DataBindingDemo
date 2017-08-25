package com.duoduolicai360.databindingdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.duoduolicai360.databindingdemo.databinding.ActivityMainBinding;
import com.duoduolicai360.databindingdemo.ui.FontBindingActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMMainBinding.setPresenter(new Presenter());
    }

    public class Presenter {
        public void onClick(View view){
            switch (view.getId()){
                case R.id.btn_font:
                    startActivity(new Intent(MainActivity.this, FontBindingActivity.class));
                    break;
            }
        }
    }



}
