package com.lichao.openglandroid.image.filter;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by ChaoLi on 2018/5/12 0012 - 20:48
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class ContrastColorFilter extends AFilter {

    private ColorFilter.Filter filter;

    private int hChangeType;
    private int hChangeColor;

    public ContrastColorFilter(Context context, ColorFilter.Filter filter) {
        super(context, "filter/half_color_vertex.sh", "filter/half_color_fragment.sh");
        this.filter = filter;
    }

    @Override
    public void onDrawSet() {
        GLES20.glUniform1i(hChangeType, filter.getType());
        GLES20.glUniform3fv(hChangeColor, 1, filter.data(), 0);
    }

    @Override
    public void onDrawCreatedSet(int mProgram) {
        hChangeType = GLES20.glGetUniformLocation(mProgram, "vChangeType");
        hChangeColor = GLES20.glGetUniformLocation(mProgram, "vChangeColor");
    }
}
