package sg.edu.nus.iss.medipal.pojo;

/**
 * Created by levis on 3/16/2017.
 * Description : Class to hold ICE(In case of Emergency Contact) table data
 */

public class Ice implements Comparable<Ice> {
    private Integer _id;
    private String _name;
    private String _contactNo;
    private Integer _contactType;
    private String _description;
    private Integer _priority;

    public Ice(String name, String contactNo, Integer contactType, String description, Integer priority) {
        this._name = name;
        this._contactNo = contactNo;
        this._contactType = contactType;
        this._description = description;
        this._priority = priority;
    }

    public Ice(Integer id, String name, String contactNo, Integer contactType, String description, Integer priority) {
        this._id = id;
        this._name = name;
        this._contactNo = contactNo;
        this._contactType = contactType;
        this._description = description;
        this._priority = priority;
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

    public Integer getContactType() {return _contactType;}
    public void setContactType(Integer contactType) {
        this._contactType = contactType;
    }

    public String getDescription() {return _description;}
    public void setDescription(String description) {this._description = description;}

    public Integer getPriority() {return _priority;}
    public void setPriority(Integer priority) {this._priority = priority;}

    @Override
    public int compareTo(Ice o) {
        return this._priority - ((Ice)o).getPriority();
    }
}
