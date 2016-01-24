package logic;

import constant.Constants;
import model.TaskModel;
import model.User;

import java.util.*;

/**
 * Created by Snap on 21.01.2016.
 */
public class Statistics {

    User user;
    long taskWork1 = 0;
    long taskWork2 = 0;
    long taskWork3 = 0;
    long taskNotWork1 = 0;
    long taskNotWork2 = 0;
    long taskNotWork3 = 0;
    long taskSport1 = 0;
    long taskSport2 = 0;

    /**
     * Получение статистики по всем задачам конкретного юзера
     *
     * @param user -для которого считаем статистику
     * @return
     */
    public List<String> getStatisticOfUserAllTime(User user) {
        this.user = user;
        List<TaskModel> list = user.getLogTasks();
        for (int i = 0; i < list.size() - 1; i++) {
            long ms = list.get(i + 1).getTimeStart().getTimeInMillis() - list.get(i).getTimeStart().getTimeInMillis();
            switch (list.get(i).getId()) {
                case Constants.WORKINGID:
                    taskWork1 += ms;
                    break;
                case Constants.MEETINGID:
                    taskWork2 += ms;
                    break;
                case Constants.PLANNINGSESSIONID:
                    taskWork3 += ms;
                    break;
                case Constants.NOTWORKINGID:
                    taskNotWork1 += ms;
                    break;
                case Constants.SLEEPINGID:
                    taskNotWork2 += ms;
                    break;
                case Constants.INHOUSEID:
                    taskNotWork3 += ms;
                    break;
                case Constants.SWIMMINGID:
                    taskSport1 += ms;
                    break;
                case Constants.GIMNASTICID:
                    taskSport2 += ms;
                    break;
                default:
            }
        }
        Map<String, Long> map = new HashMap<>();
        map.put("1.1", taskWork1);
        map.put("1.2", taskWork2);
        map.put("1.3", taskWork3);
        map.put("2.1", taskNotWork1);
        map.put("2.2", taskNotWork2);
        map.put("2.3", taskNotWork3);
        map.put("3.1", taskSport1);
        map.put("3.2", taskSport2);


        List<String> listStatistics = new ArrayList<>();
        Iterator<Map.Entry<String, Long>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Long> entry = entries.next();
            listStatistics.add("ID = " + entry.getKey() + " общее время " + entry.getValue());
        }
        return listStatistics;
    }

    long brunchWork = 0;
    long brunchNotWork = 0;
    long brunchSport = 0;

    /**
     * Подсчет статистики по веткам задач для конкретного юзера
     *
     * @param user
     * @return
     */
    public List<String> getStatisticOfUserBrunch(User user) {
        this.user = user;
        List<TaskModel> list = user.getLogTasks();
        for (int i = 0; i < list.size() - 1; i++) {
            long ms = list.get(i + 1).getTimeStart().getTimeInMillis() - list.get(i).getTimeStart().getTimeInMillis();
            switch (list.get(i).getId()) {
                case Constants.WORKINGID:
                    brunchWork += ms;
                    break;
                case Constants.MEETINGID:
                    brunchWork += ms;
                    break;
                case Constants.PLANNINGSESSIONID:
                    brunchWork += ms;
                    break;
                case Constants.NOTWORKINGID:
                    brunchNotWork += ms;
                    break;
                case Constants.SLEEPINGID:
                    brunchNotWork += ms;
                    break;
                case Constants.INHOUSEID:
                    brunchNotWork += ms;
                    break;
                case Constants.SWIMMINGID:
                    brunchSport += ms;
                    break;
                case Constants.GIMNASTICID:
                    brunchSport += ms;
                    break;
                default:
            }
        }
        Map<String, Long> map = new HashMap<>();
        map.put("1", brunchWork);
        map.put("2", brunchNotWork);
        map.put("3", brunchSport);

        List<String> listStatistics = new ArrayList<>();
        Iterator<Map.Entry<String, Long>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Long> entry = entries.next();
            listStatistics.add("ID = " + entry.getKey() + " общее время " + entry.getValue());
        }
        return listStatistics;
    }


}
