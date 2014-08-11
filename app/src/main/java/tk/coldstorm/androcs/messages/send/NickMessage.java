package tk.coldstorm.androcs.messages.send;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.PrintWriter;

import tk.coldstorm.androcs.messages.SendMessage;
import tk.coldstorm.androcs.models.irc.User;

public class NickMessage implements SendMessage {
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

    @Override
    public int describeContents() {
        return 0;
    }

    public NickMessage(Parcel in) {
        this.nickName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nickName);
    }

    public static final Parcelable.Creator<NickMessage> CREATOR
            = new Parcelable.Creator<NickMessage>() {
        public NickMessage createFromParcel(Parcel in) {
            return new NickMessage(in);
        }

        public NickMessage[] newArray(int size) {
            return new NickMessage[size];
        }
    };
}
