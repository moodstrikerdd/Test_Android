package com.moo.flowviewgroup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by moo on 2016/7/13.
 */
public class FlowLayout extends ViewGroup {
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int sizeHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int currentLineWidth = 0;
        int currenLineHeight = 0;
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View currentView = getChildAt(i);
            measureChild(currentView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) currentView.getLayoutParams();
            int viewWidth = currentView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int viewHeight = currentView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            currenLineHeight = Math.max(currenLineHeight, viewHeight);
            if (currentLineWidth + viewWidth > sizeWidth) {//宽度大于ViewGroup的宽  wrap_content的时候为屏幕宽度
                //换行
                width = Math.max(width, currentLineWidth);
                height += currenLineHeight;
                currenLineHeight = 0;
                currentLineWidth = 0;
            } else {
                currentLineWidth += viewWidth;
            }
        }
        if (currenLineHeight != 0) {
            height += currenLineHeight;
        }
        width = sizeWidthMode == MeasureSpec.EXACTLY ? sizeWidth : (width + getPaddingLeft() + getPaddingRight());
        height = sizeHeightMode == MeasureSpec.EXACTLY ? sizeHeight : (height + getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int childLeft = 0;
        int childTop = 0;
        int lineHeight = 0;

        //根据子控件的宽高，计算子控件应该出现的位置。
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            childLeft += paddingLeft + params.leftMargin;
            if (childTop == 0) {
                childTop = paddingTop + params.topMargin;
            }
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > width) {
                childLeft = paddingLeft + params.leftMargin;
                childTop += paddingTop + params.topMargin + params.bottomMargin + lineHeight;
                lineHeight = childHeight;
            }
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + params.rightMargin;
        }
    }

    public void setViews(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            final int position = i;
            View view = views.get(position);
            addView(view, position);
            Log.e("add", "add=====>" + i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    public void setTexts(List<String> texts) {
        int[] shapes = {};
        setTexts(texts, shapes);
    }

    public void setTexts(List<String> texts, int shape) {
        int[] shapes = {shape};
        setTexts(texts, shapes);
    }

    public void setTexts(List<String> texts, int[] shapes) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            TextView textView = new TextView(mContext);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            if (shapes.length > 0) {
                textView.setBackgroundResource(shapes[new Random().nextInt(shapes.length)]);
            }
            textView.setText(texts.get(i));
            views.add(textView);
        }
        setViews(views);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}