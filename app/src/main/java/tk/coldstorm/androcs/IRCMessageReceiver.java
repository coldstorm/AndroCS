package tk.coldstorm.androcs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.InvocationTargetException;

import tk.coldstorm.androcs.messages.RegisteredMessage;
import tk.coldstorm.androcs.models.irc.Message;

public class IRCMessageReceiver extends BroadcastReceiver {
    private Client mClient;
    public IRCMessageReceiver(Client client) {
        mClient = client;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Actually handle this intent...
        Message message = intent.getParcelableExtra(Constants.EXTRA_IRC_MESSAGE);

        for (RegisteredMessage registeredMessage : mClient.mRegisteredMessages) {
            try {
                if (registeredMessage.CheckMessage(message)) {
                    registeredMessage.ProcessMessage(message);
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
