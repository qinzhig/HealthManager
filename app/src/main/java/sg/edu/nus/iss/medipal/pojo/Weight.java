package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by Divahar on 3/25/2017.
 *  Description: This class has the fields related to weight measurement
 */

public class Weight extends Measurement{

    private int weight;

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Weight(Date measuredOn, int weight) {
        super(measuredOn);
        this.weight = weight;
    }
}
