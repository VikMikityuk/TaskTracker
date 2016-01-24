package socket;

import logic.ClientThread;
import constant.Constants;
import workWithFile.WorkWithXML;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Класс сервера. Сидит тихо на порту, принимает сообщение, создает logic.ClientThread на каждое сообщение
 */
public class Server {
    private ServerSocket serverSocket; // сам сервер-сокет
    private Thread serverThread = new Thread(); // главная нить обработки сервер-сокета
    private int port; // порт сервер сокета.


    static List<ClientThread> listConnectedClient = new ArrayList<>();

    /**
     * Конструктор объекта сервера
     *
     * @param port Порт, где будем слушать входящие сообщения.
     * @throws IOException Если не удасться создать сервер-сокет, вылетит по эксепшену, объект Сервера не будет создан
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port = port;
        Thread listenerServer = new Thread(new Listener());
        listenerServer.setDaemon(true);
        listenerServer.start();

    }

    /**
     * главный цикл прослушивания/ожидания коннекта.
     */
    public void run() {
        WorkWithXML workWithXML = new WorkWithXML();
        try {
            Constants.usersList = workWithXML.unmarshallingListClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        serverThread = Thread.currentThread();
        while (true) {
            Socket socket = getNewConn();
            if (serverThread.isInterrupted()) {
                break;
            } else if (socket != null) {
                try {
                    final ClientThread clientThread = new ClientThread(socket);
                    final Thread thread = new Thread(clientThread);
                    thread.setDaemon(true);
                    thread.start();
                    listConnectedClient.add(clientThread); //добавляем в список активных сокет-процессоров
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Ожидает новое подключение.
     *
     * @return Сокет нового подключения
     */
    private Socket getNewConn() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            shutdownServer();
        }
        return socket;
    }

    /**
     * метод "глушения" сервера
     */
    public synchronized void shutdownServer() {
        WorkWithXML workWithXML = new WorkWithXML();
        try {
            workWithXML.marshallerListClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // обрабатываем список рабочих коннектов, закрываем каждый
        listConnectedClient.forEach(ClientThread::closeSocket);
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }
            this.serverThread.interrupt();
        }
    }


    public static List<ClientThread> getListConnectedClient() {
        return listConnectedClient;
    }


    /**
     * Вложенный приватный класс асинхронного чтения с консоли сервера
     */
    private class Listener implements Runnable {
        public void run() {
            listener();
        }

        private void listener() {
            Scanner sc = new Scanner(System.in);
            String str;
            str = sc.nextLine();
            if ("exit".equals(str)) {
                shutdownServer();
                // listenerServer.interrupt();
            } else listener();
        }
    }

}


