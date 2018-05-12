package com.lichao.openglandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lichao.openglandroid.render.Ball;
import com.lichao.openglandroid.render.BallWithLight;
import com.lichao.openglandroid.render.Cone;
import com.lichao.openglandroid.render.Cube;
import com.lichao.openglandroid.render.Cylinder;
import com.lichao.openglandroid.render.Oval;
import com.lichao.openglandroid.render.Square;
import com.lichao.openglandroid.render.Triangle;
import com.lichao.openglandroid.render.TriangleColorFull;
import com.lichao.openglandroid.render.TriangleWithCamera;

import java.util.ArrayList;

public class ChooseActivity extends AppCompatActivity {

    private ChooseActivity context;
    private ListView mList;
    private ArrayList<Data> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_choose);
        init();
    }

    private void init() {
        initData();
        mList = findViewById(R.id.mList);
        mList.setAdapter(new Adapter());
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("name", mData.get(position).clazz);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        add("三角形", Triangle.class);
        add("正三角形", TriangleWithCamera.class);
        add("彩色三角形", TriangleColorFull.class);
        add("正方形", Square.class);
        add("圆形", Oval.class);
        add("正方体", Cube.class);
        add("圆锥", Cone.class);
        add("圆柱", Cylinder.class);
        add("球体", Ball.class);
        add("带光源的球体", BallWithLight.class);
    }

    private void add(String showName,Class<?> clazz){
        Data data = new Data();
        data.clazz = clazz;
        data.showName = showName;
        mData.add(data);
    }

    private class Data{
        String showName;
        Class<?> clazz;
    }

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_choose, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.setData(mData.get(position));
            return convertView;
        }

        private class ViewHolder{
            private TextView mName;

            ViewHolder(View parent) {
                mName= parent.findViewById(R.id.mName);
            }

            public void setData(Data data) {
                mName.setText(data.showName);
            }
        }
    }

}
