package com.selfassu.quickindexbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by selfassu on 2016/7/26.
 * FileName:
 * FileDesc:
 * SystemTime: 9:07
 * Author: selfassu
 * Email: selfassu@gmail.com
 */
public class QuickIndexBar extends View
{

    private Paint mPaint;
    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    private int mWidth;
    private float mCellHeight;

    public QuickIndexBar(Context context) {
        super(context);
        initView();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 当View的宽度发生改变的时候调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mCellHeight = (getMeasuredHeight() * 1f)/indexArr.length;  //每个格子的高度
    }

    private void initView()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = mWidth / 2;
        for(int i = 0; i < indexArr.length; i++) {
            //获取文本高度
          float y = mCellHeight / 2 + getTextHeight(indexArr[i]) / 2 + i * mCellHeight;
            mPaint.setColor(mLastIndex==i?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i], x, y, mPaint);
        }
    }


    /**
     * 将文本封装成一个长方形
     * @param text
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    private int mLastIndex = -1;//记录上一次的索引
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            //因为ACTION_DOWN 和 ACTION_MOVE是一样的触发条件，所以我们可以选择去掉一个break
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / mCellHeight);//得到字母对应的索引
                if(mLastIndex != index)
                {
//                    Log.d("QuickIndexBar", indexArr[index]);
                    //说明当前触摸字母不是同一个字母；

                    //对index做接茬安全性检查
                    if(index >= 0 && index < indexArr.length)
                    {
                        if(mListener != null)
                        {
                            mListener.onTouchLetter(indexArr[index]);
                        }
                    }
                }
                mLastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                //重置mLastIndex索引位
                mLastIndex = -1;
                break;

            default:
                break;
        }
        //引起界面重绘
        invalidate();
        return true;
    }

    private OnTouchLetterListener mListener;
    public void setOnTouchLetterListener(OnTouchLetterListener mListener)
    {
        this.mListener = mListener;
    }

    /**
     * 触摸字母的监听器
     */
    public interface OnTouchLetterListener
    {
        void onTouchLetter(String letter);
    }
}
