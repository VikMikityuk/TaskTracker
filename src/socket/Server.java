package socket;

import logic.ClientThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс сервера. Сидит тихо на порту, принимает сообщение, создает logic.ClientThread на каждое сообщение
 */
public class Server {
    private ServerSocket ss; // сам сервер-сокет
    private Thread serverThread; // главная нить обработки сервер-сокета
    private int port; // порт сервер сокета.


    //лист, где храняться все logic.ClientThread'ы для рассылки
   static List<ClientThread> listClient = new ArrayList<>();

    /**
     * Конструктор объекта сервера
     *
     * @param port Порт, где будем слушать входящие сообщения.
     * @throws IOException Если не удасться создать сервер-сокет, вылетит по эксепшену, объект Сервера не будет создан
     */
    public Server(int port) throws IOException {
        ss = new ServerSocket(port);
        this.port = port;
    }

    /**
     * главный цикл прослушивания/ожидания коннекта.
     */
    public void run() {
        serverThread = Thread.currentThread(); // со старта сохраняем нить (чтобы можно ее было interrupt())
        while (true) { //бесконечный цикл
            Socket s = getNewConn(); // получить новое соединение или фейк-соедиение
            if (serverThread.isInterrupted()) { // если это фейк-соединение, то наша нить была interrupted(),
                // надо прерваться
                break;
            } else

            if (s != null) { // "только если коннект успешно создан"...
                try {
                    final ClientThread processor = new ClientThread(s); // создаем поток клиента
                    final Thread thread = new Thread(processor); // создаем отдельную асинхронную нить чтения из сокета
                    thread.setDaemon(true); //ставим ее в демона (чтобы не ожидать ее закрытия)
                    thread.start(); //запускаем
                    listClient.add(processor); //добавляем в список активных сокет-процессоров
                }
                catch (IOException ignored) {
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
        Socket s = null;
        try {
            s = ss.accept();
        } catch (IOException e) {
            shutdownServer(); // если ошибка в момент приема - "гасим" сервер
        }
        return s;
    }

    /**
     * метод "глушения" сервера
     */
    private synchronized void shutdownServer() {
        // обрабатываем список рабочих коннектов, закрываем каждый
        listClient.forEach(ClientThread::close);
        if (!ss.isClosed()) {
            try {
                ss.close();
            } catch (IOException ignored) {
            }
        }
    }


    public static List<ClientThread> getListClient() {
        return listClient;
    }

}


