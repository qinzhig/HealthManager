package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by zhiguo on 13/3/17.
 * Description : Define Medicine class details
 */

public class Medicine {

    private int id;
    private String medicine_name;
    private String medicine_des;
    private int cateId;
    private int reminderId;
    private boolean reminder;
    private int quantity;
    private int dosage;
    private Date dateIssued;
    private int expireFactor;


    public Medicine(int id,String medicine_name, String medicine_des, int cateId, int reminderId, Boolean reminder, int quantity, int dosage, Date dateIssued, int expireFactor){

        this.id=id;
        this.medicine_name=medicine_name;
        this.medicine_des=medicine_des;
        this.cateId=cateId;
        this.reminderId=reminderId;
        this.reminder=reminder;
        this.quantity=quantity;
        this.dosage=dosage;
        this.dateIssued=dateIssued;
        this.expireFactor=expireFactor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_des() {
        return medicine_des;
    }

    public void setMedicine_des(String medicine_des) {
        this.medicine_des = medicine_des;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }



}
