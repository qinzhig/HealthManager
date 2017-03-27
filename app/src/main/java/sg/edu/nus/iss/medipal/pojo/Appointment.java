package sg.edu.nus.iss.medipal.pojo;

/**
 * Created by : Navi on 06-03-2017.
 * Description : Class to hold medicine table data
 * Modified by :
 * Reason for modification :
 */


public class Appointment {
    private Integer id;
    private String location;
    private String appointment;
    private String description;

    public Appointment(Integer id, String location, String appointment, String description) {
        this.id = id;
        this.location = location;
        this.appointment = appointment;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
