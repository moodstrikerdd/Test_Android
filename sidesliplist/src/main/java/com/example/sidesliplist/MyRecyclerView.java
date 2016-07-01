package com.example.sidesliplist;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

public class MyRecyclerView extends RecyclerView {


    private int maxLength;
    private int mStartX = 0;
    private View itemLayout;
    private Rect mTouchFrame;
    private int xDown, xMove, yDown, yMove, mTouchSlop;
    private Scroller mScroller;
    private MyViewHolder currentViewHolder;
    //需要华东的item是不是当前的item
    private boolean isCurrentItem = true;
    private View otherItemView;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //初始化Scroller
        mScroller = new Scroller(context, new LinearInterpolator(context, null), true);
    }

    boolean intrList = false;

    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //所有菜单不显示
                xDown = x;
                yDown = y;
                intrList = false;
                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    final View child = getChildAt(i);
                    MyViewHolder viewHolder = (MyViewHolder) getChildViewHolder(child);
                    int scrollX = viewHolder.itemView.getScrollX();
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);
                        if (frame.contains(x, y)) {
                            currentViewHolder = viewHolder;
                            itemLayout = viewHolder.itemView;
                            maxLength = viewHolder.llMenu.getMeasuredWidth();
                        } else {
                            if (scrollX != 0) {
                                hideMenu(viewHolder);
                                //屏蔽滑动（仿qq）
                                return false;
                            }
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop) {
                    intrList = true;
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;
                    if (newScrollX < 0 && scrollX <= 0) {
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {
                        newScrollX = 0;
                    }
                    itemLayout.scrollBy(newScrollX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentViewHolder.isShowing) {
                    hideMenu();
                } else {
                    int scrollX = itemLayout.getScrollX();
                    if (scrollX > maxLength / 5) {
                        mScroller.startScroll(scrollX, 0, maxLength - scrollX, 0, 150);
                        currentViewHolder.isShowing = true;
                        isCurrentItem = true;
                    } else if (scrollX != 0) {
                        mScroller.startScroll(scrollX, 0, -scrollX, 0, 150);
                        isCurrentItem = true;
                    }
                }
                invalidate();
                break;
        }
        mStartX = x;
        return intrList ? intrList : super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (isCurrentItem) {
                itemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            } else {
                otherItemView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 隐藏菜单
     */
    public void hideMenu() {
        hideMenu(currentViewHolder);
    }

    public void hideMenu(MyViewHolder viewHolder) {
        if (viewHolder == null) {
            return;
        }
        viewHolder.isShowing = false;
        isCurrentItem = false;
        int scrollX = viewHolder.itemView.getScrollX();
        mScroller.startScroll(scrollX, 0, -scrollX, 0, 150);
        otherItemView = viewHolder.itemView;
        invalidate();

        /**
         * 属性动画不成立，会有空白
         */
//        int[] location = new int[2];
//        myViewHolder.itemView.getLocationInWindow(location);
//        ValueAnimator animator = ValueAnimator.ofFloat(location[0],viewHolder.llMenu.getWidth());
//        animator.setDuration(500);
//        animator.setTarget(myViewHolder.llMenu);
//        animator.start();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                myViewHolder.itemView.setTranslationX((Float) animation.getAnimatedValue());
//            }
//        });
    }
}
