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
    protected void onPostExecute(ArrayList<Medicine> medicineList) {

//        Log.v("DEBUG","-------------------------Async onPostExcute Start++++++++++++++++++++++ ");
//        if (medicineList == null)
//        {
//            medicines = new ArrayList<Medicine>();
//        }
//        medicines = medicineList;
//        Log.v("DEBUG","-------------------------Async onPostExcute++++++++++++++++++++++ "+medicines.size());

    }

    public ArrayList<Medicine> getMedicineList() {

        Log.v("DEBUG","-------------------------Async getMedicineList Start++++++++++++++++++++++ "+medicines.size());

        Log.v("DEBUG","-------------------------Async getMedicineList++++++++++++++++++++++ "+medicines.toString());
        return this.medicines;
    }
}
