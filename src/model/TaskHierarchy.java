package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.*;

@XmlRootElement
public class TaskHierarchy implements Cloneable {
    private String name;
    private Map<Integer, TaskBrunch> taskHierarchyMap;


    TaskHierarchy() {
    }

    public TaskHierarchy(String n) {
        name = n;
        taskHierarchyMap = new HashMap<>();
    }

    public void addTaskBrunch(int i, TaskBrunch tb) {
        taskHierarchyMap.put(i, tb);
        tb.setTaskHierarchy(this);

    }

    @XmlElement(name = "THierarchyName")
    public void setName(String newName) {
        name = newName;

    }
    public String getName() {
        return name;
    }

    @XmlElement
    @XmlElementWrapper
    public void setTaskHierarchyMap(Map<Integer, TaskBrunch> taskHierarchyMap) {
        this.taskHierarchyMap = taskHierarchyMap;
    }
    public Map<Integer, TaskBrunch> getTaskHierarchyMap() {
        return taskHierarchyMap;
    }


    public void removeTaskBrunch(TaskBrunch tb) {
        taskHierarchyMap.remove(tb);
    }

    public List<String> toStringFromSend() {
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<Integer, TaskBrunch>> entries = this.getTaskHierarchyMap().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, TaskBrunch> entry = entries.next();
            list.add("ID = " + entry.getKey() + " название ветки задач = " + entry.getValue().getName());
        }
        return list;
    }
}
