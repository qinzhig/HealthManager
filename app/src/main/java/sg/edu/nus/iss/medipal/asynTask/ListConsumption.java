package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.pojo.Consumption;

/**
 * Created by apple on 22/03/2017.
 */

public class ListConsumption extends AsyncTask<Void, Void, ArrayList<Consumption>> {

    ArrayList<Consumption> consumptions;
    private ConsumptionDAO consumptionDAO;

    public ListConsumption (Context context) {
        this.consumptionDAO = new ConsumptionDAO(context);
    }

    protected ArrayList<Consumption> doInBackground(Void... params) {
        consumptions = consumptionDAO.getConsumptions();
        return consumptions;
    }

    protected void onPostExecute(ArrayList<Consumption> consumptionList) {
        consumptions = consumptionList;

        if (consumptionList == null) { consumptions = new ArrayList<Consumption>(); }

        consumptionDAO.close();

    }
}
