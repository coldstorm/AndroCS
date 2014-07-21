package tk.coldstorm.androcs.messages;

import tk.coldstorm.androcs.Client;
import tk.coldstorm.androcs.models.irc.Message;

public interface ReceiveMessage {
    @SuppressWarnings("unused")
    void ProcessMessage(Message message, Client client);
}
