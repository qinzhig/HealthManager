package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by divah on 3/25/2017.
 */

public class Pulse extends Measurement{

    private int pulse;

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public Pulse(int id, Date measuredOn, int pulse) {
        super(id, measuredOn);
        this.pulse = pulse;
    }

    public Pulse(Date measuredOn, int pulse) {
        super(measuredOn);
        this.pulse = pulse;
    }
}
