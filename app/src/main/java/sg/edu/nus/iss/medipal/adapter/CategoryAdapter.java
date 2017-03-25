package sg.edu.nus.iss.medipal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.EditCategoryActivity;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.Category;

/**
 * Created by zhiguo on 15/3/17.
 */

public class CategoryAdapter extends ArrayAdapter<Category>{

    private Context context;
    private List<Category> categorys = new ArrayList<>();

    public CategoryAdapter(Context context){

        super(context, R.layout.medicine_category_row_layout);
        this.context=context;
        refreshCategorys();
    }

    public void refreshCategorys() {

        categorys.clear();
        categorys.addAll(App.hm.getCategorys(this.context));

        notifyDataSetChanged();
    }

    public int getCount(){
        return  categorys.size();
    }

    static class ViewHolder{
        TextView tvName;
        Button btnUpdate;

    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Category category = categorys.get(position);

        String reminder_status;
        if(category.getRemind()) {reminder_status = "Remind_ON";}else {reminder_status = "Remind_OFF";}

        String out_name = String.format("%-12s | %-5s | %s",reminder_status,category.getCategory_code(),category.getCategory_name());
        viewHolder.tvName.setText(out_name);
        viewHolder.tvName.setTextColor(Color.rgb(0, 0, 0));

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if((category.getId() ==1) || (category.getId() >4) )
                {
                    Intent EditCategory = new Intent(context,EditCategoryActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("category",category);

                    //EditCategory.setClass(context,EditCategoryActivity.class);
                    EditCategory.putExtras(bundle);
                    EditCategory.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(EditCategory);
                }else{

                    Toast toast = Toast.makeText(context,"Pre-defined Category Not allowed to Modify",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });

        return convertView;

    }


}
