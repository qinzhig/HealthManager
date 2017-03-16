package sg.edu.nus.iss.medipal.pojo;

/**
 * Created by levis on 3/16/2017.
 * Description : Class to hold ICE(In case of Emergency Contact) table data
 * Modified by :
 * Reason for modification :
 */

public class Ice {
    private Integer _id;
    private String _name;
    private String _contactNo;
    private Integer _contactType;
    private String _description;

    public Ice(Integer id, String name, String contactNo, Integer contactType, String description) {
        this._id = id;
        this._name = name;
        this._contactNo = contactNo;
        this._contactType = contactType;
        this._description = description;
    }

    public Integer getId() {
        return _id;
    }
    public void setId(Integer id) { this._id = id; }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        this._name = name;
    }

    public String getContactNo() {
        return _contactNo;
    }
    public void setContactNo(String contactNo) {
        this._contactNo = contactNo;
    }

    public Integer getContactType() {
        return _contactType;
    }
    public void setContactType(Integer contactType) {
        this._contactType = contactType;
    }

    public String getDescription() {return _description;}
    public void setDesciption(String description) {this._description = description;}
}

