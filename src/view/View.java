package view;

import model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class View {


    public static String hello() {
        System.out.println("new user connected");
        return "HELLO!" +
                "You are in TaskTracker. version 1.0.";
    }


    public static String printValidationIDAsk() {
        return "Have you username? (Y/N)";
    }

    /**
     * Метод для проверки пользователя. Если  параметр "y"-то дальнейшая проверка, если "n" -то пользователь вводит
     * имя для последующей авторизации. З.ы. описать методо покорректрее.
     *
     * @param s - принимает 2 возможных значения.
     *          "y"-пользователь зарегестрирован в системе. Просьба ввести его UserName
     *          "n"-пользователь не зарегестрирован.Просьба ввести имя для регистрации
     */
    public static String printValidationIDINPUTNAME(String s) {
        if ("y".equals(s.toLowerCase())) {
            System.out.println("User registered");
            return "Enter you Username";
        }
        if ("n".equals(s.toLowerCase())) {
            System.out.println("New user");
            return "Enter username for registration";
        }
        return "error";
    }


    public static String printErrorIncorrectValue() {
        System.out.println("user input incorrect value");
        return "You enter incorrect value. Please, try again.";
    }

    public static List<String> printFirstMenu(TaskModel taskModel) {
        System.out.println("User in main menu");
        List<String> listString=new ArrayList<>();
        listString.add("1.View and choose the hierarchy of tasks." );
        listString.add("2. Edit the hierarchy of tasks.");
        listString.add("3.view.View Statistics.");
        listString.add("4.Logout." );
        listString.add("5.Exit.");
        listString.add("Current task:"+ taskModel.toString());
        return listString;

    }

    //для 1-го пункта меню
    public static String printBrunchMenu() {
        System.out.println("user In Brunch Menu");
        return "100.Exit to first menu." +
                "Select group of tasks:";
    }

    public static String printTaskModelMenu() {
        System.out.println("user in sub brunch menu");
        return "1000.exit "+
                "100.Exit to first menu." +
                "Select tasks:";
    }

    public static String printUpdateCurrentTask(String s) {
        System.out.println(" user doing " + s + "!");
        return " Now you doing " + s + "!"+
                "100.Exit to first menu.";
    }


//для 2-го пункта меню

    public static String printUpdateNameOfTask() {
        System.out.println("user renamed task");
        return " Task rename ";
    }


    //Для 3-го пункта меню
    public static String printSubMenuStatistic() {
        return "Select presentation of statistics: 1. my all statistic; 2.statistic of branches";
    }


    public static void printErrorIncorrectFile() {
        System.out.println("file not found");
    }
}
