package sg.edu.nus.iss.medipal.pojo;

import java.util.Date;

/**
 * Created by Divahar on 3/15/2017.
 */

public class HealthBio {

    private int id;
    private String condition;
    private Date startDate;
    private char conditionType;

    public HealthBio(String condition, Date startDate, char conditionType) {
        this.condition = condition;
        this.startDate = startDate;
        this.conditionType = conditionType;
    }

    public HealthBio(int id, String condition, Date startDate, char conditionType) {
        this.id = id;
        this.condition = condition;
        this.startDate = startDate;
        this.conditionType = conditionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public char getConditionType() {
        return conditionType;
    }

    public void setConditionType(char conditionType) {
        this.conditionType = conditionType;
    }
}
