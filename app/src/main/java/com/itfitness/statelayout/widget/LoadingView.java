package com.itfitness.statelayout.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2018/6/25 0025.
 */

public class LoadingView extends View {

    private Path path;
    private PathMeasure pathMeasure;
    private float length;
    private Path drawpath;
    private float animatedValue;
    private Paint paint;
    private ValueAnimator valueAnimator;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.parseColor("#48A1DD"));
        path = new Path();
        drawpath = new Path();
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(1700);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec),measureSize(heightMeasureSpec));
        path.addCircle(getWidth()/2,getHeight()/2, Math.min(getWidth()/2,getHeight()/2)-10, Path.Direction.CW);
        pathMeasure = new PathMeasure(path, false);
        length = pathMeasure.getLength();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        valueAnimator.start();
    }
    private int measureSize(int s){
        int size = MeasureSpec.getSize(s);
        int mode = MeasureSpec.getMode(s);
        if(mode==MeasureSpec.EXACTLY){
            return size;
        }else if(mode==MeasureSpec.AT_MOST){
            return Math.min(150,size);
        }else {
            return 150;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawpath.reset();
        //避免硬件加速的Bug
        drawpath.lineTo(0, 0);
        //截取片段
        float stop = length * animatedValue;
        float start = (float) (stop - ((0.5 - Math.abs(animatedValue - 0.5)) * length/1.5));
        pathMeasure.getSegment(start, stop, drawpath, true);
        canvas.drawPath(drawpath, paint);//绘制截取的片段
    }
}
