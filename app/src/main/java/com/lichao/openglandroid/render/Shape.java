package com.lichao.openglandroid.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.View;

/**
 * Created by ChaoLi on 2018/5/12 0012 - 9:57
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public abstract class Shape implements GLSurfaceView.Renderer {
    protected View mView;

    Shape(View mView){
        this.mView=mView;
    }

    /**
     * 加载顶点和片元着色器
     * @param type  顶点或片元着色器
     * @param shaderCode 资源
     * @return
     */
    public int loadShader(int type, String shaderCode) {
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
