package tk.coldstorm.androcs.messages.send;

import java.io.PrintWriter;

import tk.coldstorm.androcs.messages.SendMessageImpl;
import tk.coldstorm.androcs.models.irc.User;

public class NickMessage extends SendMessageImpl {
    public String nickName;

    public NickMessage(String nickName) {
        this.nickName = nickName;
    }

    public NickMessage(User user) {
        this.nickName = user.getNickName();
    }

    @Override
    public void Send(PrintWriter out) {
        out.println(String.format("NICK %s", this.nickName));
    }
}
