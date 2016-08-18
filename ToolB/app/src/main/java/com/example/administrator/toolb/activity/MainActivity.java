package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.fragment.FragmentHot;
import com.example.administrator.toolb.fragment.FragmentNews;
import com.example.administrator.toolb.fragment.FragmentSearch;
import com.example.administrator.toolb.utils.SpfManager;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_ziXun,iv_Hot,iv_search;
    private TextView tv_ziXun,tv_Hot,tv_search;
    private RelativeLayout rl_ziXun,rl_Hot,rl_search;
    private LinearLayout ll_login,ll_about,ll_clloect,ll_setting;
    private LinearLayout ll_content;
    private SpfManager spfManager;
    private String place=" ";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentNews fragmentNews;
    private FragmentHot fragmentHot;
    private FragmentSearch fragmentSearch;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    //这是抽屉和toolbar的开关
    private ActionBarDrawerToggle actionBarDrawerToggle;
    //将点击切换图标和文字的图片声明
    //选中 和  没有选中的
    int[] imgCheck={R.drawable.new_selected,R.drawable.collect_selected,R.drawable.find_selected};
    int[] imgNormal={R.drawable.new_unselected,R.drawable.collect_unselected,R.drawable.find_defult};
    //定义两个数组用来存放选择图片和文字
    private ImageView[] imageViews=new ImageView[3];
    private TextView[] title=new TextView[3];
    //用来和选中的下标进行比较的
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (place.equals(" ")) {
            setToolBar();
        }
        ShareSDK.initSDK(this);
        spfManager=new SpfManager(this);
        fragmentManager=getSupportFragmentManager();
        //初始化所有控件
        iv_ziXun= (ImageView) findViewById(R.id.iv_ziXun);
        iv_Hot= (ImageView) findViewById(R.id.iv_Hot);
        iv_search= (ImageView) findViewById(R.id.iv_search);

        tv_ziXun= (TextView) findViewById(R.id.tv_ziXun);
        tv_Hot= (TextView) findViewById(R.id.tv_Hot);
        tv_search= (TextView) findViewById(R.id.tv_search);

        rl_ziXun= (RelativeLayout) findViewById(R.id.rl_ziXun);
        rl_Hot= (RelativeLayout) findViewById(R.id.rl_Hot);
        rl_search= (RelativeLayout) findViewById(R.id.rl_search);

        ll_content= (LinearLayout) findViewById(R.id.ll_content);
        ll_about= (LinearLayout) findViewById(R.id.ll_about);
        ll_clloect= (LinearLayout) findViewById(R.id.ll_collect);
        ll_setting= (LinearLayout) findViewById(R.id.ll_setting);
        ll_login= (LinearLayout) findViewById(R.id.ll_login);

        ll_clloect.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_login.setOnClickListener(this);

        rl_ziXun.setOnClickListener(this);
        rl_Hot.setOnClickListener(this);
        rl_search.setOnClickListener(this);

        //这个图片数组 为  布局文件里的imageView的默认没有选中的图片
        imageViews[0]=iv_ziXun;
        imageViews[1]=iv_Hot;
        imageViews[2]=iv_search;

        //这个文字为 布局文件里TextView的默认没有颜色的文字哦
        title[0]=tv_ziXun;
        title[1]=tv_Hot;
        title[2]=tv_search;


        //注意不要忘了实例化啊！
        fragmentNews=new FragmentNews();
        fragmentHot=new FragmentHot();
        fragmentSearch=new FragmentSearch();

        //将3个fragment装入在主界面中   通过事务来执行
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_content,fragmentNews).add(R.id.ll_content,fragmentHot).add(R.id.ll_content,fragmentSearch);
        //切换的时候通过显示隐藏来实现
        fragmentTransaction.show(fragmentNews).hide(fragmentHot).hide(fragmentSearch);
        iv_ziXun.setImageResource(R.drawable.new_selected);
        tv_ziXun.setTextColor(android.graphics.Color.parseColor("#48D1CC"));
        //当事务完成后 记得提交！
        fragmentTransaction.commit();

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        //同步状态
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    //这里是点击事件  点击显示 隐藏 fragment 并且设置图片和文字的颜色
    @Override
    public void onClick(View v) {
        fragmentTransaction=fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.rl_ziXun:
                fragmentTransaction.show(fragmentNews).hide(fragmentHot).hide(fragmentSearch);
                position=0;
                break;
            case R.id.rl_Hot:
                fragmentTransaction.show(fragmentHot).hide(fragmentNews).hide(fragmentSearch);
                position=1;
                break;
            case R.id.rl_search:
                fragmentTransaction.show(fragmentSearch).hide(fragmentHot).hide(fragmentNews);
                position=2;
                break;

            case R.id.ll_collect:
                startActivity(CollectActivity.class);
                break;
            case R.id.ll_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.ll_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.ll_login:
                Toast.makeText(MainActivity.this, "欢迎使用第三方登录！", Toast.LENGTH_SHORT).show();
                showShare();
                break;
        }
        Log.i("msg", "位置是多少？"+position);
        setCheck(position);
        fragmentTransaction.commit();
    }


    //提供一个改变图片和文字颜色方法
    public void setCheck(int position){
        for (int i = 0; i < title.length; i++) {
            if (i==position){
                imageViews[i].setImageResource(imgCheck[i]);
                title[i].setTextColor(android.graphics.Color.parseColor("#48D1CC"));
            }
            else {
                imageViews[i].setImageResource(imgNormal[i]);
                title[i].setTextColor(android.graphics.Color.parseColor("#242424"));
            }
        }
    }

    public void startActivity(Class<?> c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JeColors colord = spfManager.getColord();
        place = colord.getPlace();
        if (!place.equals(" ")) {
            toolbar.setBackgroundColor(Color.parseColor(place));
        }
    }

    public void setToolBar(){
        toolbar= (Toolbar) findViewById(R.id.toolBar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar.setTitle(R.string.app_name);
//        toolbar.setSubtitle("看见好时光");
        toolbar.setTitleTextColor(android.graphics.Color.parseColor("#ffffff"));

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.one:
                        Toast.makeText(MainActivity.this, "哈哈，点我分享！", Toast.LENGTH_SHORT).show();
                        showShare();
                        break;
                }
                return true;
            }
        });
    }
}
