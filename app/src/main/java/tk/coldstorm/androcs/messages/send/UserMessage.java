package tk.coldstorm.androcs.messages.send;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.PrintWriter;

import tk.coldstorm.androcs.messages.SendMessage;
import tk.coldstorm.androcs.models.irc.User;

public class UserMessage implements SendMessage {
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

    @Override
    public int describeContents() {
        return 0;
    }

    private UserMessage(Parcel in) {
        userName = in.readString();
        realName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(realName);
    }

    public static final Parcelable.Creator<UserMessage> CREATOR
            = new Parcelable.Creator<UserMessage>() {
        public UserMessage createFromParcel(Parcel in) {
            return new UserMessage(in);
        }

        public UserMessage[] newArray(int size) {
            return new UserMessage[size];
        }
    };
}
