package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by apple on 07/03/2017.
 */

public class Consumption {
    private Integer id;
    private Integer medicineId;
    private Integer quantity;
    private Date consumedOn;

    public Consumption(Integer id, Integer medicineId, Integer quantity, Date consumedOn) {
        this.id = id;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.consumedOn = consumedOn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getQuality() {
        return quantity;
    }

    public void setQuality(Integer quality) {
        this.quantity = quality;
    }

    public Date getConsumedOn() {
        return consumedOn;
    }

    public void setConsumedOn(Date consumedOn) {
        this.consumedOn = consumedOn;
    }


}
