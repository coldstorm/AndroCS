package tk.coldstorm.androcs.messages.receive;

import tk.coldstorm.androcs.Client;
import tk.coldstorm.androcs.messages.ReceiveMessage;
import tk.coldstorm.androcs.models.irc.Message;

public class ConnectionMessage implements ReceiveMessage {
    public static boolean CheckMessage(Message message, Client client) {
        return message.getCommand().equals("NOTICE") && message.getParameters()[0].startsWith("Auth") &&
                !client.isConnected();
    }

    @Override
    public void ProcessMessage(Message message, Client client) {
        client.onConnect();
    }
}
