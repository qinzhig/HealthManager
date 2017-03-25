package sg.edu.nus.iss.medipal.PieChartView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by apple on 14/03/2017.
 */

public class PiegraphView extends View implements Runnable {

    // 动画速度 animation speed
    private float moveSpeed = 5.0F;
    // 总数值 total
    private double total;
    // 各饼块对应的数值
    private Double[] itemValuesTemp;
    // 各饼块对应的数值
    private Double[] itemsValues;
    // 各饼块对应的颜色
    private String[] itemColors;
    // 各饼块的角度
    private float[] itemsAngle;
    // 各饼块的起始角度
    private float[] itemsStartAngle;
    // 各饼块的占比
    private float[] itemsPercent;
    // 旋转起始角度
    private float rotateStartAng = 0.0F;
    // 旋转结束角度
    private float rotateEndAng = 0.0F;
    // 正转还是反转
    private boolean isClockWise;
    // 正在旋转
    private boolean isRotating;
    // 是否开启动画
    private boolean isAnimEnabled = true;
    // 边缘圆环的颜色
    private String loopStrokeColor;
    // 边缘圆环的宽度
    private float strokeWidth = 0.0F;
    // 饼图半径，不包括圆环
    private float radius;
    // 当前item的位置
    private int itemPostion = -1;
    // 停靠位置
    private int stopPosition = 0;
    // 停靠位置
    public static final int TO_RIGHT = 0;
    public static final int TO_BOTTOM = 1;
    public static final int TO_LEFT = 2;
    public static final int TO_TOP = 3;

    HealthManager healthManager;




    // 颜色值
    private final String[] DEFAULT_ITEMS_COLORS = { "#FF0000", "#FFFF01",
            "#FF9933", "#9967CC", "#00CCCC", "#00CC33", "#0066CC", "#FF6799",
            "#99FF01", "#FF67FF", "#4876FF", "#FF00FF", "#FF83FA", "#0000FF",
            "#363636", "#FFDAB9", "#90EE90", "#8B008B", "#00BFFF", "#FFFF00",
            "#00FF00", "#006400", "#00FFFF", "#00FFFF", "#668B8B", "#000080",
            "#008B8B" };
    // 消息接收器
    private Handler piegraphHandler = new Handler();

    // 监听器集合
    private OnPiegraphItemSelectedListener itemSelectedListener;

    public PiegraphView(Context context, String[] itemColors,
                        Double[] itemSizes, float total, int radius, int strokeWidth,
                        String strokeColor, int stopPosition, int separateDistence) {
        super(context);
        healthManager = new HealthManager();

        this.stopPosition = stopPosition;

        if ((itemSizes != null) && (itemSizes.length > 0)) {
            itemValuesTemp = itemSizes;
            this.total = total;
            // 重设总值

            reSetTotal();
            // 重设各个模块的值
            refreshItemsAngs();
        }

        if (radius < 0)
            // 默认半径设置为100
            this.radius = 100.0F;
        else {
            this.radius = radius;
        }
        // 默认圆环宽度设置为2
        if (strokeWidth < 0)
            strokeWidth = 2;
        else {
            this.strokeWidth = strokeWidth;
        }

        loopStrokeColor = strokeColor;

        if (itemColors == null) {
            // 如果没有设定颜色，则使用默认颜色值
            setDefaultColor();
        } else if (itemColors.length < itemSizes.length) {
            this.itemColors = itemColors;
            // 如果设置的颜色值和设定的集合大小不一样，那么需要充默认颜色值集合里面补充颜色，一般是不会出现这种情况。
            setDifferentColor();
        } else {
            this.itemColors = itemColors;
        }

        invalidate();
    }

    public PiegraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loopStrokeColor = "#000000";
        // 把我们自定义的属性,放在attrs的属性集合里面
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PiegraphView);
        radius = ScreenUtil.dip2px(getContext(),
                a.getFloat(R.styleable.PiegraphView_radius, 100));
        strokeWidth = ScreenUtil.dip2px(getContext(),
                a.getFloat(R.styleable.PiegraphView_strokeWidth, 2));
        moveSpeed = a.getFloat(R.styleable.PiegraphView_moveSpeed, 5);
        if (moveSpeed < 1F) {
            moveSpeed = 1F;
        }
        if (moveSpeed > 5.0F) {
            moveSpeed = 5.0F;
        }
        invalidate();
        a.recycle();
    }

    /**
     * @Title: setRaduis
     * @Description: 设置半径
     * @param radius
     * @throws
     */
    public void setRaduis(float radius) {
        if (radius < 0)
            this.radius = 100.0F;
        else {
            this.radius = radius;
        }
        invalidate();
    }

    public float getRaduis() {
        return radius;
    }

    /**
     * @Title: setStrokeWidth
     * @Description: 设置圆环宽度
     * @param strokeWidth
     * @throws
     */
    public void setStrokeWidth(int strokeWidth) {
        if (strokeWidth < 0)
            strokeWidth = 2;
        else {
            this.strokeWidth = strokeWidth;
        }
        invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * @Title: setStrokeColor
     * @Description: 设置圆环颜色
     * @param strokeColor
     * @throws
     */
    public void setStrokeColor(String strokeColor) {
        loopStrokeColor = strokeColor;
        invalidate();
    }

    public String getStrokeColor() {
        return loopStrokeColor;
    }

    /**
     * @Title: setitemColors
     * @Description: 设置个饼块的颜色
     * @param colors
     * @throws
     */
    public void setitemColors(String[] colors) {
        if ((itemsValues != null) && (itemsValues.length > 0)) {
            // 如果传入值未null，则使用默认的颜色
            if (colors == null) {
                setDefaultColor();
            } else if (colors.length < itemsValues.length) {
                // 如果传入颜色不够，则从默认颜色中填补
                itemColors = colors;
                setDifferentColor();
            } else {
                itemColors = colors;
            }
        }

        invalidate();
    }

    public String[] getitemColors() {
        return itemColors;
    }

    /**
     * @Title: setitemsValues
     * @Description: 设置各饼块数据
     * @param items
     * @throws
     */
    public void setitemsValues(Double[] items) {
        if ((items != null) && (items.length > 0)) {
            itemValuesTemp = items;
            // 重设总值，默认为所有值的和
            reSetTotal();
            refreshItemsAngs();
            setitemColors(itemColors);
        }
        invalidate();
    }

    public Double[] getitemsValues() {
        return itemValuesTemp;
    }

    public void setTotal(int total) {
        this.total = total;
        reSetTotal();

        invalidate();
    }

    public double getTotal() {
        return total;
    }

    /**
     * @Title: setAnimEnabled
     * @Description: 设置是否开启旋转动画
     * @param isAnimEnabled
     * @throws
     */
    public void setAnimEnabled(boolean isAnimEnabled) {
        this.isAnimEnabled = isAnimEnabled;
        invalidate();
    }

    public boolean isAnimEnabled() {
        return isAnimEnabled;
    }

    public void setmoveSpeed(float moveSpeed) {
        if (moveSpeed < 1F) {
            moveSpeed = 1F;
        }
        if (moveSpeed > 5.0F) {
            moveSpeed = 5.0F;
        }
        this.moveSpeed = moveSpeed;
    }

    public float getmoveSpeed() {
        if (isAnimEnabled()) {
            return moveSpeed;
        }
        return 0.0F;
    }

    /**
     * @Title: setShowItem
     * @Description: 旋转到指定位置的item
     * @param position
     *            位置
     * @param anim
     *            是否动画
     * @param
     *
     * @throws
     */
    public void setShowItem(int position, boolean anim) {
        if ((itemsValues != null) && (position < itemsValues.length)
                && (position >= 0)) {
            // 拿到需要旋转的角度
            rotateEndAng = getLastrotateStartAngle(position);
            itemPostion = position;

            if (anim) {
                rotateStartAng = 0.0F;
                if (rotateEndAng > 0.0F) {
                    // 如果旋转角度大于零，则顺时针旋转
                    isClockWise = true;
                } else {
                    // 如果小于零则逆时针旋转
                    isClockWise = false;
                }
                // 开始旋转
                isRotating = true;
            } else {
                rotateStartAng = rotateEndAng;
            }

            // 如果有监听器
            if (null != itemSelectedListener) {
                itemSelectedListener.onPieChartItemSelected(position,
                        itemColors[position], itemsValues[position],
                        itemsPercent[position],
                        getAnimTime(Math.abs(rotateEndAng - rotateStartAng)));
            }
            // 开始旋转
            piegraphHandler.postDelayed(this, 1L);
        }
    }

    private float getLastrotateStartAngle(int position) {
        float result = 0.0F;
        // 拿到旋转角度，根据停靠位置进行修正
        result = itemsStartAngle[position] + itemsAngle[position] / 2.0F
                + getstopPositionAngle();
        if (result >= 360.0F) {
            result -= 360.0F;
        }

        if (result <= 180.0F)
            result = -result;
        else {
            result = 360.0F - result;
        }

        return result;
    }

    /**
     * @Title: getstopPositionAngle
     * @Description: 根据停靠位置修正旋转角度
     * @return
     * @throws
     */
    private float getstopPositionAngle() {
        float resultAngle = 0.0F;
        switch (stopPosition) {
            case TO_RIGHT:
                resultAngle = 0.0F;
                break;
            case TO_LEFT:
                resultAngle = 180.0F;
                break;
            case TO_TOP:
                resultAngle = 90.0F;
                break;
            case TO_BOTTOM:
                resultAngle = 270.0F;
                break;
        }

        return resultAngle;
    }

    public int getShowItem() {
        return itemPostion;
    }

    public void setstopPosition(int stopPosition) {
        this.stopPosition = stopPosition;
    }

    public int getstopPosition() {
        return stopPosition;
    }

    /**
     * @Title: refreshItemsAngs
     * @Description: 初始化各个角度
     * @throws
     */
    private void refreshItemsAngs() {
        if ((itemValuesTemp != null) && (itemValuesTemp.length > 0)) {
            // 如果出现总值比设定的集合的总值还大，那么我们自动的增加一个模块出来（几乎不会出现这种情况）
            if (getTotal() > getAllSizes()) {
                itemsValues = new Double[itemValuesTemp.length + 1];
                for (int i = 0; i < itemValuesTemp.length; i++) {
                    itemsValues[i] = itemValuesTemp[i];
                }
                itemsValues[(itemsValues.length - 1)] = (getTotal() - getAllSizes());
            } else {
                itemsValues = new Double[itemValuesTemp.length];
                itemsValues = itemValuesTemp;
            }



            // 开始给各模块赋值
            itemsPercent = new float[itemsValues.length];
            itemsStartAngle = new float[itemsValues.length];
            itemsAngle = new float[itemsValues.length];
            float startAngle = 0.0F;
            Log.v("PIECHARTVIEW Length","()()()()()()()()()()()()()()()()()()()"+itemsValues.length);

            for (int i = 0; i < itemsValues.length; i++) {
                itemsPercent[i] = ((float) (itemsValues[i] * 1.0D / getTotal() * 1.0D));
                Log.v("PIECHARTVIEW","()()()()()()()()()()()()()()()()()()()"+itemsValues[i]);

            }
            Log.v("PIECHARTVIEW","()()()()()()()()()()()()()()()()()()()"+itemsValues);
            Log.v("PIECHARTVIEW ITEMVALUESTEMP","()()()()()()()()()()()()()()()()()()()()"+itemValuesTemp);

            for (int i = 0; i < itemsPercent.length; i++) {
                itemsAngle[i] = (360.0F * itemsPercent[i]);
                if (i != 0) {
                    itemsStartAngle[i] = startAngle + itemsAngle[i - 1];
                    startAngle = 360.0F * itemsPercent[(i - 1)] + startAngle;
                } else {
                    // Android默认起始位置设定是右侧水平，初始化默认停靠位置也在右边。有兴趣的同学可以根据自己的喜好修改
                    itemsStartAngle[i] = -itemsAngle[i] / 2;
                    startAngle = itemsStartAngle[i];
                }
            }
        }
    }

    /**
     * 绘图
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 饼图半径加圆环半径
        float realRadius = radius + strokeWidth;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float lineLength = 2.0F * radius + strokeWidth;
        if (strokeWidth != 0.0F) {
            // 空心的画笔，先画外层圆环
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor(loopStrokeColor));
            paint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(realRadius, realRadius, realRadius - 5, paint);
        }

        if ((itemsAngle != null) && (itemsStartAngle != null)) {
            // 旋转角度
            canvas.rotate(rotateStartAng, realRadius, realRadius);
            // 设定饼图矩形
            RectF oval = new RectF(strokeWidth, strokeWidth, lineLength,
                    lineLength);
            // 开始画各个扇形
            for (int i = 0; i < itemsAngle.length; i++) {
                oval = new RectF(strokeWidth, strokeWidth, lineLength,
                        lineLength);
                // 先画实体
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.parseColor(itemColors[i]));
                canvas.drawArc(oval, itemsStartAngle[i], itemsAngle[i], true,
                        paint);
                // 再画空心体描边
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(strokeWidth / 2);
                paint.setColor(Color.WHITE);
                canvas.drawArc(oval, itemsStartAngle[i], itemsAngle[i], true,
                        paint);

            }
        }
        // 画中心的小圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(realRadius, realRadius,
                ScreenUtil.dip2px(getContext(), 40), paint);
        // 描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(realRadius, realRadius,
                ScreenUtil.dip2px(getContext(), 40), paint);

    }

    /**
     * 触摸事件
     */
    public boolean onTouchEvent(MotionEvent event) {
        if ((!isRotating) && (itemsValues != null) && (itemsValues.length > 0)) {
            float x1 = 0.0F;
            float y1 = 0.0F;
            switch (event.getAction()) {
                // 按下
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    float r = radius + strokeWidth;
                    if ((x1 - r) * (x1 - r) + (y1 - r) * (y1 - r) - r * r <= 0.0F) {
                        // 拿到位置
                        int position = getShowItem(getTouchedPointAngle(r, r, x1,
                                y1));
                        // 旋转到指定位置
                        setShowItem(position, isAnimEnabled());
                    }
                    break;
            }

        }

        return super.onTouchEvent(event);
    }

    /**
     * @Title: getTouchedPointAngle
     * @Description: 计算触摸角度
     * @param radiusX
     *            圆心
     * @param radiusY
     *            圆心
     * @param x1
     *            触摸点
     * @param y1
     *            触摸点
     * @return
     * @throws
     */
    private float getTouchedPointAngle(float radiusX, float radiusY, float x1,
                                       float y1) {
        float differentX = x1 - radiusX;
        float differentY = y1 - radiusY;
        double a = 0.0D;
        double t = differentY
                / Math.sqrt(differentX * differentX + differentY * differentY);

        if (differentX > 0.0F) {
            // 0~90
            if (differentY > 0.0F)
                a = 6.283185307179586D - Math.asin(t);
            else
                // 270~360
                a = -Math.asin(t);
        } else if (differentY > 0.0F)
            // 90~180
            a = 3.141592653589793D + Math.asin(t);
        else {
            // 180~270
            a = 3.141592653589793D + Math.asin(t);
        }
        return (float) (360.0D - a * 180.0D / 3.141592653589793D % 360.0D);
    }

    /**
     * @Title: getShowItem
     * @Description: 拿到触摸位置
     * @param touchAngle
     *            触摸位置角度
     * @return
     * @throws
     */
    private int getShowItem(float touchAngle) {
        int position = 0;
        for (int i = 0; i < itemsStartAngle.length; i++) {
            if (i != itemsStartAngle.length - 1) {
                if ((touchAngle >= itemsStartAngle[i])
                        && (touchAngle < itemsStartAngle[(i + 1)])) {
                    position = i;
                    break;
                }

            } else if ((touchAngle > itemsStartAngle[(itemsStartAngle.length - 1)])
                    && (touchAngle < itemsStartAngle[0])) {
                position = itemsValues.length - 1;
            } else {
                // 如果触摸位置不对，则旋转到最大值得位置
                position = getPointItem(itemsStartAngle);
            }

        }

        return position;
    }

    private int getPointItem(float[] startAngle) {
        int item = 0;

        float temp = startAngle[0];
        for (int i = 0; i < startAngle.length - 1; i++) {
            if (startAngle[(i + 1)] - temp > 0.0F)
                temp = startAngle[i];
            else {
                return i;
            }
        }

        return item;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        piegraphHandler.removeCallbacks(this);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthHeight = 2.0F * (radius + strokeWidth + 1.0F);
        // 重设view的宽高
        setMeasuredDimension((int) widthHeight, (int) widthHeight);
    }

    /**
     * 旋转动作
     */
    public void run() {
        if (isClockWise) {
            // 顺时针旋转
            rotateStartAng += moveSpeed;
            invalidate();
            piegraphHandler.postDelayed(this, 10L);
            if (rotateStartAng - rotateEndAng >= 0.0F) {
                rotateStartAng = 0.0F;
                // 如果已经转到指定位置，则停止动画
                piegraphHandler.removeCallbacks(this);
                // 重设各模块起始角度值
                resetStartAngle(rotateEndAng);
                isRotating = false;
            }
        } else {
            // 逆时针旋转
            rotateStartAng -= moveSpeed;
            invalidate();
            piegraphHandler.postDelayed(this, 10L);
            if (rotateStartAng - rotateEndAng <= 0.0F) {
                rotateStartAng = 0.0F;
                piegraphHandler.removeCallbacks(this);
                resetStartAngle(rotateEndAng);

                isRotating = false;
            }
        }
    }

    private float getAnimTime(float ang) {
        return (int) Math.floor(ang / getmoveSpeed() * 10.0F);
    }

    /**
     * @Title: resetStartAngle
     * @Description: 重设个模块角度
     * @param angle
     * @throws
     */
    private void resetStartAngle(float angle) {
        for (int i = 0; i < itemsStartAngle.length; i++) {
            float newStartAngle = itemsStartAngle[i] + angle;

            if (newStartAngle < 0.0F)
                itemsStartAngle[i] = (newStartAngle + 360.0F);
            else if (newStartAngle > 360.0F)
                itemsStartAngle[i] = (newStartAngle - 360.0F);
            else
                itemsStartAngle[i] = newStartAngle;
        }
    }

    /**
     * @Title: setDefaultColor
     * @Description: 设置默认颜色
     * @throws
     */
    private void setDefaultColor() {
        if ((itemsValues != null) && (itemsValues.length > 0)
                && (itemColors == null)) {
            itemColors = new String[itemsValues.length];
            if (itemColors.length <= DEFAULT_ITEMS_COLORS.length) {
                System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemColors, 0,
                        itemColors.length);
            } else {
                int multiple = itemColors.length / DEFAULT_ITEMS_COLORS.length;
                int difference = itemColors.length
                        % DEFAULT_ITEMS_COLORS.length;

                for (int a = 0; a < multiple; a++) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemColors, a
                                    * DEFAULT_ITEMS_COLORS.length,
                            DEFAULT_ITEMS_COLORS.length);
                }
                if (difference > 0)
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemColors,
                            multiple * DEFAULT_ITEMS_COLORS.length, difference);
            }
        }
    }

    /**
     * @Title: setDifferentColor
     * @Description: 补差颜色
     * @throws
     */
    private void setDifferentColor() {
        if ((itemsValues != null) && (itemsValues.length > itemColors.length)) {
            String[] preitemColors = new String[itemColors.length];
            preitemColors = itemColors;
            int leftall = itemsValues.length - itemColors.length;
            itemColors = new String[itemsValues.length];
            System.arraycopy(preitemColors, 0, itemColors, 0,
                    preitemColors.length);

            if (leftall <= DEFAULT_ITEMS_COLORS.length) {
                System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemColors,
                        preitemColors.length, leftall);
            } else {
                int multiple = leftall / DEFAULT_ITEMS_COLORS.length;
                int left = leftall % DEFAULT_ITEMS_COLORS.length;
                for (int a = 0; a < multiple; a++) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemColors, a
                                    * DEFAULT_ITEMS_COLORS.length,
                            DEFAULT_ITEMS_COLORS.length);
                }
                if (left > 0) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemColors,
                            multiple * DEFAULT_ITEMS_COLORS.length, left);
                }
            }
            preitemColors = null;
        }
    }

    /**
     * @Title: reSetTotal
     * @Description: 重设总值
     * @throws
     */
    private void reSetTotal() {
        double totalSizes = getAllSizes();
        if (getTotal() < totalSizes)
            total = totalSizes;
    }

    private double getAllSizes() {
        float tempAll = 0.0F;
        if ((itemValuesTemp != null) && (itemValuesTemp.length > 0)) {
            for (double itemsize : itemValuesTemp) {
                tempAll += itemsize;
            }
        }

        return tempAll;
    }

    public OnPiegraphItemSelectedListener getItemSelectedListener() {
        return itemSelectedListener;
    }

    public void setItemSelectedListener(
            OnPiegraphItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public int[] CategotyNum()

    {
        int CategoryNum = 0;
        List<Integer>medicine_id = new ArrayList<Integer>();

        List<Integer>category_id = new ArrayList<Integer>();

        List<Integer>category_Num = new ArrayList<Integer>();



        ConsumptionManager consumptionManager = new ConsumptionManager(getContext());
        List<Consumption>consumptionList = new ArrayList<Consumption>();
        consumptionList = consumptionManager.getConsumptions(getContext());
        Consumption consumptionitem = new Consumption();
        Iterator iterator = consumptionList.iterator();
        while (iterator.hasNext()) {
            consumptionitem = (Consumption)  iterator.next();
            medicine_id.add(consumptionitem.getMedicineId());

        }
        for (int i = 0; i < medicine_id.size(); i++)
        {
            category_id.add(i,healthManager.getMedicine(medicine_id.get(i),getContext()).getCateId());
        }

        Collections.sort(category_id);


        int diffrent=category_id.get(0);
        int[] a = {0};

        for (int i = 0,j=0; i < category_id.size(); i++)
        {
            if (diffrent == category_id.get(i)) {
                i++;

            }else{
                diffrent = category_id.get(i);
                a[j]=i+1;
                j++;
            }
        }

        return a;
    }

}

