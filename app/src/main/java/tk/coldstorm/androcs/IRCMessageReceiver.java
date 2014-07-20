package tk.coldstorm.androcs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import tk.coldstorm.androcs.models.irc.Message;

public class IRCMessageReceiver extends BroadcastReceiver {
    public IRCMessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Actually handle this intent...
        Message message = intent.getParcelableExtra(Constants.EXTRA_IRC_MESSAGE);
        Log.d("IRCMessageReceiver", "onReceive: " + message.getPrefix() + " " + message.getCommand());
    }
}
