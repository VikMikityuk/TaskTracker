import logic.DataLists;
import model.User;
import socket.Server;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        new User("snap");//for test
        new Server(22222).run();
    }
}
