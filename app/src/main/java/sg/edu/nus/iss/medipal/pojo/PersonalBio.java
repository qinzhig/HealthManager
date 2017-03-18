package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by Divahar on 3/15/2017.
 */

public class PersonalBio {

    private int id;
    private String name;
    private Date dob;
    private String idNo;
    private String address;
    private int postalCode;
    private int height;
    private String bloodType;

    public PersonalBio(int id, String name, Date dob, String idNo, String address, int postalCode, int height, String bloodType) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.idNo = idNo;
        this.address = address;
        this.postalCode = postalCode;
        this.height = height;
        this.bloodType = bloodType;
    }

    public PersonalBio(String name, Date dob, String idNo, String address, int postalCode, int height, String bloodType) {
        this.name = name;
        this.dob = dob;
        this.idNo = idNo;
        this.address = address;
        this.postalCode = postalCode;
        this.height = height;
        this.bloodType = bloodType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
