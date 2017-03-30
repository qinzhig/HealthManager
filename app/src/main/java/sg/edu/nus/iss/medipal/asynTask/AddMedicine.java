package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sg.edu.nus.iss.medipal.dao.MedicineDAO;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by zhiguo on 14/3/17.
 */

public class AddMedicine extends AsyncTask<Medicine, Void, Long> {


    Medicine medicine = null;
    private MedicineDAO medicineDAO;

    public AddMedicine(Context context) {
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected Long doInBackground(Medicine... params) {
        long result = medicineDAO.insert(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
        {
            Log.v("DBSave","____________________-------------------Save Medicine successfully!");
        }

        if (medicineDAO != null)
        {
            medicineDAO.close();
        }

    }

}
