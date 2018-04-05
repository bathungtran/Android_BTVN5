package bathung.com.btvn_5;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by HH on 4/3/2018.
 */

public class Task extends RealmObject implements Serializable {
    private String Name;
    private Date DueTo;
    private String Priority;
    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Task(){

     }

    public Task(String name, Date dueTo, String priority,boolean done ) {
        Name = name;
        DueTo = dueTo;
        Priority = priority;
        isDone=done;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDueTo() {
        return DueTo;
    }

    public void setDueTo(Date dueTo) {
        DueTo = dueTo;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }
}
