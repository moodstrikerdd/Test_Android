package com.moo.toptab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/6/16.
 */
public class LineGraghView extends View {
    private int mWidth;
    private int mHeight;
    private int numX = 5;
    private int startY;
    private int endY;

    private Paint mPaint;

    public LineGraghView(Context context) {
        super(context, null, 0);
    }

    public LineGraghView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineGraghView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(2);
    }

    private void initAttrs() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (specWidthMode == MeasureSpec.EXACTLY) {
            mWidth = specWidth;
        } else if (specWidthMode == MeasureSpec.AT_MOST) {
            mWidth = numX * 20;
        }

        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (specHeightMode == MeasureSpec.EXACTLY) {
            mHeight = specHeight;
        } else if (specHeightMode == MeasureSpec.AT_MOST) {
            mHeight = (int) (mWidth * 0.6);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private String[] strings = {"6","7","8","9","10-5"};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int itemWith = mWidth / (numX - 1);
        for (int i = 0,m=0; i <= mWidth; i = i + itemWith) {
            canvas.drawLine(i, 0, i, mWidth, mPaint);
            mPaint.setTextSize(12);
            String textWidth = mPaint.getTextWidths(strings[m],)
            canvas.drawText(strings[m],);
        }
        mPaint.setColor(0xe78821);
        canvas.drawLine();
    }
}
