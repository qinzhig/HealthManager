package sg.edu.nus.iss.medipal.pojo;

import java.io.Serializable;

/**
 * Created by zhiguo on 13/3/17.
 * Description : Define Medicine class details
 */

public class Medicine implements Serializable {

    private int id;
    private String medicine_name;
    private String medicine_des;
    private int cateId;
    private int reminderId;
    private boolean reminder;
    private int quantity;
    private int dosage;
    private int consumequantity;
    private int threshold;
    private String dateIssued;
    private int expireFactor;


    public Medicine(int id, String medicine_name, String medicine_des, int cateId, int reminderId, Boolean reminder, int quantity, int dosage, int cquantity, int threshold, String dateIssued, int expireFactor){

        this.id=id;
        this.medicine_name=medicine_name;
        this.medicine_des=medicine_des;
        this.cateId=cateId;
        this.reminderId=reminderId;
        this.reminder=reminder;
        this.quantity=quantity;
        this.dosage=dosage;
        this.consumequantity=cquantity;
        this.threshold=threshold;
        this.dateIssued=dateIssued;
        this.expireFactor=expireFactor;
    }

    public Medicine() {

    }

    public int getConsumequantity() {
        return consumequantity;
    }

    public void setConsumequantity(int consumequantity) {
        this.consumequantity = consumequantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
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

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }

    public String toString () {
        return ("ID: "+getId()+" /Name: "+getMedicine_name()+" /Des: "+getMedicine_des()+" /catId: "+getCateId()+" /quantity: "+getQuantity()+" /Dosage: "+getDosage()+" /reminderId: "+getReminderId()+" /Date: "+getDateIssued()+" /expireFactor: "+getExpireFactor());
    }


}
