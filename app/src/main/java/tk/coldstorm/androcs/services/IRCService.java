package tk.coldstorm.androcs.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * A service that holds a connection to an IRC server and relays parsed messages to bound components.
 * */
public class IRCService extends Service {
    private final IBinder mBinder = new IRCBinder();

    /**
     * A Binder class used to expose the service to bound components.
     * */
    public class IRCBinder extends Binder {
        IRCService getService() {
            return IRCService.this;
        }
    }

    public IRCService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
