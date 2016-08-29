package com.selfassu.quickindexbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.selfassu.quickindexbar.adapter.MyAdapter;
import com.selfassu.quickindexbar.bean.Friend;
import com.selfassu.quickindexbar.view.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private QuickIndexBar mIndexBar;
    private ListView mListView;
    private TextView mCurrentWord;
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIndexBar = (QuickIndexBar) findViewById(R.id.qid_index_bar);
        mListView = (ListView) findViewById(R.id.list_view);
        mCurrentWord = (TextView) findViewById(R.id.currentWord);
        //1.准备数据
        fillList();
        //2.对数据进行排序
        Collections.sort(friends);
        //3.listView设置Adapter
        mListView.setAdapter(new MyAdapter(this, friends));
        mIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                for (int i = 0; i < friends.size(); i++)
                {
                    String firstWord = friends.get(i).getPinyin().charAt(0) + "";
                    if(letter.equals(firstWord))
                    {
                        //说明找到了，那么应该讲当前的item放到屏幕顶端
                        mListView.setSelection(i);
                        break;//只需要找到第一个就行
                    }
                }
                //显示当前触摸的字母
                showCurrentWord(letter);
            }
        });
    }


    private boolean isScale = false;
    private Handler mHandler = new Handler();

    private void showCurrentWord(String letter) {
        mCurrentWord.setVisibility(View.VISIBLE);
        mCurrentWord.setText(letter);
        if (!isScale) {
            isScale = true;
            ViewPropertyAnimator.animate(mCurrentWord).scaleX(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
            ViewPropertyAnimator.animate(mCurrentWord).scaleY(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();

            //先移除之前的任务
            mHandler.removeCallbacksAndMessages(null);

            //延时隐藏currentWord
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCurrentWord.setVisibility(View.INVISIBLE);
                    ViewPropertyAnimator.animate(mCurrentWord).scaleX(0f).setDuration(450).start();
                    ViewPropertyAnimator.animate(mCurrentWord).scaleY(0f).setDuration(450).start();
                    isScale = false;
                }
            }, 1500);
        }
    }




    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("林志颖"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("陈小春"));
        friends.add(new Friend("陈龙"));
        friends.add(new Friend("陈传奇"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("林志玲"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("王熙凤"));
        friends.add(new Friend("薛仁贵"));
        friends.add(new Friend("郑爽"));
        friends.add(new Friend("杨洋"));
        friends.add(new Friend("张一山"));
        friends.add(new Friend("赵文卓"));
        friends.add(new Friend("霍建华"));
        friends.add(new Friend("霍思燕"));
        friends.add(new Friend("霍元甲"));
        friends.add(new Friend("巫启贤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨颖"));
        friends.add(new Friend("刘诗诗"));
        friends.add(new Friend("赵丽颖"));
        friends.add(new Friend("蒋欣"));
        friends.add(new Friend("张卫健"));
        friends.add(new Friend("大s"));
        friends.add(new Friend("SHE"));
    }
}
