package tk.coldstorm.androcs.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.*;

import tk.coldstorm.androcs.Constants;
import tk.coldstorm.androcs.messages.SendMessage;
import tk.coldstorm.androcs.messages.send.NickMessage;
import tk.coldstorm.androcs.messages.send.UserMessage;
import tk.coldstorm.androcs.models.irc.Message;
import tk.coldstorm.androcs.models.irc.User;

/**
 * An {@link IntentService} subclass for handling IRC tasks.
 */
public class IRCService extends IntentService {
    //region Actions
    private static final String ACTION_CONNECT = "tk.coldstorm.androcs.services.action.CONNECT";
    private static final String ACTION_SEND = "tk.coldstorm.androcs.services.action.SEND";
    //endregion

    //region Parameters
    private static final String EXTRA_ADDRESS = "tk.coldstorm.androcs.services.extra.ADDRESS";
    private static final String EXTRA_PORT = "tk.coldstorm.androcs.services.extra.PORT";
    private static final String EXTRA_CLIENT = "tk.coldstorm.androcs.services.extra.CLIENT";

    private static final String EXTRA_MESSAGE = "tk.coldstorm.androcs.services.extra.MESSAGE";
    //endregion

    //region Log
    private static final String NAME = "IRCService";
    //endregion

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    private static Socket mSocket;
    private static PrintWriter mOut;
    private static BufferedReader mIn;

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
    public static void startActionConnect(Context context, String serverAddress, int serverPort, User client) {
        Intent intent = new Intent(context, IRCService.class);
        intent.setAction(ACTION_CONNECT);
        intent.putExtra(EXTRA_ADDRESS, serverAddress);
        intent.putExtra(EXTRA_PORT, serverPort);
        intent.putExtra(EXTRA_CLIENT, client);
        context.startService(intent);
    }

    public static void startActionSend(Context context, SendMessage message) {
        Intent intent = new Intent(context, IRCService.class);
        intent.setAction(ACTION_SEND);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.startService(intent);
    }
    //endregion

    public IRCService() {
        super(NAME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CONNECT.equals(action)) {
                String address = intent.getStringExtra(EXTRA_ADDRESS);
                int port = intent.getIntExtra(EXTRA_PORT, 6660);
                User client = intent.getParcelableExtra(EXTRA_CLIENT);

                startConnectThread(address, port, client);
            } else if (ACTION_SEND.equals(action)) {
                final SendMessage message = intent.getParcelableExtra(EXTRA_MESSAGE);

                executor.submit(new Runnable() {
                    public void run() {
                        handleActionSend(message);
                    }
                });
            }
        }
    }

    public static void startConnectThread(final String address, final int port, final User client) {
        new Thread(new Runnable() {
            public void run() {
             handleActionConnect(address, port, client);
            }
        }).start();
    }

    //region Action Handlers
    /**
     * Handle action Connect in the provided background thread with the provided
     * parameters.
     */
    private static void handleActionConnect(String address, int port, User client) {
        // Create a socket
        try {
            mSocket = new Socket(address, port);
            mOut = new PrintWriter(mSocket.getOutputStream(), true);
            mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

            new NickMessage(client).Send(mOut);
            new UserMessage(client).Send(mOut);
            Log.d(NAME, "Connection opened");
        } catch (UnknownHostException ex) {
            Log.e(NAME, ex.getMessage());
        } catch (IOException ex) {
            Log.e(NAME, ex.getMessage());
        }

        // Keep the connection
        while (mSocket.isConnected() &&
                !mSocket.isInputShutdown() &&
                !mSocket.isOutputShutdown()) {
            String line = "";
            try {
                line = mIn.readLine();
            } catch (IOException ex) {
                Log.e(NAME, ex.getMessage());
            }

            if (line == null) {
                break;
            }

            if (!line.isEmpty()) {
                Message parsedMessage = new Message(line);
                Intent localIntent = new Intent(Constants.ACTION_IRC_MESSAGE_RECEIVED)
                        .putExtra(Constants.EXTRA_IRC_MESSAGE, parsedMessage);

                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            }
        }

        Log.d(NAME, "Connection closed.");
    }

    private static void handleActionSend(SendMessage message) {
        message.Send(mOut);
    }
    //endregion
}
