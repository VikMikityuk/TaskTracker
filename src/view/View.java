package view;

public class View {


    public static String hello() {
        System.out.println("new user connected");
        return "HELLO!" +
                "You are in TaskTracker. version 1.0.";
    }


    public static String printValidationIDAsk() {
        System.out.println("Have you username? (Y/N)");
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
            System.out.println("Enter you Username");
            return "Enter you Username";
        }
        if ("n".equals(s.toLowerCase())) {
            System.out.println("Enter username for registration");
            return "Enter username for registration";
        }
        return "error";
    }


    public static String printErrorIncorrectValue() {
        System.out.println("You enter incorrect value. Please, try again.");
        return "You enter incorrect value. Please, try again.";
    }

    public static String printFirstMenu() {
        System.out.println("1.View and choose the hierarchy of tasks.");
        System.out.println("2. Edit the hierarchy of tasks.");
        System.out.println("3.view.View Statistics.");
        System.out.println("4.Logout.");
        System.out.println("5.Exit.");
        System.out.println("Current task:");
        return "1.View and choose the hierarchy of tasks." +
                "2. Edit the hierarchy of tasks." +
                "3.view.View Statistics." +
                "4.Logout." +
                "5.Exit." +
                "Current task:";

    }

    //для 1-го пункта меню
    public static String printMenuOfItemOneOrTwo() {
        System.out.println("100.Exit to first menu.");
        System.out.println("Select group of tasks:");
        //TODO -вывести группу задач
        return "100.Exit to first menu." +
                "Select group of tasks:";
    }

    public static String printSubMenuOfItemOneOrTwo() {
        System.out.println("1000.exit ");
        System.out.println("100.Exit to first menu.");
        System.out.println("Select tasks:");
        //TODO вывести полное дерево задач
        return "1000.exit "+
                "100.Exit to first menu." +
                "Select tasks:";
    }

    public static String printUpdateCurrentTask(String s) {
        System.out.println(" Now you doing " + s + "!");
        System.out.println("100.Exit to first menu.");
        return " Now you doing " + s + "!"+
                "100.Exit to first menu.";
    }


//для 2-го пункта меню

    public static String printUpdateNameOfTask() {
        System.out.println(" Task rename ");
        System.out.println("100.Exit to first menu.");
        return " Task rename ";
    }


    //Для 3-го пункта меню
    public static String printSubMenuStatistic() {
        System.out.println("Select presentation of statistics");
        System.out.println("1.");
        System.out.println("2.");
        return "Select presentation of statistics";
    }


    public static void printErrorIncorrectFile() {
        System.out.println("file not found");
    }
}
