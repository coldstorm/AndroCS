package tk.coldstorm.androcs.messages.send;

import android.os.Parcel;

import java.io.PrintWriter;

import tk.coldstorm.androcs.messages.SendMessage;

public class PongMessage implements SendMessage, android.os.Parcelable {
    public String target;

    public PongMessage(String target) {
        this.target = target;
    }

    @Override
    public void Send(PrintWriter out) {
        out.println(String.format("PONG :%s", target));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.target);
    }

    private PongMessage(Parcel in) {
        this.target = in.readString();
    }

    public static final Creator<PongMessage> CREATOR = new Creator<PongMessage>() {
        public PongMessage createFromParcel(Parcel source) {
            return new PongMessage(source);
        }

        public PongMessage[] newArray(int size) {
            return new PongMessage[size];
        }
    };
}
