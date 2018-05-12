package com.lichao.openglandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ChaoLi on 2018/5/12 0012 - 9:22
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("OpenGL Android");
    }
}
