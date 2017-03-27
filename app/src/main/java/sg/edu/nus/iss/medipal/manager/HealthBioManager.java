package sg.edu.nus.iss.medipal.manager;

import android.content.Context;

import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.dao.HealthBioDAO;
import sg.edu.nus.iss.medipal.pojo.HealthBio;

/**
 * Created by Divahar on 3/17/2017.
 * Description: This manager class takes care of all the operations between Activity and DAO for HealthBio
 */

public class HealthBioManager {

    HealthBioDAO healthBioDAO = null;

    public long addHealthBio(String condition, Date startDate,
                             char conditionType, Context context) {
        long result;
        HealthBio healthBio =
                new HealthBio(condition, startDate, conditionType);
        healthBioDAO = new HealthBioDAO(context);
        result = healthBioDAO.insert(healthBio);
        healthBioDAO.close();
        return result;
    }

    public List<HealthBio> getHealthBio(Context context) {
        List<HealthBio> healthBioList;
        healthBioDAO = new HealthBioDAO(context);
        healthBioList=healthBioDAO.retrieve();
        healthBioDAO.close();
        return healthBioList;
    }

    public long deleteHealthBio(String id, Context context) {
        Long result;
        healthBioDAO = new HealthBioDAO(context);
        result=healthBioDAO.delete(id);
        healthBioDAO.close();
        return result;
    }

    public long updateHealthBio(String id,
                                String condition, Date startDate, char conditionType, Context context) {
        Long result;
        healthBioDAO = new HealthBioDAO(context);
        HealthBio healthBio =
                new HealthBio(Integer.valueOf(id), condition, startDate, conditionType);
        result = healthBioDAO.update(healthBio);
        healthBioDAO.close();
        return result;
    }
}
