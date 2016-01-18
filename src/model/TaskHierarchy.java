package model;

import java.io.*;
import java.util.*;

/**
 * Created by Snap on 12.01.2016.
 */
public class TaskHierarchy implements Cloneable, Serializable {
    private String name;
    private Map<Integer, TaskBrunch> taskHierarchyMap;


    public TaskHierarchy(String n) {
        name = n;
        taskHierarchyMap = new HashMap<>();
    }

    public void setName(String newName) {
        name = newName;

    }


    public String getName() {
        return name;
    }

    public void addTaskBrunch(int i, TaskBrunch tb) {
        taskHierarchyMap.put(i, tb);
        tb.setTaskHierarchy(this);
    }


    public Map<Integer, TaskBrunch> getTaskHierarchyMap() {
        return taskHierarchyMap;
    } //получаем из объекта ТаскИерархия мапу с TaskBrunch'ами

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
