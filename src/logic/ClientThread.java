package logic;

import model.TaskModel;
import model.User;
import socket.Server;
import view.View;
import workWithFile.WorkWithFile;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    Socket s; // наш сокет
    BufferedReader br; // буферизировнный читатель сокета
    BufferedWriter bw; // буферизированный писатель в сокет

    User user;//объкт пользователь
    Integer a;//для реализации контроллера


    /**
     * Сохраняем сокет, пробуем создать читателя и писателя. Если не получается - вылетаем без создания объекта
     *
     * @param socketParam сокет
     * @throws IOException Если ошибка в создании br || bw
     */
    public ClientThread(Socket socketParam) throws IOException {
        s = socketParam;
        br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
    }


    /**
     * Главный цикл чтения сообщений/рассылки. "контроллер"
     */
    public void run() {
        send(View.hello());
        //тут начинается вся *магия* логика
        login();
    }

    public void login() {// есть ли логин? да- авторизация, нет-создание нового и записать его в список
        send(View.printValidationIDAsk());
        String u;
        try {
            u = br.readLine();
        } catch (Exception e) {
            u = "q";
        }
        switch (u) {
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
                if (!s.isClosed()) login();
        }
    }

    private void validationLogin() {
        String u;
        try {
            u = br.readLine();
        } catch (IOException e) {
            send("Input Error..");
            u = null;
        }
        String str = User.validationUser(u); // здесь проверка логина. Стрингу присваивается имя, которое проверили
        // если такое есть, то это имя, если нет то null
        if (str != null) {// если имя есть, то
            WorkWithFile wwfile = new WorkWithFile(); //считываем из файла все данные о юзере.
            wwfile.setTaskFileJM(new File("C://taskTracker//" + str + ".out"));
            try {
                user = (User) wwfile.validDeser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            firstMenu();
        } else {
            send(View.printErrorIncorrectValue());
            if (!s.isClosed()) login();
        }

    }

    private void createUser() {
        String u = null;
        try {
            u = br.readLine();
        } catch (IOException e) {
            send("Input Error..");
        }
        if (u == null && u.equals("")) {
            send(View.printErrorIncorrectValue());//TODO если ентер то оно не работает, исправить
            if (!s.isClosed()) createUser();
        } else {
            user = new User(u);
            // DataLists.usersList.forEach(p-> System.out.println(p)); //ДЛя проверки добавления
            firstMenu();
        }
    }


    public void firstMenu() {
        if (!s.isClosed())
            send(View.printFirstMenu());
        try {
            a = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            a = 0;
        }
        switch (a) {
            case 1:
                menuOfItemOneOrTwo(1);
                break;
            case 2:
                menuOfItemOneOrTwo(2);
                break;
            case 3:
                viewStatistic();
                break;
            case 4:
                user.saveToFile();
                user = null;
                login();
                break;
            case 5:
                user.saveToFile();
                send("");//TODO оно работает,но понять как оно работает о_0
                this.close();
                break;
            case 6:
                user.getLog().forEach(p -> {
                    send(p.toString());
                });
            default:
                send(View.printErrorIncorrectValue());
                if (!s.isClosed()) firstMenu();
        }
    }

    /**
     * метод для 1 и 2-го пункта главного меню. Выводит вевтви иерархии задач
     *
     * @param q - номер пункта 1 или 2
     */
    private void menuOfItemOneOrTwo(int q) {
        send(View.printMenuOfItemOneOrTwo());
        user.taskHierarchy.toStringFromSend().forEach(p -> {
            send(p);
        });

        int i = user.taskHierarchy.getTaskHierarchyMap().size();//i- количество деревьев. Надо взять из файла  //user.taskHierarchy.getTaskHierarchyMap().size()
        System.out.println(i);
        try {
            a = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            a = 1234;
        }
        for (int k = 1; k <= i; k++) {
            if (a == k) subMenuOfItemOneOrTwo(k, q);
        }
        if (a == 100) {
            firstMenu();
            return;
        } else {
            send(View.printErrorIncorrectValue());
            if (!s.isClosed()) menuOfItemOneOrTwo(q);
        }
    }

    /**
     * следующее действие в пунктах 1 и 2. Показать список задач в данной ветке.
     *
     * @param i - это ид дерева задач
     * @param q - значение пункта меню- 1 или 2
     */
    private void subMenuOfItemOneOrTwo(int i, int q) {
        send(View.printSubMenuOfItemOneOrTwo());
        user.taskHierarchy.getTaskHierarchyMap().get(i).toStringFromSend().forEach(p -> {
            System.out.println(p);
            send(p);
        });
        int t = user.taskHierarchy.getTaskHierarchyMap().get(i).getTaskBrunch().size();   //t- количество задач в деревею
        System.out.println(t);
        try {
            a = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            a = 1234;
        }
        for (int k = 1; k <= t; k++) {
            if (a == k) {
                if (q == 1) updateCurrentTask(i, k);
                if (q == 2) updateNameOfTask();
                return;
            }
        }
        if (a == 100) {
            firstMenu();
            return;
        }
        if (a == 1000) {
            menuOfItemOneOrTwo(q);
            return;
        } else {
            send(View.printErrorIncorrectValue());
            if (!s.isClosed()) subMenuOfItemOneOrTwo(i, q);
        }
    }

    /**
     * обновление задачи, которой в данный момент занимается пользователь,  добавление задачи и времени начала в лог
     *
     * @param i - номер ветки иерархии
     * @param k - номер задачи в иерархии
     */

    private void updateCurrentTask(int i, int k) {//TODO тут вообще должна быть логика обновления текущего дела,и запись его в бд.
        try {
            TaskModel newTaskModel = user.taskHierarchy.getTaskHierarchyMap().get(i).getTaskBrunch().get(k).clone();
            if (user.addToLog(newTaskModel)) send(View.printUpdateCurrentTask("You doing "+newTaskModel.getName()+"! yeeeeah!)"));
            else send(View.printUpdateCurrentTask("Error"));
            //firstMenu();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        try {
            a = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            a = 1234;
        }
        if ((a == 0) || !(a == 0)) {
            firstMenu();
        }
    }


    private void updateNameOfTask() { //TODO логика по изменению имени задачи
        send(View.printUpdateNameOfTask());
        try {
            a = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            a = 1234;
        }
        if ((a == 0) || !(a == 0)) {
            firstMenu();
        }
    }

    private void viewStatistic() {
        send(View.printSubMenuStatistic());
        try {
            a = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            a = 1234;
        }
        switch (a) {
            //  case 1:

            //  break;
            // case 2:

            //    break;
            case 100:
                firstMenu();
                break;
            default:
                send(View.printErrorIncorrectValue());
                viewStatistic();
        }
    }


    /**
     * Метод посылает в сокет полученную строку
     *
     * @param line строка на отсылку
     */
    public synchronized void send(String line) {
        try {
            if (line == null) bw.write((char[]) null);//без этого оно выдает NPE //TODO понять почему ошибка
            else {
                bw.write(line); // пишем строку
                bw.write("\n"); // пишем перевод строки
                bw.flush(); // отправляем
            }
        } catch (IOException e) {
            close(); //если глюк в момент отправки - закрываем данный сокет.
        }
    }

    /**
     * метод закрывает сокет и убирает его со списка активных сокетов
     */
    public synchronized void close() {
        Server.getListClient().remove(this); //убираем из списка
        if (!s.isClosed()) {
            try {
                s.close(); // закрываем
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
        close();
    }


}
