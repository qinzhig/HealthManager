package sg.edu.nus.iss.medipal.manager;

import android.content.Context;

import java.util.List;

import sg.edu.nus.iss.medipal.dao.IceDAO;
import sg.edu.nus.iss.medipal.pojo.Ice;

/**
 * Created by levis on 3/23/2017.
 */

public class IceManager {

    IceDAO _iceDAO = null;

    public long addIce(String name, String contactNo, Integer contactType, String description, Integer priority, Context context) {
        long result;
        Ice ice = new Ice(name, contactNo, contactType, description, priority);
        _iceDAO = new IceDAO(context);
        result = _iceDAO.insert(ice);
        _iceDAO.close();
        return result;
    }

    public Ice getIce(Integer index, Context context) {
        Ice ice;
        _iceDAO = new IceDAO(context);
        ice=_iceDAO.retrieve().get(index);
        _iceDAO.close();
        return ice;
    }

    public List<Ice> getIces(Context context) {
        List<Ice> iceList;
        _iceDAO = new IceDAO(context);
        iceList=_iceDAO.retrieve();
        _iceDAO.close();
        return iceList;
    }

    public long deleteIce(String id, Context context) {
        long result;
        _iceDAO = new IceDAO(context);
        result=_iceDAO.delete(id);
        _iceDAO.close();
        return result;
    }

    public long updateIce(Integer id, String name, String contactNo, Integer contactType, String description, Integer priority, Context context) {
        long result;
        _iceDAO = new IceDAO(context);
        Ice ice = new Ice(id, name, contactNo, contactType, description, priority);
        result = _iceDAO.update(ice);
        _iceDAO.close();
        return result;
    }
}
