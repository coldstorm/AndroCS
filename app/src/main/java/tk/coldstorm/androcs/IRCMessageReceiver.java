package tk.coldstorm.androcs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IRCMessageReceiver extends BroadcastReceiver {
    public IRCMessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Actually handle this intent...
        Log.d("IRCMessageReceiver", "onReceive: " + intent.getStringExtra(Constants.EXTRA_IRC_MESSAGE));
    }
}
