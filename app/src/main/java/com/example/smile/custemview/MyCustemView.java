package com.example.smile.custemview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Description:
 * Created by LiBing
 * Data:2017/6/9 10:29
 */

public class MyCustemView extends View {

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    //画笔
    public Paint mPaint;
    //数据集合
    public List<PieBean> dataList;
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 宽高
    private int mWidth, mHeight;

    public MyCustemView(Context context) {
        this(context, null);
    }

    public MyCustemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataList != null && dataList.size() != 0) {
            //当前起始角度
            float currentStartAngle = mStartAngle;
            // 将画布坐标原点移动到中心位置
            //canvas.translate(mWidth / 2, mHeight / 2);
            // 饼状图半径
            float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
            //饼状图绘制区域
            RectF rect = new RectF(-r, -r, r, r);
            for (int i = 0; i < dataList.size(); i++) {
                PieBean pie = dataList.get(i);
                mPaint.setColor(pie.getColor());
                canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, mPaint);
                currentStartAngle += pie.getAngle();
            }
        }
    }

    //设置起始角度
    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
        //刷新
        invalidate();
    }

    public void setData(List<PieBean> dataList) {
        this.dataList = dataList;
        initData(dataList);
        //刷新
        invalidate();
    }

    private void initData(List<PieBean> data) {
        if (data != null && data.size() != 0) {

            float sumValue = 0;
            for (int i = 0; i < data.size(); i++) {
                PieBean pie = data.get(i);
                //计算数值和
                sumValue += pie.getValue();
                //设置颜色
                int j = i % mColors.length;
                pie.setColor(mColors[j]);
            }

            float sumAngle = 0;
            for (int i = 0; i < data.size(); i++) {
                PieBean pie = data.get(i);
                // 百分比
                float percentage = pie.getValue() / sumValue;
                // 对应的角度
                float angle = percentage * 360;
                // 记录百分比
                pie.setPercentage(percentage);
                // 记录角度大小
                pie.setAngle(angle);
                sumAngle += angle;
            }
        }
    }
}
