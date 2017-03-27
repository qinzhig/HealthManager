package sg.edu.nus.iss.medipal.asynTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.medipal.interfaces.AsyncTaskCallbacks;
import sg.edu.nus.iss.medipal.pojo.Appointment;

/**
 * Created by : Navi on 19-03-2017.
 * Description : This is the AsyncTask class for performing appointment table inserts
 * Modified by :
 * Reason for modification :
 */

public class AppointmentWriteAsyncTask extends AsyncTask<Object, Void, Long> {
    private ProgressDialog progressDialog;
    private AppointmentDAO appointmentDAO;
    private AsyncTaskCallbacks asyncTaskCallbacks;
    private Context mContext;
    private Boolean isAdd;

    public AppointmentWriteAsyncTask(Context context, AsyncTaskCallbacks asyncTaskCallbacks) {
        mContext = context;
        this.appointmentDAO = new AppointmentDAO(context);
        this.asyncTaskCallbacks = asyncTaskCallbacks;
        progressDialog = new ProgressDialog(context,R.style.AppTheme_Dark_Dialog);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressDialog != null) {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Saving...");
            progressDialog.show();
        }
    }

    @Override
    protected Long doInBackground(Object... args) {
        //delete operation
        if(args.length == 1)
        {
            int noOfRowsDeleted;
            noOfRowsDeleted = appointmentDAO.deleteAppointment((String)args[0]);
            return Long.valueOf(noOfRowsDeleted);
        }
        //addition or edit operation
        else {
            isAdd = (Boolean) args[1];
            Long retVal;

            if (isAdd)
                retVal = appointmentDAO.insert((Appointment) args[0]);
            else
                retVal = appointmentDAO.update((Appointment) args[0]);
            return retVal;
        }
    }

    @Override
    protected void onPostExecute(Long result) {
        Log.d("result in async",Long.toString(result));
        Boolean status = false;
        if(progressDialog != null) {
            //close dialog after 1 secs.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    ((Activity) mContext).finish();
                    Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                }
            }, 1000);

        }

        //valid appointment id is returned
        if (isAdd != null && isAdd && (result != -1))
            status = true;
        else if (result == 1){ //only one row is deleted or updated
                status = true;
        }
        //callback to manager class to perform post process tasks
        asyncTaskCallbacks.dbOperationStatus(status,result);

        if (appointmentDAO != null)
            appointmentDAO.close();

    }
}