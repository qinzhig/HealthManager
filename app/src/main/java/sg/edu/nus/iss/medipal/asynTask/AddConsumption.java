package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.pojo.Consumption;

/**
 * Created by apple on 22/03/2017.
 */

public class AddConsumption extends AsyncTask<Consumption,Void,Long> {

    Consumption consumption = null;
    private ConsumptionDAO consumptionDAO;

    public AddConsumption(Context context) {
        this.consumptionDAO = new ConsumptionDAO(context);
    }

    protected Long doInBackground(Consumption... params) {
        long result = consumptionDAO.insert(params[0]);
        return result;
    }

    protected void onPostExecute(Long result) {
        if (result != -1) {
            Log.v("DBSave","____________________++++++++++++++++++++Add consumption successfully!");
        }
        if (consumptionDAO != null) {
            consumptionDAO.close();
        }
    }
}
