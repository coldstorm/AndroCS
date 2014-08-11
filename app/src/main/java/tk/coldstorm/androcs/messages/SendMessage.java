package tk.coldstorm.androcs.messages;

import android.os.Parcelable;

import java.io.PrintWriter;

public interface SendMessage extends Parcelable {
    @SuppressWarnings("unused")
    void Send(PrintWriter out);
}
