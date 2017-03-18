package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.dao.MedicineDAO;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by zhiguo on 14/3/17.
 */

public class ListMedicine extends AsyncTask<Void, Void, ArrayList<Medicine>> {

    ArrayList<Medicine> medicines;
    private MedicineDAO medicineDAO;

    public ListMedicine(Context context) {
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected ArrayList<Medicine> doInBackground(Void... arg0) {
        //ArrayList<Medicine> medicineList = medicineDAO.getMedicines();
        medicines = medicineDAO.getMedicines();
        Log.v("DEBUG","-------------------------Async DoInBackgroud++++++++++++++++++++++ "+medicines.toString());
        return medicines;
    }

    @Override
    protected void onPostExecute(ArrayList<Medicine> medicines) {

        Log.v("DEBUG","-------------------------Async onPostExcute Start++++++++++++++++++++++ ");

        if(medicines == null){ medicines = new ArrayList<Medicine>();}
        medicineDAO.close();
        Log.v("DEBUG","-------------------------Async onPostExcute++++++++++++++++++++++ "+medicines.size());

    }

}
