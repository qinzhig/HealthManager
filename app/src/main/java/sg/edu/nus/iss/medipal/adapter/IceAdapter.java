package sg.edu.nus.iss.medipal.adapter;

/**
 * Created by levis on 3/23/2017.
 */

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
import android.app.Activity;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.IceManager;
import sg.edu.nus.iss.medipal.pojo.Ice;
import sg.edu.nus.iss.medipal.activity.IceActivity;

public class IceAdapter extends RecyclerView.Adapter<IceAdapter.IceViewHolder>{

    private Context _context;
    private List<Ice> _iceList;

    public class IceViewHolder extends RecyclerView.ViewHolder {
        public TextView _name;
        public TextView _contactNo;
        public TextView _contactType;
        public ImageView _edit;
        public ImageView _delete;

        public IceViewHolder(View view) {
            super(view);

            _name = (TextView) view.findViewById(R.id.icelistitemname_view);
            _contactNo = (TextView) view.findViewById(R.id.icelistitemcontactnumber_view);
            _contactType = (TextView) view.findViewById(R.id.icelistitemcontacttype_view);
            _edit = (ImageView) view.findViewById(R.id.iceedit);
            _delete = (ImageView) view.findViewById(R.id.icedelete);

            _edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IceManager iceManager = new IceManager();
                    Ice ice = iceManager.getIce(getAdapterPosition(), _context);

                    Intent iceIntent = new Intent(_context, IceActivity.class);
                    iceIntent.putExtra("id", ice.getId());
                    iceIntent.putExtra("name", ice.getName());
                    iceIntent.putExtra("contactNo", ice.getContactNo());
                    iceIntent.putExtra("contactType", ice.getContactType());
                    iceIntent.putExtra("description", ice.getDescription());
                    iceIntent.putExtra("isEdit", true);
                    ((Activity)_context).startActivityForResult(iceIntent, 103);
                }
            });

            _delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Contact?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    IceManager iceManager = new IceManager();
                                    iceManager.deleteIce(getAdapterPosition(), _context);
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }

    public IceAdapter(Context context, List<Ice> iceBioList) {
        this._context = context;
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

    public void delete(int position) {
        _iceList.remove(position);
        notifyItemRemoved(position);
    }
}
