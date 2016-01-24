package model;

import constant.Constants;
import workWithFile.WorkWithXML;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
public class User {
    @XmlElement(name = "UserName")
    private String name;
    private TaskModel currentTask;
    private List<TaskModel> logTasks;
    @XmlElement
    public TaskHierarchy taskHierarchy; //у каждого пользователя своя иерархия задач,у которой он может менять названия
    @XmlElement
    private File file;
    private transient WorkWithXML wwXML = new WorkWithXML();

    User() {
    }

    public User(String nm) throws CloneNotSupportedException {

        name = nm;
        logTasks = new ArrayList<>();
        Constants.usersList.add(nm);//userList из класса констант
        taskHierarchy = setMainTaskHierarchy();
        currentTask = taskHierarchy.getTaskHierarchyMap().get(2).getTaskBrunchMap().get(1).clone();
        logTasks.add(currentTask); //при создании у юзера по
        //умолчанию текущая задача- не работа "not working"
        file = new File("C://taskTracker//" + nm + ".xml");
        try {
            wwXML.marshallerUser(this, this.name);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private TaskHierarchy setMainTaskHierarchy() {
        TaskBrunch work = new TaskBrunch("Working");
        work.addTaskModel(1, new TaskModel("working", Constants.WORKINGID));
        work.addTaskModel(2, new TaskModel("meeting", Constants.MEETINGID));
        work.addTaskModel(3, new TaskModel("planning session", Constants.PLANNINGSESSIONID));

        TaskBrunch notWork = new TaskBrunch("NotWorking");
        notWork.addTaskModel(1, new TaskModel("not working", Constants.NOTWORKINGID));
        notWork.addTaskModel(2, new TaskModel("sleeping", Constants.SLEEPINGID));
        notWork.addTaskModel(3, new TaskModel("in house", Constants.INHOUSEID));


        TaskBrunch sport = new TaskBrunch("Sport");
        sport.addTaskModel(1, new TaskModel("swimming", Constants.SWIMMINGID));
        sport.addTaskModel(2, new TaskModel("gymnastic", Constants.GIMNASTICID));


        TaskHierarchy taskHierarchy = new TaskHierarchy("taskH");
        taskHierarchy.addTaskBrunch(1, work);
        taskHierarchy.addTaskBrunch(2, notWork);
        taskHierarchy.addTaskBrunch(3, sport);

        return taskHierarchy;
    }

    /**
     * Проверяем, если ли такой ник в списке constant.Constants.usersList
     *
     * @param str - строка, имя пользователя
     * @return если есть такой ник, то возвращаем объект этого пользователя, если нет то "null"
     */
    public static String validationUser(String str) {
        if (Constants.usersList.isEmpty() && Constants.usersList.size() <= 0) return null;
        if (str == null && str.equals("")) return null;

        for (int i = 0; i < Constants.usersList.size(); i++) {
            if ((Constants.usersList.get(i)).equals(str)) {
                return Constants.usersList.get(i);
            }
        }
        return null;
    }

    /**
     * Добавление задачи в список(logTasks)
     *
     * @param taskModel новая задача(currentTask)
     * @return результат добавления задачи.
     */
    public boolean addToLog(TaskModel taskModel) {
        if (logTasks == null) {
            logTasks = new ArrayList<>();
        }
        if (logTasks.add(taskModel)) {
            currentTask = taskModel;
            return true;
        } else return false;
    }




    public String getName() {
        return name;
    }

    public List<TaskModel> getLogTasks() {
        return logTasks;
    }

    @XmlElement
    @XmlElementWrapper
    public void setLogTasks(List<TaskModel> logTasks) {
        this.logTasks = logTasks;
    }

    public TaskModel getCurrentTask() {
        return currentTask;
    }
    @XmlElement
    public void setCurrentTask(TaskModel currentTask) {
        this.currentTask = currentTask;
    }
}
