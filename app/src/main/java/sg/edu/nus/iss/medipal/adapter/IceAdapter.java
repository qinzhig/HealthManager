package sg.edu.nus.iss.medipal.adapter;

/**
 * Created by levis on 3/23/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;

import java.util.Collections;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.IceManager;
import sg.edu.nus.iss.medipal.pojo.Ice;
import sg.edu.nus.iss.medipal.activity.IceActivity;

public class IceAdapter extends RecyclerView.Adapter<IceAdapter.IceViewHolder>{
    private Context _context;
    private List<Ice> _iceList;

    //callback listener to communicate with the parent activity
    private AdapterCallbackInterface mCallback;

    public class IceViewHolder extends RecyclerView.ViewHolder {
        public TextView _name;
        public TextView _contactNo;
        public TextView _contactType;
        public String _description;
        public ImageView _edit;
        public ImageView _delete;
        public ImageView _top;
        public ImageView _up;
        public ImageView _down;
        public ImageView _bottom;
        public CardView _iceCardView;

        private PopupWindow _iceCardPopUp;

        private String[] items = {"Call", "SMS"};
        private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;

        public IceViewHolder(View view, final View popUp) {
            super(view);

            _iceCardView = (CardView)view.findViewById(R.id.ice_list_item);
            _name = (TextView) view.findViewById(R.id.icelistitemname_view);
            _contactNo = (TextView) view.findViewById(R.id.icelistitemcontactnumber_view);
            _contactType = (TextView) view.findViewById(R.id.icelistitemcontacttype_view);
            _top = (ImageView) view.findViewById(R.id.ice_list_top);
            _up = (ImageView) view.findViewById(R.id.ice_list_up);
            _down = (ImageView) view.findViewById(R.id.ice_list_down);
            _bottom = (ImageView) view.findViewById(R.id.ice_list_bottom);
            _edit = (ImageView) view.findViewById(R.id.iceedit);
            _delete = (ImageView) view.findViewById(R.id.icedelete);

            _contactNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String phoneNumber = _contactNo.getText().toString();
                    if(!phoneNumber.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("tel : " + phoneNumber);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i == 0) {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + Uri.encode(phoneNumber)));

                                    if (ContextCompat.checkSelfPermission(((Activity)_context), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(((Activity)_context), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                    } else {
                                        ((Activity)_context).startActivity(intent);
                                    }

                                } else if (i == 1) {
                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
                                    ((Activity)_context).startActivity(intent);
                                }
                            }
                        });
                        AlertDialog alertdialog = builder.create();
                        alertdialog.show();
                    }
                }
            });

            _iceCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView)popUp.findViewById(R.id.ice_popup_nameview)).setText(_name.getText().toString());
                    ((TextView)popUp.findViewById(R.id.ice_popup_contacttypeview)).setText(_contactType.getText().toString());
                    ((TextView)popUp.findViewById(R.id.ice_popup_contactnumberview)).setText(_contactNo.getText().toString());
                    ((TextView)popUp.findViewById(R.id.ice_popup_descriptionview)).setText(_description);

                    _iceCardPopUp = new PopupWindow(popUp, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    _iceCardPopUp.showAtLocation(popUp, Gravity.CENTER, 0, 0);
                }
            });

            _top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTop(getAdapterPosition());
                }
            });

            _up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goUp(getAdapterPosition());
                }
            });

            _down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { goDown(getAdapterPosition());
                }
            });

            _bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { goBottom(getAdapterPosition());
                }
            });

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
                    iceIntent.putExtra("priority", ice.getPriority());
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
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }

    public IceAdapter(Context context, List<Ice> iceList, AdapterCallbackInterface mCallback) {
        this._context = context;
        this._iceList = iceList;
        this.mCallback = mCallback;
    }

    @Override
    public IceAdapter.IceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ice_listitem, parent, false);
        View popUp = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ice_popup, parent, false);

        return new IceAdapter.IceViewHolder(itemView, popUp);
    }

    @Override
    public void onBindViewHolder(final IceAdapter.IceViewHolder holder, int position) {
        Ice ice = _iceList.get(position);

        holder._name.setText(ice.getName());
        holder._contactNo.setText(ice.getContactNo());

        String contactType = "";
        if(ice.getContactType()== 0){
            contactType = "Emergency Numbers";
        }else if(ice.getContactType()== 1){
            contactType = "Next Of Kin";
        } else{
            contactType = "General Practitioner";
        }

        holder._contactType.setText(contactType);
        holder._description = ice.getDescription();

        holder._name.setTag(ice.getId());
    }

    @Override
    public int getItemCount() {
        return _iceList.size();
    }

    public void goTop(Integer position) {
        Collections.swap(_iceList, position, 0);
        notifyItemMoved(position, 0);
        updateAllPriority();
    }

    public void goUp(Integer position) {
        if(position > 0) {
            Collections.swap(_iceList, position, position - 1);
            notifyItemMoved(position, position - 1);
            updateAllPriority();
        }
    }

    public void goDown(Integer position) {
        if(position < this.getItemCount() - 1) {
            Collections.swap(_iceList, position, position + 1);
            notifyItemMoved(position, position + 1);
            updateAllPriority();
        }
    }

    public void goBottom(Integer position) {
        Collections.swap(_iceList, position, this.getItemCount() - 1);
        notifyItemMoved(position, this.getItemCount() - 1);
        updateAllPriority();
    }

    public void delete(int position) {
        IceManager iceManager = new IceManager();
        Ice ice = (Ice)_iceList.get(position);
        iceManager.deleteIce(ice.getId().toString(), _context);

        _iceList.remove(position);
        notifyItemRemoved(position);
        updateAllPriority();

        if(_iceList.size() == 0)
        {
            mCallback.refreshView("No Contacts found");
        }
    }

    void updateAllPriority() {
        IceManager iceManager = new IceManager();
        for(int i = 0; i < _iceList.size(); i++) {
            Ice ice = _iceList.get(i);
            ice.setPriority(i);
            iceManager.updateIce(ice.getId(), ice.getName(), ice.getContactNo(), ice.getContactType(), ice.getDescription(), i, _context);
        }
    }
}
