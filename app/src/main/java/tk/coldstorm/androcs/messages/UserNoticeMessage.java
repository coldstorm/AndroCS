package tk.coldstorm.androcs.messages;

import tk.coldstorm.androcs.Client;
import tk.coldstorm.androcs.models.irc.Message;

public class UserNoticeMessage implements ReceiveMessage {
    public static boolean CheckMessage(Message message, Client client) {
        return message.getCommand().equals("NOTICE");
    }

    @Override
    public void ProcessMessage(Message message, Client client) {
        client.onNotice(message);
    }
}
