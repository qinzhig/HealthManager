package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddEditHealthBioActivity;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.HealthBioManager;
import sg.edu.nus.iss.medipal.pojo.HealthBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/16/2017.
 */

public class HealthBioAdapter extends RecyclerView.Adapter<HealthBioAdapter.HealthBioViewHolder>{

    private Context mContext;
    private List<HealthBio> healthBioList;
    //callback listener to communicate with the parent activity
    private AdapterCallbackInterface mCallback;

    public class HealthBioViewHolder extends RecyclerView.ViewHolder {
        public TextView condition,startDate,conditionType;
        public ImageView edit, delete;

        public HealthBioViewHolder(View view) {
            super(view);
            condition = (TextView) view.findViewById(R.id.condition);
            startDate = (TextView) view.findViewById(R.id.startDate);
            conditionType = (TextView) view.findViewById(R.id.conditionType);
            edit = (ImageView) view.findViewById(R.id.edit);
            delete = (ImageView) view.findViewById(R.id.delete);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addEditHealthBio = new Intent(mContext, AddEditHealthBioActivity.class);
                    addEditHealthBio.putExtra("id",condition.getTag().toString());
                    addEditHealthBio.putExtra("condition",condition.getText().toString());
                    addEditHealthBio.putExtra("startDate",startDate.getText().toString());
                    addEditHealthBio.putExtra("conditionType",conditionType.getText().toString());
                    addEditHealthBio.putExtra("isEdit",true);
                    ((Activity)mContext).startActivityForResult(addEditHealthBio,2);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Health Bio?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    HealthBioManager healthBioManager =
                                            new HealthBioManager();
                                    healthBioManager.deleteHealthBio(condition.getTag().toString(), mContext);
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }

    public HealthBioAdapter(Context mContext, List<HealthBio> healthBioList, AdapterCallbackInterface mCallback) {
        this.mContext = mContext;
        this.healthBioList = healthBioList;
        this.mCallback = mCallback;
    }

    @Override
    public HealthBioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_healthbio, parent, false);

        return new HealthBioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HealthBioViewHolder holder, int position) {

       String conditionType = "";

        HealthBio healthBio = healthBioList.get(position);
        holder.condition.setText(healthBio.getCondition());
        holder.startDate.setText(MediPalUtility.
                convertDateToString(healthBio.getStartDate(),"dd MMM yyyy"));
        if(healthBio.getConditionType()=='C'){
            conditionType = "Condition";
        }
        else{
            conditionType = "Allergy";

        }
        holder.conditionType.setText(conditionType);
        holder.condition.setTag(healthBio.getId());
    }

    @Override
    public int getItemCount() {
        return healthBioList.size();
    }

    public void delete(int position) { //removes the row
        healthBioList.remove(position);
        notifyItemRemoved(position);

        if(healthBioList.size() == 0)
        {
            mCallback.refreshView();
        }
    }
}
