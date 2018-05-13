package com.lichao.openglandroid.vary;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.lichao.openglandroid.BaseActivity;
import com.lichao.openglandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VaryActivity extends BaseActivity {

    private VaryRender render;

    @BindView(R.id.mGLView)
    GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vary);
        ButterKnife.bind(this);
        initGL();
    }

    private void initGL() {
        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(render = new VaryRender(getResources()));
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
