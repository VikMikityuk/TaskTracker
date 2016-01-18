package model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Snap on 12.01.2016.
 */
public class TaskModel implements Cloneable, Serializable {
    String name;
    Calendar timeStart;
    TaskBrunch taskBrunch;

    public TaskModel(String name) {
        this.name = name;
        timeStart = Calendar.getInstance();
    }


    public Calendar getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Calendar timeStart) {
        this.timeStart = timeStart;
    }

    public TaskBrunch getTaskBrunch() {
        return taskBrunch;
    }

    public void setTaskBrunch(TaskBrunch taskBrunch) {
        this.taskBrunch = taskBrunch;
    }

    public TaskModel clone() throws CloneNotSupportedException {
        TaskModel tm = (TaskModel) super.clone();
        tm.timeStart = Calendar.getInstance();
        return tm;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {//TODO
        return name +"   " +timeStart.get(Calendar.HOUR)+":" + timeStart.get(Calendar.MINUTE)+":" + timeStart.get(Calendar.SECOND);
    }
}
