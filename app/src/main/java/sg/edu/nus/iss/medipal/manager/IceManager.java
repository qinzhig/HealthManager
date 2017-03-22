package sg.edu.nus.iss.medipal.manager;

import android.content.Context;

import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.dao.IceDAO;
import sg.edu.nus.iss.medipal.pojo.Ice;

/**
 * Created by levis on 3/23/2017.
 */

public class IceManager {

    IceDAO _iceDAO = null;

    public long addIce(String name, String contactNo, int contactType, String description, Context context) {
        Ice ice = new Ice(name, contactNo, contactType, description);
        _iceDAO = new IceDAO(context);
        return _iceDAO.insert(ice);
    }

    public List<Ice> getIce(Context context) {
        _iceDAO = new IceDAO(context);
        return _iceDAO.retrieve();
    }

    public long deleteIce(String id, Context context) {
        _iceDAO = new IceDAO(context);
        return _iceDAO.delete(id);
    }

    public long updateHealthBio(String id, String name, String contactNo, int contactType, String description, Context context) {
        _iceDAO = new IceDAO(context);
        Ice ice = new Ice(Integer.valueOf(id), name, contactNo, contactType, description);
        return _iceDAO.update(ice);
    }
}
