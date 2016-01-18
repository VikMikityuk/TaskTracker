package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import logic.DataLists;
import workWithFile.WorkWithFile;

/**
 * Created by Snap on 11.01.2016.
 */
public class User implements Serializable {
    private String name;
    private TaskModel currentTask;
    private List<TaskModel> log;
    public TaskHierarchy taskHierarchy; //у каждого пользователя своя иерархия задач,у которой он может менять названия

    private File file;
    private WorkWithFile wwfile = new WorkWithFile();


    public User(String nm) {
        name = nm;
        log = new ArrayList<>();
        DataLists.usersList.add(nm);
        taskHierarchy = setMainTaskHierarchy();
        file = new File("C://taskTracker//" + nm + ".out");
        wwfile.setTaskFileJM(file);
        try {
            wwfile.serJM(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTask = taskHierarchy.getTaskHierarchyMap().get(2).getTaskBrunch().get(1); //при создании у юзера по
        //умолчанию текущая задача- не работа "not working"
    }


    public List<TaskModel> getLog() {
        return log;
    }


    private TaskHierarchy setMainTaskHierarchy() {
        TaskBrunch work = new TaskBrunch("Working");
        work.addTaskModel(1, new TaskModel("working"));
        work.addTaskModel(2, new TaskModel("meeting"));
        work.addTaskModel(3, new TaskModel("planning session"));

        TaskBrunch notWork = new TaskBrunch("NotWorking");
        notWork.addTaskModel(1, new TaskModel("not working"));
        notWork.addTaskModel(2, new TaskModel("sleeping"));
        notWork.addTaskModel(3, new TaskModel("in house"));


        TaskBrunch sport = new TaskBrunch("Sport");
        sport.addTaskModel(1, new TaskModel("swimming"));
        sport.addTaskModel(2, new TaskModel("gymnastics"));


        TaskHierarchy taskHierarchy = new TaskHierarchy("taskH");
        taskHierarchy.addTaskBrunch(1, work);
        taskHierarchy.addTaskBrunch(2, notWork);
        taskHierarchy.addTaskBrunch(3, sport);

        return taskHierarchy;
    }

    /**
     * Проверяем, если ли такой ник в списке DataLists.usersList
     *
     * @param str - строка, имя пользователя
     * @return если есть такой ник, то возвращаем объект этого пользователя, если нет то "null"
     */
    public static String validationUser(String str) {
        if (DataLists.usersList.isEmpty() && DataLists.usersList.size() <= 0) return null;
        if (str == null && str.equals("")) return null;

        for (int i = 0; i < DataLists.usersList.size(); i++) {
            if ((DataLists.usersList.get(i)).equals(str)) {
                return DataLists.usersList.get(i);
            }
        }

        //listUsers.forEach(p -> {
        //   if ((p.name).equals(str)) return true;//TODO почему тут он выдает ошибку?
        //});
        return null;
    }


    public boolean addToLog(TaskModel taskModel) {
        if (log.add(taskModel)) {
            currentTask = taskModel;
            return true;
        } else return false;
    }


    public void saveToFile() {
        try {
            wwfile.serJM(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
