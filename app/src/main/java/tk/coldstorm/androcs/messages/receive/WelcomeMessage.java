package tk.coldstorm.androcs.messages.receive;

import tk.coldstorm.androcs.Client;
import tk.coldstorm.androcs.messages.ReceiveMessage;
import tk.coldstorm.androcs.models.irc.Message;

public class WelcomeMessage implements ReceiveMessage {
    public static boolean CheckMessage(Message message, Client client) {
        return message.getCommand().equals("001");
    }

    @Override
    public void ProcessMessage(Message message, Client client) {
        client.onWelcome();
    }
}
