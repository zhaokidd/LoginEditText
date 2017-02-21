package com.example.customedittext.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

/**
 * Created by hp on 2017/2/22.
 */

public class LoginEditText extends EditText {
    /*
    * edittext 下划线的起始横纵坐标
    * */
    private float lineStartX;
    private float lineStartY;
    private float lineEndX;
    private float lineEndY;

    private final static float EXTRA_LINE_PADDING_FOCUSED = 5f;
    private final static float EXTRA_LINE_PADDING_NOT_FOCUSED = 0.8f;

    /*
    * 绘制under line 的paint
    * */
    private Paint mUnderLinePaint;

    /*
    * editText是否获得了当前焦点.
    * */
    private boolean isFocusd = true;

    public LoginEditText(Context context) {
        this(context, null);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void getLinePosition() {
        lineStartX = getScrollX();
        lineStartY = getScrollY() + getHeight() - getPaddingBottom() + dip2px(5);
        lineEndX = getScrollX() + getWidth() - getPaddingRight();
        lineEndY = lineStartY + dip2px(isFocusd ?
                EXTRA_LINE_PADDING_FOCUSED :
                EXTRA_LINE_PADDING_NOT_FOCUSED);
    }

    private void init() {
        mUnderLinePaint = new Paint();
        mUnderLinePaint.setStrokeWidth(1f);
        mUnderLinePaint.setColor(Color.BLUE);
        mUnderLinePaint.setAntiAlias(true);
        mUnderLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mUnderLinePaint.setStrokeCap(Paint.Cap.ROUND);

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeLinePadding(hasFocus);
            }
        });
    }

    private void changeLinePadding(boolean hasFocus) {
        isFocusd = hasFocus;

    }

    private int dip2px(float i) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                i,
                getContext().getResources().getDisplayMetrics());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        getLinePosition();
        canvas.drawRect(lineStartX, lineStartY, lineEndX, lineEndY, mUnderLinePaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 设置edittext的背景为空，主要为了隐藏自带的下划线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        } else {
            setBackgroundDrawable(null);
        }
    }


}
