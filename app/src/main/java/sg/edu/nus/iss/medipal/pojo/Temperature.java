package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by Divahar on 3/25/2017.
 *  Description: This class has the fields related to temperature measurement
 */

public class Temperature extends Measurement {

    private float temperature;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Temperature(Date measuredOn, float temperature) {
        super(measuredOn);
        this.temperature = temperature;
    }
}
