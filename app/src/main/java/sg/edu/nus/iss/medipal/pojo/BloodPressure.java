package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by divah on 3/25/2017.
 */

public class BloodPressure extends Measurement{

    private int systolic;
    private int diastolic;

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public BloodPressure(int id, Date measuredOn, int systolic, int diastolic) {
        super(id, measuredOn);
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public BloodPressure(Date measuredOn, int systolic, int diastolic) {
        super(measuredOn);
        this.systolic = systolic;
        this.diastolic = diastolic;
    }
}
