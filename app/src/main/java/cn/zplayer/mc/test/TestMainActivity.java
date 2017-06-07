package cn.zplayer.mc.test;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.zplayer.mc.BaseActivity;
import cn.zplayer.mc.R;

public class TestMainActivity extends BaseActivity {


    public static Intent getIntent(Activity act){
        return new Intent(act,TestMainActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_main;
    }

    public void toTestKotLin(View view) {
        startActivity(new Intent(this,KotLinTest1Activity.class));
    }
}
