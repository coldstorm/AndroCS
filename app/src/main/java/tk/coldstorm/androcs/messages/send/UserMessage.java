package tk.coldstorm.androcs.messages.send;

import java.io.PrintWriter;

import tk.coldstorm.androcs.messages.SendMessageImpl;
import tk.coldstorm.androcs.models.irc.User;

public class UserMessage extends SendMessageImpl {
    public String userName;
    public String realName;

    public UserMessage(String userName, String realName) {
        this.userName = userName;
        this.realName = realName;
    }

    public UserMessage(User user) {
        this.userName = user.getUserName();
        this.realName = user.getRealName();
    }

    @Override
    public void Send(PrintWriter out) {
        out.println(String.format("USER %s 0 - : %s", this.userName, this.realName));
    }
}
