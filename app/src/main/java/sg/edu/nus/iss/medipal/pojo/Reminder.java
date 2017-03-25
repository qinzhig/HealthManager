package sg.edu.nus.iss.medipal.pojo;

/**
 * Created by zhiguo on 19/3/17.
 */

public class Reminder {

    private int id;
    private int frequency ;
    private String startTime ;
    private int interval;

    public Reminder(int id, int frequency, String startTime, int interval) {
        this.id = id;
        this.frequency = frequency;
        this.startTime = startTime;
        this.interval = interval;
    }

    public Reminder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String toString () {

        return (Integer.toString(this.id) + " /" + Integer.toString(this.frequency) + " /" + this.startTime + " /" + this.interval);
    }


}
