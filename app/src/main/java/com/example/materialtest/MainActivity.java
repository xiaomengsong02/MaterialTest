package com.example.materialtest;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private Fruit[] fruits={new Fruit("未知1",R.drawable.e1),new Fruit("未知2",R.drawable.e5),
            new Fruit("未知3",R.drawable.e6),new Fruit("未知4",R.drawable.aa2),
            new Fruit("未知5",R.drawable.aa1),new Fruit("未知6",R.drawable.aa3),
            new Fruit("未知7",R.drawable.aa4),new Fruit("未知8",R.drawable.aa5),
            new Fruit("未知9",R.drawable.aa6),new Fruit("未知10",R.drawable.aa7)};

    private List<Fruit> fruitList=new ArrayList<>();
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        //设置一个工具条作为这个活动窗口的标题栏，并继承ActionBar所有的功能。
        setSupportActionBar(toolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navView=(NavigationView)findViewById(R.id.nav_view);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            //显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮的图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //设置应用于菜单项图标的颜色。传递null表示不设置颜色，显示图片本身的颜色
        navView.setItemIconTintList(null);

        //指定默认选项
       // navView.setCheckedItem(R.id.nav_friends);
        //设置视图的背景颜色
        //navView.setBackgroundColor(Color.BLUE);
        /*int size=navView.getMenu().size();
        for(int i=0;i<size;i++){
            navView.getMenu().getItem(i).setCheckable(false);
        }*/
        //设置Item的背景颜色
        //navView.setItemBackgroundResource(R.drawable.strawberry);
        //navView.setItemBackground()

        //设置NavigationView控件里面的点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              switch (item.getItemId()){
                  case R.id.nav_task:
                      item.setChecked(true);
                      mDrawerLayout.closeDrawers();//关闭滑动菜单
                      break;
                  default:
                      break;
              }
                return true;
            }
        });

        //设置侧拉菜单的滑动回调事件
        // onDrawerSlide()会被调用很多次，不知道为什么
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d("侧拉菜单", "onDrawerSlide: 111111 ");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d("侧拉菜单", "onDrawerSlide: 打开滑动菜单会回调该方法 ");
                int size=navView.getMenu().size();
                for(int i=0;i<size;i++){
                    navView.getMenu().getItem(i).setCheckable(true);
                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("侧拉菜单", "onDrawerSlide: 关闭滑动菜单会回调该方法 ");
               // navView.setCheckedItem(R.id.nav_call);
                int size=navView.getMenu().size();
                for(int i=0;i<size;i++){
                    navView.getMenu().getItem(i).setChecked(false);
                    navView.getMenu().getItem(i).setCheckable(false);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d("侧拉菜单", "onDrawerSlide: 状态改变了 ");

            }
        });

        //获取悬浮按钮的id
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        //不设置按钮的背景颜色？
        fab.setBackgroundTintList(null);
        //fab.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        //设置悬浮按钮的点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo",new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Data restored",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        initFruits();//初始化数据
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        //声明一个网格布局(第一个参数是Context 第二个参数是列数)
        GridLayoutManager manager=new GridLayoutManager(this,2);
        //把网格布局设置到滚动控件中
        recyclerView.setLayoutManager(manager);
        //实例化一个适配器
        adapter=new FruitAdapter(fruitList);
        //把适配器和滚动控件相关联
        recyclerView.setAdapter(adapter);

        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        //设置下拉刷新进度条颜色
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        //设置下拉刷新的监听器
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();//执行本地刷新操作
            }
        });

    }

    /**
     * 执行本地刷新操作
     */
    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //切换到UI线程中进行UI更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();//初始化水果资源
                        //通知数据发送了改变
                        adapter.notifyDataSetChanged();
                        //表示刷新结束，并隐藏刷新进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 初始化水果资源
     */
    private void initFruits(){
        fruitList.clear();
        Random random=new Random();
        for (int i = 0; i < 50; i++) {
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单视图
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //菜单子项的点击事件
        switch (item.getItemId()){

            case android.R.id.home:
                //设置导航按钮的点击事件
                //该方法为 将滑动菜单显示出来
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;

    }
}
