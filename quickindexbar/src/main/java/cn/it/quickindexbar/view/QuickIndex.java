package cn.it.quickindexbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.it.quickindexbar.utils.Cheeses;

/**
 * 自定义的控件
 */
public class QuickIndex extends View {

    private Paint paint;
    private Rect rect;
    private int cellWidth;
    private float cellHeight;

    /**
     * 记录当前手指触摸的单元格
     */
    private int currentIndex = -1;
    /**
     * 记录当前手指触摸上一次的单元格
     */
    private int preIndex = -1;

    public QuickIndex(Context context) {
        this(context, null);
    }

    public QuickIndex(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔并且使用抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //给画笔设置颜色
        paint.setColor(Color.BLACK);
        //设置画笔加粗
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        // paint.setFakeBoldText(true);
        //设置字体大小
        int textSize = pxToDp(14);
        paint.setTextSize(textSize);
        //初始化Rect，空的矩形，用来存放文本
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < Cheeses.LETTERS.length; i++) {
            String text = Cheeses.LETTERS[i];
            //计算文本绘制的位置
            paint.getTextBounds(text, 0, 1, rect);
            //获取文本的宽高
            int textWidth = rect.width();
            int textHeight = rect.height();

            //计算绘制文本宽高的位置
            float x = (cellWidth-textWidth)*0.5f;
            float y = cellHeight*0.5f + textHeight*0.5f +cellHeight*i;
            //如果是当前手指触摸的单元格则颜色变为红色，否则还是黑色
            paint.setColor(currentIndex == i?Color.RED:Color.BLACK);
            //绘制文本到指定位置
            canvas.drawText(text, x, y, paint);
        }
    }

    /**
     * 当布局加载完毕之后调用该方法
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取单元格的宽和高
        cellWidth = getMeasuredWidth();
        cellHeight = getMeasuredHeight()*1.0f/ Cheeses.LETTERS.length;
    }

    /**
     * 重写onTouchEvent用来处理事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //保存上一次手指触摸的单元格
                preIndex = currentIndex;
                //获取当前手指触摸的位置
                float y = event.getY();
                //计算当前手指所在的单元格
                currentIndex = (int) (y/cellHeight);
                //判断当前手指触摸的范围
                if (currentIndex >=0 && currentIndex < Cheeses.LETTERS.length){
                    if (onSelectTextListener != null && preIndex != currentIndex){
                        onSelectTextListener.selectTextListener(Cheeses.LETTERS[currentIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                currentIndex = -1;
                break;
            default:
                break;
        }

        //重新绘制
        invalidate();
        return true;
    }

    /** dp转换px */
    public int pxToDp(int dp){
        //获取手机的密度比
        float density = getResources().getDisplayMetrics().density;
        return (int) (density*dp+0.5);
    }

    /**
     * 定义接口回调
     */
    public interface OnSelectTextListener{

        public void selectTextListener(String text);

    }


    private OnSelectTextListener onSelectTextListener;

    /**
     * 提供一个方法改变文本
     * @param onSelectTextListener
     */
    public void setOnSelectTextListener(OnSelectTextListener onSelectTextListener) {
        this.onSelectTextListener = onSelectTextListener;
    }

}
