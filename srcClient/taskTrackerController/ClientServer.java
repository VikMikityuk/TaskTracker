package taskTrackerController;

import java.io.*;
import java.net.Socket;

/**
 * Created by Snap on 10.01.2016.
 */
public class ClientServer {
    final Socket socket;
    final BufferedReader socketReader;
    final BufferedWriter socketWriter;
    final BufferedReader userInput;


    /**
     * Конструктор объекта клиента
     *
     * @param host - IP адрес или localhost или доменное имя
     * @param port - порт, на котором висит сервер
     * @throws java.io.IOException - если не смогли приконнектиться, кидается исключение, чтобы
     *                             предотвратить создание объекта
     */
    public ClientServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        userInput = new BufferedReader(new InputStreamReader(System.in));
        new Thread(new Receiver()).start();
    }

    /**
     * метод, где происходит главный цикл чтения сообщений с консоли и отправки на сервер
     */
    public void run() {
        System.out.println("Type phrase(socket):");
        while (true) {
            String userString = null;
            try {
                userString = userInput.readLine();
            } catch (IOException ignored) {
            }
            if (socket.isClosed()) {
                close();
            }
            if (userString == null && "".equals(userString)) {
                System.out.println("try again");
            } else {
                try {
                    socketWriter.write(userString);
                    socketWriter.write("\n");
                    socketWriter.flush();
                } catch (IOException e) {
                    System.out.println("Error");
                    close();
                }
            }
        }
    }

    /**
     * метод закрывает коннект и выходит из
     * программы (это единственный  выход прервать работу BufferedReader.readLine(), на ожидании пользователя)
     */
    public synchronized void close() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                System.exit(0);
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }


    /**
     * Вложенный приватный класс асинхронного чтения
     */
    private class Receiver implements Runnable {
        /**
         * run() вызовется после запуска нити из конструктора клиента чата.
         */
        public void run() {
            while (!socket.isClosed()) {
                String line = null;
                try {
                    line = socketReader.readLine();
                } catch (IOException e) {
                    if ("Socket closed".equals(e.getMessage())) {
                        break;
                    }
                    System.out.println("Connection lost");
                    close();
                }
                if (line == null) {
                    System.out.println("Server has closed connection");
                    close();
                } else {
                    System.out.println("Server:" + line);
                }
            }
        }
    }
}
