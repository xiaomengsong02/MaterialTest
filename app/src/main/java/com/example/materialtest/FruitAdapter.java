package com.example.materialtest;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view){
            super(view);
            //初始化子布局中的控件
            cardView=(CardView)view;
            fruitImage=(ImageView)view.findViewById(R.id.fruit_image);
            fruitName=(TextView) view.findViewById(R.id.fruit_name);

        }
    }
    public FruitAdapter(List<Fruit> fruitList){
        mFruitList=fruitList;

    }
    @Override//加载布局文件并返回ViewHolder对象
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        //加载布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前点击的位置
                int position=holder.getAdapterPosition();
                //根据当前点击的位置返回水果对象
                Fruit fruit = mFruitList.get(position);
                //跳转到显示水果详细的活动中, 该活动提供了一个跳转的静态方法
                FruitActivity.actionStart(mContext,fruit.getName(),fruit.getImageId());

            }
        });

        return holder;
    }

    @Override//给控件赋值
    public void onBindViewHolder(ViewHolder holder, int position) {
        //根据position获取对应的Fruit对象
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        //使用Glide加载图片
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override//获取有多少数据
    public int getItemCount() {
        return mFruitList.size();
    }



}
