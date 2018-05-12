package com.lichao.openglandroid.image;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.lichao.openglandroid.image.filter.AFilter;

import java.io.IOException;

/**
 * Created by ChaoLi on 2018/5/12 0012 - 19:02
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class SGLView extends GLSurfaceView {

    private SGLRender render;

    public SGLView(Context context) {
        this(context, null);
    }

    public SGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        render = new SGLRender(this);
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        try {
            // 加载图片资源
            render.setImage(BitmapFactory.decodeStream(getResources().getAssets().open("texture/fengj.png")));
            requestRender();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SGLRender getRender() {
        return render;
    }

    public void setFilter(AFilter filter){
        render.setFilter(filter);
    }
}
