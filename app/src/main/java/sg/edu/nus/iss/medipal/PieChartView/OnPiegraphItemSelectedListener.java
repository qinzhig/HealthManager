package sg.edu.nus.iss.medipal.PieChartView;



public abstract interface OnPiegraphItemSelectedListener {
    /**
     * @Title: onPieChartItemSelected
     * @Description: 点击事件监听
     * @param position
     *            位置
     * @param itemColor
     *            颜色
     * @param value
     *            item的值
     * @param percent
     *            item的比重
     * @param rotateTime
     * @throws
     */
    public void onPieChartItemSelected(int position, String itemColor,
                                       double value, float percent, float rotateTime);
}
