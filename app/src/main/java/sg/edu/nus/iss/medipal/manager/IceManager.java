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
        Ice ice = new Ice(name, contactNo, contactType, description, priority);
        _iceDAO = new IceDAO(context);
        return _iceDAO.insert(ice);
    }

    public Ice getIce(Integer index, Context context) {
        _iceDAO = new IceDAO(context);
        return _iceDAO.retrieve().get(index);
    }

    public List<Ice> getIces(Context context) {
        _iceDAO = new IceDAO(context);
        return _iceDAO.retrieve();
    }

    public long deleteIce(String id, Context context) {
        _iceDAO = new IceDAO(context);
        return _iceDAO.delete(id);
    }

    public long deleteIce(Integer index, Context context) {
        Integer id = this.getIce(index, context).getId();
        return this.deleteIce(id.toString(), context);
    }

    public long updateIce(Integer id, String name, String contactNo, Integer contactType, String description, Integer priority, Context context) {
        _iceDAO = new IceDAO(context);
        Ice ice = new Ice(id, name, contactNo, contactType, description, priority);
        return _iceDAO.update(ice);
    }
}
