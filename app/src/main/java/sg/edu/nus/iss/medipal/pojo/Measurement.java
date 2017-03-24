package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by levis on 3/16/2017.
 * Description : Class to hold measurement table data
 */

public class Measurement {
    private Integer _id;
    private Integer _systolic;
    private Integer _diastolic;
    private Integer _pulse;
    private Float _temperature;
    private Integer _weight;
    private String _measuredOn;

    public Measurement(Integer systolic, Integer diastolic, Integer pulse, Float temperature, Integer weight, String mesuredOn) {
        this._systolic = systolic;
        this._diastolic = diastolic;
        this._pulse = pulse;
        this._temperature = temperature;
        this._weight = weight;
        this._measuredOn = mesuredOn;
    }

    public Measurement(Integer id, Integer systolic, Integer diastolic, Integer pulse, Float temperature, Integer weight, String mesuredOn) {
        this._id = id;
        this._systolic = systolic;
        this._diastolic = diastolic;
        this._pulse = pulse;
        this._temperature = temperature;
        this._weight = weight;
        this._measuredOn = mesuredOn;
    }

    public Integer getId() {
        return _id;
    }
    public void setId(Integer id) { this._id = id; }

    public Integer getSystolic() {
        return _systolic;
    }
    public void setSystolic(Integer systolic) {
        this._systolic = systolic;
    }

    public Integer getDiastolic() {
        return _diastolic;
    }
    public void setDiastolic(Integer diastolic) {
        this._diastolic = diastolic;
    }

    public Integer getPulse() { return _pulse; }
    public void setPulse(Integer pulse) { this._pulse = pulse; }

    public Float getTemperature() {
        return _temperature;
    }
    public void setTemperature(Float temperature) { this._temperature = temperature; }

    public Integer getWeight() { return _weight; }
    public void setWeight(Integer weight) { this._weight = weight; }

    public String getMeasuredOn() { return _measuredOn; }
    public void setMeasuredOn(String measuredOn) { this._measuredOn = measuredOn; }
}
