package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.pojo.Consumption;

/**
 * Created by apple on 22/03/2017.
 */

public class UpdateConsumption extends AsyncTask<Consumption, Void, Long> {
    Consumption consumption = null;
    private ConsumptionDAO consumptionDAO;

    public UpdateConsumption(Context context) {
        this.consumptionDAO = new ConsumptionDAO(context);
    }

    protected Long doInBackground(Consumption... params) {
        long result = consumptionDAO.update(params[0]);
        return result;
    }

    protected void onPostExecute(Long result) {
        if (result != -1)
        {
            Log.v("DBSave","____________________-------------------Update consumption successfully!");
        }

        if (consumptionDAO != null)
        {
            consumptionDAO.close();
        }

    }
}
