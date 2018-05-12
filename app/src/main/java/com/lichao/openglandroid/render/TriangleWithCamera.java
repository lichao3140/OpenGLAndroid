package com.lichao.openglandroid.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ChaoLi on 2018/5/12 0012 - 11:38
 * Email: lichao3140@gmail.com
 * Version: v1.0  等腰直角三角形
 */
public class TriangleWithCamera extends Shape {

    private FloatBuffer vertexBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandler;

    // 顶点着色器  gl_Position Shader的内置变量定点位置
    //修改顶点着色器，增加矩阵变换
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;"+
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "}";

    // 片元着色器  gl_FragColor Shader的内置变量片元颜色
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    static final int COORDS_PER_VERTEX = 3;
    // 三角形的坐标  因为是2D所以Z轴方向都为0
    static float triangleCoords[] = {
            0.5f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节
    //设置颜色，依次为红绿蓝和透明通道
    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    TriangleWithCamera(View mView) {
        super(mView);
        // 申请底层空间
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        //将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);
        // 顶点着色器
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        // 片元着色器
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置相机和投影，获取相机矩阵和投影矩阵，然后用相机矩阵与投影矩阵相乘，得到实际变换矩阵
        //计算宽高比
        float ratio=(float)width / height;
        //正交投影，物体呈现出来的大小不会随着其距离视点的远近而发生变化
        //透视投影,物体离视点越远，呈现出来的越小。离视点越近，呈现出来的越大
        Matrix.frustumM(mProjectMatrix,  //接收透视投影的变换矩阵
                0,                 //变换矩阵的起始位置（偏移量）
                -ratio,                  //相对观察点近面的左边距
                ratio,                   //相对观察点近面的右边距
                -1,               //相对观察点近面的下边距
                1,                   //相对观察点近面的上边距
                3,                  //相对观察点近面距离
                7);                  //相对观察点远面距离
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix,  //接收相机变换矩阵
                0,              //变换矩阵的起始位置（偏移量）
                0, 0, 7.0f, //相机位置
                0f, 0f, 0f,  //观测点位置
                0f, 1.0f, 0.0f); //up向量在xyz上的分量
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,  //接收相乘结果
                0,     //接收矩阵的起始位置（偏移量）
                mProjectMatrix,  //左矩阵
                0,       //左矩阵的起始位置（偏移量）
                mViewMatrix,     //右矩阵
                0);     //右矩阵的起始位置（偏移量）
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //将变换矩阵传入顶点着色器
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);
        //获取变换矩阵vMatrix成员句柄
        mMatrixHandler= GLES20.glGetUniformLocation(mProgram, "vMatrix");
        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0);
        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制方式有
        //int GL_POINTS       //将传入的顶点坐标作为单独的点绘制
        //int GL_LINES        //将传入的坐标作为单独线条绘制，ABCDEFG六个顶点，绘制AB、CD、EF三条线
        //int GL_LINE_STRIP   //将传入的顶点作为折线绘制，ABCD四个顶点，绘制AB、BC、CD三条线
        //int GL_LINE_LOOP    //将传入的顶点作为闭合折线绘制，ABCD四个顶点，绘制AB、BC、CD、DA四条线。
        //int GL_TRIANGLES    //将传入的顶点作为单独的三角形绘制，ABCDEF绘制ABC,DEF两个三角形
        //int GL_TRIANGLE_FAN    //将传入的顶点作为扇面绘制，ABCDEF绘制ABC、ACD、ADE、AEF四个三角形
        //int GL_TRIANGLE_STRIP   //将传入的顶点作为三角条带绘制，ABCDEF绘制ABC,BCD,CDE,DEF四个三角形
        //顶点法绘制GLES20.glDrawArrays：根据传入的定点顺序进行绘制的
        //索引法GLES20.glDrawElements：根据索引序列，在顶点序列中找到对应的顶点，并根据绘制的方式，组成相应的图元进行绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, //表示绘制方式
                0,  //表示偏移量
                vertexCount);  //表示顶点个数
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
