package com.duoduolicai360.databindingdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.duoduolicai360.databindingdemo.databinding.ActivityMainBinding;
import com.duoduolicai360.databindingdemo.ui.BaseActivity;
import com.duoduolicai360.databindingdemo.ui.ConverterActivity;
import com.duoduolicai360.databindingdemo.ui.CustomSetterActivity;
import com.duoduolicai360.databindingdemo.ui.DataBindSimpleActivity;
import com.duoduolicai360.databindingdemo.ui.ELActivity;
import com.duoduolicai360.databindingdemo.ui.FontBindingActivity;
import com.duoduolicai360.databindingdemo.ui.GitHubContributorsActivity;
import com.duoduolicai360.databindingdemo.ui.SearchDebounceActivity;
import com.duoduolicai360.databindingdemo.ui.UpdateUserActivity;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mMMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMMainBinding.setPresenter(new Presenter());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

    }

    public class Presenter {
        public void onClick(View view){
            switch (view.getId()){
                case R.id.btn_font:
                    launchActivity(FontBindingActivity.class);
                    break;
                case R.id.btn_simple:
                    launchActivity(DataBindSimpleActivity.class);
                    break;
                case R.id.btn_observable:
                    launchActivity(UpdateUserActivity.class);
                    break;
                case R.id.btn_list:
                    launchActivity(GitHubContributorsActivity.class);
                    break;
                case R.id.btn_el:
                    launchActivity(ELActivity.class);
                    break;
                case R.id.btn_converter:
                    launchActivity(ConverterActivity.class);
                    break;
                case R.id.btn_custom_setter:
                    launchActivity(CustomSetterActivity.class);
                    break;
                case R.id.btn_search:
                    launchActivity(SearchDebounceActivity.class);
                    break;

            }
        }
    }

}
