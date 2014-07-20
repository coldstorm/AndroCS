package tk.coldstorm.androcs.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import tk.coldstorm.androcs.Constants;
import tk.coldstorm.androcs.models.irc.User;

/**
 * An {@link IntentService} subclass for handling IRC tasks.
 */
public class IRCService extends IntentService {
    //region Actions
    private static final String ACTION_CONNECT = "tk.coldstorm.androcs.services.action.CONNECT";
    //endregion

    //region Parameters
    private static final String EXTRA_ADDRESS = "tk.coldstorm.androcs.services.extra.ADDRESS";
    private static final String EXTRA_PORT = "tk.coldstorm.androcs.services.extra.PORT";
    private static final String EXTRA_CLIENT = "tk.coldstorm.androcs.services.extra.CLIENT";
    //endregion

    //region Action Helpers
    /**
     * Starts this service to perform a Connect action to an IRC server with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @param context       The Context of the Intent
     * @param serverAddress The host of the server to connect to
     * @param serverPort    The port to connect to on the server
     * @param client        The User object to use in IRC registration
     * @see IntentService
     */
    public static void startActionConnect(Context context, String serverAddress, String serverPort, User client) {
        Log.d("IRCService", "startActionConnect");
        Intent intent = new Intent(context, IRCService.class);
        intent.setAction(ACTION_CONNECT);
        intent.putExtra(EXTRA_ADDRESS, serverAddress);
        intent.putExtra(EXTRA_PORT, serverPort);
        intent.putExtra(EXTRA_CLIENT, client);
        context.startService(intent);
    }
    //endregion

    public IRCService() {
        super("IRCService");
    }

    @Override
    public void onCreate() {
        Log.d("IRCService", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("IRCService", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("IRCService", "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CONNECT.equals(action)) {
                final String address = intent.getStringExtra(EXTRA_ADDRESS);
                final String port = intent.getStringExtra(EXTRA_PORT);
                final User client = intent.getParcelableExtra(EXTRA_CLIENT);
                handleActionConnect(address, port, client);
            }
        }
    }

    //region Action Handlers
    /**
     * Handle action Connect in the provided background thread with the provided
     * parameters.
     */
    private void handleActionConnect(String address, String port, User client) {
        // TODO: Handle action Connect

        // TODO: Make a parcelable Message model to send as EXTRA_IRC_MESSAGE
        Intent localIntent = new Intent(Constants.ACTION_IRC_MESSAGE_RECEIVED)
                .putExtra(Constants.EXTRA_IRC_MESSAGE, "test message");

        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
    //endregion
}
