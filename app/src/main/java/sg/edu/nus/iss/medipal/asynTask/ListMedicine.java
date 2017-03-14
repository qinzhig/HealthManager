package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;

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
        ArrayList<Medicine> medicineList = medicineDAO.getMedicines();
        return medicineList;
    }

    @Override
    protected void onPostExecute(ArrayList<Medicine> memList) {
        medicines = memList;
        if (memList == null) { medicines = new ArrayList<Medicine>(); }

    }

    public ArrayList<Medicine> getMedicineList() {
        return this.medicines;
    }
}
