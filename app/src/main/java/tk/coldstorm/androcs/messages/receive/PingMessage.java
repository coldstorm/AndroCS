package tk.coldstorm.androcs.messages.receive;

import tk.coldstorm.androcs.Client;
import tk.coldstorm.androcs.messages.ReceiveMessage;
import tk.coldstorm.androcs.messages.send.PongMessage;
import tk.coldstorm.androcs.models.irc.Message;
import tk.coldstorm.androcs.services.IRCService;

public class PingMessage implements ReceiveMessage {
    public static boolean CheckMessage(Message message, Client client) {
        return message.getCommand().equals("PING");
    }

    @Override
    public void ProcessMessage(Message message, Client client) {
        String source = message.getParameters()[0];

        client.onPing(source);
    }
}
