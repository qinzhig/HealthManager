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
        HealthBio healthBio =
                new HealthBio(condition, startDate, conditionType);
        healthBioDAO = new HealthBioDAO(context);
        return healthBioDAO.insert(healthBio);
    }

    public List<HealthBio> getHealthBio(Context context) {

        healthBioDAO = new HealthBioDAO(context);
        return healthBioDAO.retrieve();
    }

    public long deleteHealthBio(String id, Context context) {

        healthBioDAO = new HealthBioDAO(context);
        return healthBioDAO.delete(id);
    }

    public long updateHealthBio(String id,
                                String condition, Date startDate, char conditionType, Context context) {

        healthBioDAO = new HealthBioDAO(context);
        HealthBio healthBio =
                new HealthBio(Integer.valueOf(id), condition, startDate, conditionType);
        return healthBioDAO.update(healthBio);
    }
}
