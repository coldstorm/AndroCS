package tk.coldstorm.androcs.helpers;

import android.util.Log;

import java.io.OutputStream;
import java.io.PrintWriter;

public class IRCWriter extends PrintWriter {
    public IRCWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    @Override
    public void println(String str) {
        Log.d("IRCWriter", String.format("< %s", str));
        super.println(str);
    }
}
