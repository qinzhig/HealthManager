package sg.edu.nus.iss.medipal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
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
            convertView = inflater.inflate(R.layout.medicine_category_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Category category = categorys.get(position);
        viewHolder.tvName.setText(category.getCategory_name());

        return convertView;

    }


}
