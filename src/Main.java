import model.User;
import socket.Server;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    static Server svr;

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        new User("snap");//for test
        svr = new Server(22222);
        svr.run();


    }

}
