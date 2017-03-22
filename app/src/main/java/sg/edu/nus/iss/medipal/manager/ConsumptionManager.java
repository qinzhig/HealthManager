package sg.edu.nus.iss.medipal.manager;


import android.content.Context;

import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.pojo.Consumption;

public class ConsumptionManager {
    private Consumption consumption;
    private ConsumptionDAO consumptionDAO;

    private Context context;

    public ConsumptionManager(Consumption consumption, ConsumptionDAO consumptionDAO, Context context) {
        this.consumption = consumption;
        this.consumptionDAO = consumptionDAO;
        this.context = context;
    }

    public ConsumptionManager(Context context) {

        this.context = context;
        consumptionDAO = new ConsumptionDAO(context);
    }
}
