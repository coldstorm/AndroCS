package tk.coldstorm.androcs.messages.send;

import android.os.Parcel;

import java.io.PrintWriter;

import tk.coldstorm.androcs.messages.SendMessage;
import tk.coldstorm.androcs.models.irc.Channel;

public class JoinMessage implements SendMessage, android.os.Parcelable {
    public String channelName;

    public JoinMessage(String channelName) {
        this.channelName = channelName;
    }

    public JoinMessage(Channel channel) {
        this.channelName = channel.getFullName();
    }

    @Override
    public void Send(PrintWriter out) {
        out.println(String.format("JOIN %s", this.channelName));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.channelName);
    }

    private JoinMessage(Parcel in) {
        this.channelName = in.readString();
    }

    public static final Creator<JoinMessage> CREATOR = new Creator<JoinMessage>() {
        public JoinMessage createFromParcel(Parcel source) {
            return new JoinMessage(source);
        }

        public JoinMessage[] newArray(int size) {
            return new JoinMessage[size];
        }
    };
}
