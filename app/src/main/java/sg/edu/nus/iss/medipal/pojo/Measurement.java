package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by divah on 3/25/2017.
 */

public class Measurement {

    private int id;
    private Date measuredOn;

    public int getId() {
        return id;
    }

    public Date getMeasuredOn() {
        return measuredOn;
    }

    public Measurement(int id, Date measuredOn) {
        this.id = id;
        this.measuredOn = measuredOn;
    }

    public Measurement(Date measuredOn) {
        this.measuredOn = measuredOn;
    }
}
