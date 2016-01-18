package model;


import java.io.Serializable;
import java.util.*;

/**
 * Created by Snap on 12.01.2016.
 */
public class TaskBrunch implements Serializable {
    private String name;
    private Map<Integer, TaskModel> taskMap;
    private TaskHierarchy taskHierarchy;


    public TaskBrunch(String n) {
        name = n;
        taskMap = new HashMap<>();
    }


    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }


    public TaskHierarchy getTaskHierarchy() {
        return taskHierarchy;
    }

    public void setTaskHierarchy(TaskHierarchy taskHierarchy) {
        this.taskHierarchy = taskHierarchy;
    }

    public void addTaskModel(int i, TaskModel tm) {
        taskMap.put(i, tm); //тут как раз и происходит автоупаковка инт в интеджер
        tm.setTaskBrunch(this);
    }

    public Map<Integer, TaskModel> getTaskBrunch() {
        return taskMap;
    }

    public void removeTaskModel(TaskModel tm) {
        taskMap.remove(tm);
    }


    //@Override
    //  public String toString() {
    //    String str = null;
    //    for (int i = 0; i < taskMap.size(); i++) {
    //        str += i + " " + taskMap.get(i).toString();
    //    }
    //    return name +
    //            str;
    // }

    public List<String> toStringFromSend() {
        List<String> list = new ArrayList<>();
        Iterator<Map.Entry<Integer, TaskModel>> entries = taskMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, TaskModel> entry = entries.next();
            list.add("ID = " + entry.getKey() + " название задачи = " + entry.getValue().getName());
        }

        return list;
    }

    public int size() {
        return taskMap.size();
    }
}
