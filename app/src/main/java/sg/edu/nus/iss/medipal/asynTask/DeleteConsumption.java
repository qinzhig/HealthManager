package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.pojo.Consumption;

/**
 * Created by apple on 22/03/2017.
 */

public class DeleteConsumption extends AsyncTask<Object,Void,Long> {

    Consumption consumption = null;
    private ConsumptionDAO consumptionDAO;

    public DeleteConsumption(Context context) {
        this.consumptionDAO = new ConsumptionDAO(context);
    }
    @Override
    protected Long doInBackground(Object... params) {
        long result = consumptionDAO.delete((Integer) params[0]);
        return result;
    }

    protected void onPostExecute(Long result) {
        if (result != -1)
        {
            Log.v("DBSave","____________________-------------------Delete Consumption successfully!");
        }

        if (consumptionDAO != null)
        {
            consumptionDAO.close();
        }

    }
}
