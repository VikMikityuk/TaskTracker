package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;

@XmlRootElement
public class TaskModel implements Cloneable, Serializable {
    String name;
    Calendar timeStart;
    String taskBrunch;
    int id;

    TaskModel() {
    }

    public TaskModel(String name, int id) {
        this.id = id;
        this.name = name;
        timeStart = Calendar.getInstance();
    }

    /**
     * Метод клонирования задачи, с новым(текущим) временем
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public TaskModel clone() throws CloneNotSupportedException {
        TaskModel tm = (TaskModel) super.clone();
        tm.timeStart = Calendar.getInstance();
        return tm;
    }


    public Calendar getTimeStart() {
        return timeStart;
    }

    @XmlElement
    public void setTimeStart(Calendar timeStart) {
        this.timeStart = timeStart;
    }

    public String getTaskBrunch() {
        return taskBrunch;
    }

    @XmlElement(name = "TaskBrunchOfModel")
    public void setTaskBrunch(TaskBrunch taskBrunch) {
        this.taskBrunch = taskBrunch.getName();
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "TaskModelName")
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + "   " + timeStart.get(Calendar.HOUR) + ":" + timeStart.get(Calendar.MINUTE) + ":" + timeStart.get(Calendar.SECOND);
    }
}
