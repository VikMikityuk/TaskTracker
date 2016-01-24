import taskTrackerController.ClientServer;


import java.io.IOException;

/**
 * Created by Snap on 03.01.2016.
 */
public class taskTrackerMain {
    public static void main(String[] args) throws IOException {

        ClientServer client = new ClientServer("localhost", 22222);
        client.run();


    }
}
