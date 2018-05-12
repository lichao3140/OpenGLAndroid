package com.lichao.openglandroid.image;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lichao.openglandroid.BaseActivity;
import com.lichao.openglandroid.R;
import com.lichao.openglandroid.image.filter.ColorFilter;
import com.lichao.openglandroid.image.filter.ContrastColorFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片处理
 */
public class SGLViewActivity extends BaseActivity {

    private boolean isHalf = false;
    @BindView(R.id.glView)
    SGLView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sglview);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mDeal:
                isHalf = !isHalf;
                if (isHalf) {
                    item.setTitle("处理一半");
                }else{
                    item.setTitle("全部处理");
                }
                glView.getRender().refresh();
                break;
            case R.id.mDefault: // 原图
                glView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.NONE));
                break;
            case R.id.mGray:  // 黑白
                glView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.GRAY));
                break;
            case R.id.mCool:  // 冷色调
                glView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.COOL));
                break;
            case R.id.mWarm:  // 暖色调
                glView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.WARM));
                break;
            case R.id.mBlur:  // 模糊
                glView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.BLUR));
                break;
            case R.id.mMagn:  // 放大镜
                glView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.MAGN));
                break;
        }
        glView.getRender().getFilter().setHalf(isHalf);
        glView.requestRender();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

}
