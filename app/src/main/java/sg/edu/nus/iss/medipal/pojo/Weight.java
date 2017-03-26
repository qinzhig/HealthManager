package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by divah on 3/25/2017.
 */

public class Weight extends Measurement{

    private int weight;

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Weight(int id, Date measuredOn, int weight) {
        super(id, measuredOn);
        this.weight = weight;
    }

    public Weight(Date measuredOn, int weight) {
        super(measuredOn);
        this.weight = weight;
    }
}
