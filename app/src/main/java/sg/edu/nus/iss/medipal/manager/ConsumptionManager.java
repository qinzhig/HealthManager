package sg.edu.nus.iss.medipal.manager;


import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.medipal.asynTask.AddConsumption;
import sg.edu.nus.iss.medipal.asynTask.DeleteConsumption;
import sg.edu.nus.iss.medipal.asynTask.ListConsumption;
import sg.edu.nus.iss.medipal.asynTask.UpdateConsumption;
import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by : Ma teng on 07-03-2017.
 * Description : This is the manager class for consumption
 * Modified by : Naval on 25-03-2017
 * Reason for modification :
 */
public class ConsumptionManager {
    private ArrayList<Consumption> consumptions;

    private AddConsumption addConsumption;
    private ListConsumption listConsumption;
    private UpdateConsumption updateConsumption;
    private DeleteConsumption deleteConsumption;


    private Context context;

    public ConsumptionManager(Context context) {

        this.context = context;
    }


    public Consumption getConsumption(int id){

        Iterator<Consumption> i = consumptions.iterator();

        while(i.hasNext()){
            Consumption c = i.next();
            if( c.getId() == id)
            {
                return c;
            }
        }

        return null;
    }

    public ArrayList<Consumption> getConsumptions(String date) {
       ConsumptionDAO consumptionDAO = new ConsumptionDAO(context);
        ArrayList<Consumption> consumptionList;

            consumptionList =consumptionDAO.getConsumptions(date);

        if(consumptionList == null){
            consumptionList = new ArrayList<Consumption>();
        }

        return consumptionList;
    }

    public ArrayList<Consumption> getConsumptions() {
        listConsumption = new ListConsumption(context);
        listConsumption.execute((Void)null);

        try{
            consumptions = listConsumption.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if(consumptions == null){
            consumptions = new ArrayList<Consumption>();
        }

        return consumptions;
    }


    public Consumption addConsumption(int id, int medicine_id,int quantity,String ConsumedOn){

        Consumption consumption = new Consumption(id, medicine_id,quantity,ConsumedOn);

        addConsumption = new AddConsumption(context);
        try {
            addConsumption.execute(consumption).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return consumption;

    }

    public Consumption updateConsumption(int id, int medicine_id, int quantity,String ConsumedOn){

        Consumption consumption = new Consumption(id,medicine_id, quantity,ConsumedOn);

        updateConsumption = new UpdateConsumption(context);
        updateConsumption.execute(consumption);

        return consumption;
    }

    public void deleteConsumption(int id){

       // Consumption m = getConsumption(id);

        //if(m != null)
        //{
            deleteConsumption = new DeleteConsumption(context);
            deleteConsumption.execute(id);
        //}

    }




}
