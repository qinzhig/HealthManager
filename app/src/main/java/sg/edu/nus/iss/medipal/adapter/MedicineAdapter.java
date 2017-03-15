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
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by zhiguo on 15/3/17.
 */

public class MedicineAdapter extends ArrayAdapter<Medicine>{

    private Context context;
    private List<Medicine> medicines = new ArrayList<>();

    public MedicineAdapter(Context context){

        super(context, R.layout.medicine_category_row_layout);
        this.context=context;
    }

    public void refreshMedicines() throws ExecutionException, InterruptedException {

        medicines.clear();
        medicines.addAll(App.hm.getMedicines(this.context));

        notifyDataSetChanged();
    }

    public int getCount(){
        return  medicines.size();
    }

    static class ViewHolder{
        TextView tvName;
        Button btnRemove;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_category_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Medicine medicine = medicines.get(position);
        viewHolder.tvName.setText(medicine.toString());

        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                App.hm.deleteMedicine(medicine.getId(),context);
                try {
                    refreshMedicines();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;

    }


}