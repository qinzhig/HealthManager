package sg.edu.nus.iss.medipal.adapter;

/**
 * Created by levis on 3/23/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.pojo.Ice;

public class IceAdapter extends RecyclerView.Adapter<IceAdapter.IceViewHolder>{

    private Context _context;
    private List<Ice> _iceList;

    public class IceViewHolder extends RecyclerView.ViewHolder {
        public TextView _name, _contactNo, _contactType;
        public ImageView _edit, _delete;

        public IceViewHolder(View view) {
            super(view);

            _name = (TextView) view.findViewById(R.id.icelistitemname_view);
            _contactNo = (TextView) view.findViewById(R.id.icelistitemcontactnumber_view);
            _contactType = (TextView) view.findViewById(R.id.icelistitemcontacttype_view);
            _edit = (ImageView) view.findViewById(R.id.iceedit);
            _delete = (ImageView) view.findViewById(R.id.icedelete);

            /*
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addEditHealthBio = new Intent(_context, AddEditHealthBioActivity.class);
                    addEditHealthBio.putExtra("id",condition.getTag().toString());
                    addEditHealthBio.putExtra("condition",condition.getText().toString());
                    addEditHealthBio.putExtra("startDate",startDate.getText().toString());
                    addEditHealthBio.putExtra("conditionType",conditionType.getText().toString());
                    addEditHealthBio.putExtra("isEdit",true);
                    ((Activity)_context).startActivityForResult(addEditHealthBio,2);
                }
            });
            */
            /*
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Contact?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    IceManager healthBioManager = new IceManager();
                                    healthBioManager.deleteIce(condition.getTag().toString(), _context);
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
            */
        }
    }

    public IceAdapter(Context mContext, List<Ice> iceBioList) {
        this._context = mContext;
        this._iceList = iceBioList;
    }

    @Override
    public IceAdapter.IceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ice_listitem, parent, false);

        return new IceAdapter.IceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IceAdapter.IceViewHolder holder, int position) {
        Ice ice = _iceList.get(position);
        holder._name.setText(ice.getName());
        holder._contactNo.setText(ice.getContactNo());

        String contactType = "";
        if(ice.getContactType()== 0){
            contactType = "NOK";
        } else{
            contactType = "GP";
        }
        holder._contactType.setText(contactType);
        holder._name.setTag(ice.getId());
    }

    @Override
    public int getItemCount() {
        return _iceList.size();
    }

    public void delete(int position) { //removes the row
        _iceList.remove(position);
        notifyItemRemoved(position);
    }
}
