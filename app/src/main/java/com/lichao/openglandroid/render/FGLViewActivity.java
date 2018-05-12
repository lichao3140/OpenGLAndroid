package com.lichao.openglandroid.render;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lichao.openglandroid.ChooseActivity;
import com.lichao.openglandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绘制形体
 */
public class FGLViewActivity extends AppCompatActivity {

    private static final int REQ_CHOOSE = 0x0101;
    @BindView(R.id.mChange)
    Button mChange;
    @BindView(R.id.mGLView)
    FGLView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fglview);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.mChange)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mChange:
                Intent intent=new Intent(this, ChooseActivity.class);
                startActivityForResult(intent, REQ_CHOOSE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            mGLView.setShape((Class<? extends Shape>) data.getSerializableExtra("name"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }
}
