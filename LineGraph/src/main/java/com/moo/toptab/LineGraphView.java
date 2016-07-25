package com.moo.toptab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/17.
 */
public class LineGraphView extends View {
    private int mWidth;
    private int mHeight;
    private int numX = 5;
    private int startY = 0;
    private int endY = 400;
    private int lineHeight;

    private Paint mPaint;
    private Paint textPaint;
    private float textWidth;
    private float textHeight;
    private int[] data = {100, 300, 100, 200, 100};
    private String[] strings = {"6", "7", "8", "9", "10-5"};
    private int[] x;
    private int[] y;
    private Paint linePaint;

    public LineGraphView(Context context) {
        super(context, null, 0);
    }

    public LineGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.GRAY);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(16);
        textPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(4);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LineGraphView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.LineGraphView_numX:
                    numX = typedArray.getInteger(index, 0);
                    break;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textWidth = getMaxTextWith();
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.descent - fontMetrics.ascent;

        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (specWidthMode == MeasureSpec.EXACTLY) {
            mWidth = specWidth;
        } else if (specWidthMode == MeasureSpec.AT_MOST) {
            mWidth = (int) (textWidth * numX + getPaddingLeft() + getPaddingRight());
        }

        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (specHeightMode == MeasureSpec.EXACTLY) {
            mHeight = specHeight;
        } else if (specHeightMode == MeasureSpec.AT_MOST) {
            mHeight = (int) (mWidth * 0.618);
        }
        setMeasuredDimension(mWidth, mHeight);
        lineHeight = (int) (mHeight - textHeight * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        x = new int[strings.length];
        y = new int[strings.length];
        //没两条竖线之间的距离
        int itemWith = (int) (mWidth - (getPaddingLeft() + getPaddingRight() + textWidth)) / (numX - 1);
        for (int i = (int) (getPaddingLeft() + textWidth / 2), m = 0; i <= mWidth - (getPaddingRight() + textWidth / 2); i = i + itemWith) {
            x[m] = i;
            //写底部文字
            canvas.drawText(strings[m], i, mHeight - textHeight / 2, textPaint);
            //画竖线
            canvas.drawLine(i, 0, i, mHeight - 2 * textHeight, mPaint);
            m++;
        }
        //画底部横线
        canvas.drawLine((getPaddingLeft() + textWidth / 2), mHeight - 2 * textHeight + 2, mWidth - (getPaddingRight() + textWidth / 2), mHeight - 2 * textHeight + 2, linePaint);
        for (int i = 0; i < x.length; i++) {
            y[i] = lineHeight - data[i] * lineHeight / (endY - startY);
            //画点
            canvas.drawCircle(x[i], y[i], 5, linePaint);
            linePaint.setStrokeWidth(2);
            if (i > 0) {
                //连线
                canvas.drawLine(x[i - 1], y[i - 1], x[i], y[i], linePaint);
            }
        }
    }

    /**
     * 获取最大文字宽
     *
     */
    private float getMaxTextWith() {
        float returnWidth;
        float maxWidth = 0;
        float minWidth = 0;
        if (strings == null) {
            returnWidth = 0;
        } else {
            for (String string : strings) {
                float width = textPaint.measureText(string);
                if (width > maxWidth) {
                    maxWidth = width;
                    continue;
                }
                if (width < minWidth) {
                    minWidth = width;
                }
            }
            if (maxWidth == minWidth) {
                returnWidth = minWidth * 3;
            } else {
                returnWidth = maxWidth * 1.5f;
            }
        }
        return returnWidth;
    }
}
