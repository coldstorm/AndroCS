package tk.coldstorm.androcs.messages;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.PrintWriter;

public class SendMessageImpl implements SendMessage, Parcelable {
    @Override
    public void Send(PrintWriter out) {
        // Empty send method
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public SendMessageImpl() {
    }

    private SendMessageImpl(Parcel in) {
    }

    public static final Parcelable.Creator<SendMessageImpl> CREATOR = new Parcelable.Creator<SendMessageImpl>() {
        public SendMessageImpl createFromParcel(Parcel source) {
            return new SendMessageImpl(source);
        }

        public SendMessageImpl[] newArray(int size) {
            return new SendMessageImpl[size];
        }
    };
}
