package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.medipal.PieChartView.OnPiegraphItemSelectedListener;
import sg.edu.nus.iss.medipal.PieChartView.PiegraphView;
import sg.edu.nus.iss.medipal.PieChartView.ScreenUtil;
import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.UnConsumptionAdapter;
import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.fragment.ConsumedFragment;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;

public class ConsumptionDetail extends AppCompatActivity {

    private PiegraphView view;
    private TextView text;
    private TextView back;
    private float radius;
    private int strokeWidth;
    private String strokeColor = "#ffffff";
    private float animSpeed = (float) 20;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static final String fileName = "sharedfile";

    ConsumptionDAO consumptionDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_detail);
        view = (PiegraphView) findViewById(R.id.piechar_view);
        text = (TextView) findViewById(R.id.content);
        back = (TextView) findViewById(R.id.back);
      //  back.getBackground().setAlpha(80);
        text.setText("the first");
        radius = ScreenUtil.dip2px(this, 140);
        strokeWidth = ScreenUtil.dip2px(this, 3);
        view.setitemsValues(new Double[] { 10d, 20d, 30d, 20d, 40d });
        // pieChart.setItemsColors(colors);//设置各个块的颜色,可以使用默认
        // view.setmoveSpeed(animSpeed);// 设置旋转速度
        // view.setRaduis(radius);// 设置饼状图半径，不包含边缘的圆环
        // view.setStrokeWidth(strokeWidth);// 设置边缘的圆环粗度
        view.setStrokeColor(strokeColor);// 设置边缘的圆环颜色
        view.setItemSelectedListener(new OnPiegraphItemSelectedListener() {
            @Override
            public void onPieChartItemSelected(int position, String itemColor,
                                               double value, float percent, float rotateTime) {
                Toast.makeText(ConsumptionDetail.this, "the" + position + "part",
                        Toast.LENGTH_SHORT).show();

                text.setText("the" + (position + 1) + "part");

            }
        });
    }
    public void OnclickConsumed(View view) {
       /*
        Intent i = new Intent(getApplicationContext(), ConsumedActivity.class);

       // Intent show = new Intent (this,ConsumedFragment.class);
       // show.putExtra("grxx",1);


       // startActivity(show);
        startActivity(i);
        */
        int id = R.id.ViewConsumption;
        Fragment fragment;
        if (id == R.id.ViewConsumption) {
            fragment = new ConsumedFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.consumedData,fragment).commit();
        }
        else {
            finish();
        }

    }




    public void OnclickUnconsumed(View view) {
        consumptionDAO = new ConsumptionDAO(this);
        Bundle getFromConsumption = getIntent().getExtras();
        int medicine_id = 0;
        medicine_id = getFromConsumption.getInt("medicine_id");
        String ConsumedOn = getFromConsumption.getString("ConsumedOn");
        int getConsumptionQuantity[] = consumptionDAO.getConsumptionQuantities(Integer.toString(medicine_id),ConsumedOn);

        String quantityValues="";
        for(int i=0; i<getConsumptionQuantity.length; i++) {
          quantityValues += Integer.toString(getConsumptionQuantity[i]);
            if(i!=(getConsumptionQuantity.length - 1))
                quantityValues+=" ";
        }

        Intent transferToUnconsumedActivity = new Intent(getApplicationContext(),UncomsumedActivity.class);
        transferToUnconsumedActivity.putExtra("ConsumptionQuantity",quantityValues);
        transferToUnconsumedActivity.putExtra("medicine_id",medicine_id);
        startActivity(transferToUnconsumedActivity);
        /*
        Intent i = new Intent(getApplicationContext(), UncomsumedActivity.class);
        SharedPreferences share = super.getSharedPreferences(fileName,MODE_PRIVATE);
        i.putExtra("countFrequentNum",share.getInt("countFrequentNum",100));
        startActivity(i);
        */
    }
}
