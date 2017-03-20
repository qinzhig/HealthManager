package sg.edu.nus.iss.medipal.interfaces;

import java.util.ArrayList;

/**
 * Created by : Navi on 19-03-2017.
 * Description : This interface to communicate status from async db task
 * Modified by :
 * Reason for modification :
 */


public interface AsyncTaskCallbacks {
    void dbOperationStatus(boolean resultStatus, Long resultValue);
}
