package com.lichao.openglandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lichao.openglandroid.render.FGLViewActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ArrayList<MenuBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        data = new ArrayList<>();
        add("绘制形体", FGLViewActivity.class);
        recyclerView.setAdapter(new MenuAdapter());
    }

    private void add(String name,Class<?> clazz) {
        MenuBean bean = new MenuBean();
        bean.name = name;
        bean.clazz = clazz;
        data.add(bean);
    }

    @Override
    public void onClick(View view) {
        int position= (int) view.getTag();
        MenuBean bean = data.get(position);
        startActivity(new Intent(this, bean.clazz));
    }

    private class MenuBean{
        String name;
        Class<?> clazz;
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

        @NonNull
        @Override
        public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MenuHolder(getLayoutInflater().inflate(R.layout.item_button, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
            holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MenuHolder extends RecyclerView.ViewHolder {
            private Button mBtn;

            MenuHolder(View itemView) {
                super(itemView);
                mBtn = itemView.findViewById(R.id.mBtn);
                mBtn.setOnClickListener(MainActivity.this);
            }

            void setPosition(int position){
                MenuBean bean=data.get(position);
                mBtn.setText(bean.name);
                mBtn.setTag(position);
            }
        }
    }
}
