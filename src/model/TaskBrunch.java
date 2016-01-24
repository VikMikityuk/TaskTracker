package model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;


@XmlRootElement
public class TaskBrunch implements Serializable {

    private String name;
    private Map<Integer, TaskModel> taskBrunchMap;
    private String taskHierarchy;


    TaskBrunch() {
    }
    public TaskBrunch(String name) {
        this.name = name;
        taskBrunchMap = new HashMap<>();
    }


    public void addTaskModel(int i, TaskModel tm) {
        taskBrunchMap.put(i, tm); //тут как раз и происходит автоупаковка инт в интеджер
        tm.setTaskBrunch(this);
    }


    @XmlElement(name = "TaskBrunchName")
    public void setName(String newName) {
        name = newName;
    }
    public String getName() {
        return name;
    }


    public String getTaskHierarchy() {
        return taskHierarchy;
    }
    @XmlElement(name = "TaskHierarchyOfBrunch")
    public void setTaskHierarchy(TaskHierarchy taskHierarchy) {
        this.taskHierarchy = taskHierarchy.getName();
    }

    public void setTaskBrunchMap(Map<Integer, TaskModel> taskBrunchMap) {
        this.taskBrunchMap = taskBrunchMap;
    }
    @XmlElement
    @XmlElementWrapper
    public Map<Integer, TaskModel> getTaskBrunchMap() {
        return taskBrunchMap;
    }


    public void removeTaskModel(TaskModel tm) {
        taskBrunchMap.remove(tm);
    }


    public List<String> toStringFromSend() {
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<Integer, TaskModel>> entries = taskBrunchMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, TaskModel> entry = entries.next();
            list.add("ID = " + entry.getKey() + " название задачи = " + entry.getValue().getName());
        }

        return list;
    }

    public int size() {
        return taskBrunchMap.size();
    }
}
