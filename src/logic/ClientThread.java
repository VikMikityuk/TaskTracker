package logic;

import model.TaskModel;
import model.User;
import socket.Server;
import view.View;
import workWithFile.WorkWithXML;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    User user;
    Integer intInput;
    WorkWithXML workWithXML = new WorkWithXML();


    /**
     * Сохраняем сокет, пробуем создать читателя и писателя. Если не получается - вылетаем без создания объекта
     *
     * @param socketParam сокет
     * @throws IOException Если ошибка в создании bufferedReader || bufferedWriter
     */
    public ClientThread(Socket socketParam) throws IOException {
        socket = socketParam;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
    }


    /**
     * Главный цикл чтения сообщений/рассылки. "контроллер"
     */
    public void run() {
        send(View.hello());
        //тут начинается вся *магия* логика
        loginMenu();
    }

    public void loginMenu() {
        send(View.printValidationIDAsk());
        String strInput;
        try {
            strInput = bufferedReader.readLine();
        } catch (Exception e) {
            strInput = "q";
        }
        switch (strInput) {
            case "y":
                send(View.printValidationIDINPUTNAME("y"));
                validationLogin();
                break;
            case "n":
                send(View.printValidationIDINPUTNAME("n"));
                createUser();
                break;
            default:
                send(View.printErrorIncorrectValue());
                if (!socket.isClosed()) loginMenu();
        }
    }

    private void validationLogin() {
        String strInput;
        try {
            strInput = bufferedReader.readLine();
        } catch (IOException e) {
            send("Input Error..");
            strInput = null;
        }
        String uaerName = User.validationUser(strInput); // здесь проверка логина. Стрингу присваивается имя, которое проверили
        // если такое есть, то это имя, если нет то null
        if (uaerName != null) {// если имя есть, то
            try {
                if (workWithXML.unmarshallingUser(uaerName) == null) {
                    send("Error");
                    loginMenu();
                }
                user = workWithXML.unmarshallingUser(uaerName);
                //  System.out.println(user.taskHierarchy.toStringFromSend()); //for test
                mainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            send(View.printErrorIncorrectValue());
            if (!socket.isClosed()) loginMenu();
        }

    }

    private void createUser() {
        String strInput = null;
        try {
            strInput = bufferedReader.readLine();
        } catch (IOException e) {
            send("Input Error..");
        }
        if ("".equals(strInput)) {
            send(View.printErrorIncorrectValue());//если пользователь ввел пустое значение, то ошибка
            if (!socket.isClosed()) createUser();
        } else {
            try {
                user = new User(strInput);
                mainMenu();
            } catch (CloneNotSupportedException e) {
                send("Error");
                loginMenu();
            }

        }
    }


    public void mainMenu() {
        if (!socket.isClosed())
            View.printFirstMenu(user.getCurrentTask()).forEach(p -> send(p));
        try {
            intInput = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            intInput = 0;
        }
        switch (intInput) {
            case 1:
                brunchMenu(1);
                break;
            case 2:
                brunchMenu(2);
                break;
            case 3:
                viewStatistic();
                break;
            case 4:
                unLoginUser();
                break;
            case 5:
                exitUser();
                break;
            case 6: //only for test
                if (user.getLogTasks() == null) {
                    send("Log is Empty");
                } else user.getLogTasks().forEach(p -> {
                    send(p.toString());
                });
            default:
                send(View.printErrorIncorrectValue());
                if (!socket.isClosed()) mainMenu();
        }
    }


    /**
     * метод для 1 и 2-го пункта главного меню. Выводит вевтви иерархии задач
     *
     * @param q - номер пункта 1 или 2
     */
    private void brunchMenu(int q) {
        send(View.printBrunchMenu());
        user.taskHierarchy.toStringFromSend().forEach(p -> {
            send(p);
        });

        int i = user.taskHierarchy.getTaskHierarchyMap().size();//i- количество деревьев. Надо взять из файла  //user.taskHierarchy.getTaskHierarchyMap().size()
        System.out.println(i);
        try {
            intInput = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            intInput = 1234;
        }
        for (int k = 1; k <= i; k++) {
            if (intInput == k) taskModeMenu(k, q);
        }
        if (intInput == 100) {
            mainMenu();
            return;
        } else {
            send(View.printErrorIncorrectValue());
            if (!socket.isClosed()) brunchMenu(q);
        }
    }

    /**
     * следующее действие в пунктах 1 и 2. Показать список задач в данной ветке.
     *
     * @param i - это ид дерева задач
     * @param q - значение пункта меню- 1 или 2
     */
    private void taskModeMenu(int i, int q) {
        send(View.printTaskModelMenu());
        user.taskHierarchy.getTaskHierarchyMap().get(i).toStringFromSend().forEach(p -> {
            System.out.println(p);
            send(p);
        });
        int t = user.taskHierarchy.getTaskHierarchyMap().get(i).getTaskBrunchMap().size();   //t- количество задач в деревею
        System.out.println(t);
        try {
            intInput = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            intInput = 1234;
        }
        for (int k = 1; k <= t; k++) {
            if (intInput == k) {
                if (q == 1) updateCurrentTask(i, k);
                if (q == 2) updateNameOfTask(i, k);
                return;
            }
        }
        if (intInput == 100) {
            mainMenu();
            return;
        }
        if (intInput == 1000) {
            brunchMenu(q);
            return;
        } else {
            send(View.printErrorIncorrectValue());
            if (!socket.isClosed()) taskModeMenu(i, q);
        }
    }

    /**
     * обновление задачи, которой в данный момент занимается пользователь,  добавление задачи и времени начала в лог
     *
     * @param i - номер ветки иерархии
     * @param k - номер задачи в иерархии
     */

    private void updateCurrentTask(int i, int k) {
        try {
            TaskModel newTaskModel = user.taskHierarchy.getTaskHierarchyMap().get(i).getTaskBrunchMap().get(k).clone();
            if (user.addToLog(newTaskModel))
                send(View.printUpdateCurrentTask("You doing " + newTaskModel.getName() + "! yeeeeah!)"));
            else send(View.printUpdateCurrentTask("Error"));
            //mainMenu();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        try {
            intInput = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            intInput = 1234;
        }
        if ((intInput == 0) || !(intInput == 0)) {
            mainMenu();
        }
    }


    private void updateNameOfTask(int i, int k) {
        send(View.printUpdateNameOfTask());
        try {
            String u = bufferedReader.readLine();
            user.taskHierarchy.getTaskHierarchyMap().get(i).getTaskBrunchMap().get(k).setName(u);
        } catch (Exception e) {
        }
        send("you update name of task!");
        try {
            intInput = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            intInput = 123;
        }
        if ((intInput == 0) || !(intInput == 0)) {
            mainMenu();
        }
    }

    private void viewStatistic() {
        send(View.printSubMenuStatistic());
        try {
            intInput = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            intInput = 1234;
        }
        Statistics st = new Statistics();
        switch (intInput) {
            case 1:
                st.getStatisticOfUserAllTime(user).forEach(p -> send(p));
                break;
            case 2:
                st.getStatisticOfUserBrunch(user).forEach(p -> send(p));
                break;
            case 100:
                mainMenu();
                break;
            default:
                send(View.printErrorIncorrectValue());
                viewStatistic();
        }
    }


    private void exitUser() {
        try {
            workWithXML.marshallerUser(user, user.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        send("");
        this.closeSocket();
    }


    private void unLoginUser() {
        try {
            workWithXML.marshallerUser(user, user.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        user = null;
        loginMenu();
    }


    /**
     * Метод посылает в сокет полученную строку
     *
     * @param line строка на отсылку
     */
    public synchronized void send(String line) {
        try {
            if (line == null) bufferedWriter.write((char[]) null);
            else {
                bufferedWriter.write(line); // пишем строку
                bufferedWriter.write("\n"); // пишем перевод строки
                bufferedWriter.flush(); // отправляем
            }
        } catch (IOException e) {
            closeSocket(); //если глюк в момент отправки - закрываем данный сокет.
        }
    }

    /**
     * метод закрывает сокет и убирает его со списка активных сокетов
     */
    public synchronized void closeSocket() {
        Server.getListConnectedClient().remove(this); //убираем из списка
        if (!socket.isClosed()) {
            try {
                socket.close(); // закрываем
            } catch (IOException ignored) {
            }
        }
    }


    /**
     * финализатор просто на всякий случай.
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeSocket();
    }


}
