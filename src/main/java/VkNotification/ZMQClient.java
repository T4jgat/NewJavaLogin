package VkNotification;

import org.zeromq.ZMQ;

public class ZMQClient {
    public static void main(String[] args) {
        // Initialize the ZeroMQ context and socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.PUB);

        // Connect to the ZeroMQ messaging service
        socket.connect("tcp://127.0.0.1:5555");

        // Send a message to the ZeroMQ socket
        socket.send("true");

        // Clean up the ZeroMQ context and socket
        socket.close();
        context.term();

    }
}