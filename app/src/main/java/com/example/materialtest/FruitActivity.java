package com.example.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivity extends AppCompatActivity {
    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_IMAGE_ID="fruit_image_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        //获取另一个活动传递过来的数据
        Intent intent=getIntent();
        String fruitName=intent.getStringExtra(FRUIT_NAME);
        int fruitImageId=intent.getIntExtra(FRUIT_IMAGE_ID,0);

        //初始化一个标题栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);

        //初始化标题栏中的控件
        ImageView fruitImageView=(ImageView)findViewById(R.id.fruit_image_view);
        TextView fruitContentText=(TextView)findViewById(R.id.fruit_content_text);

        //设置一个Toolbar作为这个活动窗口的标题栏，并继承ActionBar所有的功能。
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            //设置显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置CollapsingToolbarLayout 高级Toolbar的标题
        collapsingToolbar.setTitle(fruitName);
        //使用第三方开源库 Glide加载图片
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        //设置具体内容
        String fruitContent=generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);

    }
    /**
     * 获取具体的内容
     */
    private String generateFruitContent(String fruitName){
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < 500; i++) {
            builder.append(fruitName);
        }
        return builder.toString();
    }

    /**
     * 设置菜单的按钮
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context,String name,int image){
        Intent intent =new Intent(context,FruitActivity.class);
        intent.putExtra(FRUIT_NAME,name);
        intent.putExtra(FRUIT_IMAGE_ID,image);
        context.startActivity(intent);

    }
}
