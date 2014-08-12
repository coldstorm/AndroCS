package tk.coldstorm.androcs;

import android.app.Activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import tk.coldstorm.androcs.messages.ReceiveMessage;
import tk.coldstorm.androcs.messages.RegisteredMessage;
import tk.coldstorm.androcs.messages.receive.ConnectionMessage;
import tk.coldstorm.androcs.messages.receive.UserNoticeMessage;
import tk.coldstorm.androcs.models.irc.Message;

public class Client {
    protected OnClientEventListener mListener;
    protected ArrayList<RegisteredMessage> mRegisteredMessages;

    private boolean connected;

    public boolean isConnected() {
        return connected;
    }

    private void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Client(Activity activity) throws NoSuchMethodException {
        mListener = (OnClientEventListener) activity;
        mRegisteredMessages = new ArrayList<RegisteredMessage>();

        this.RegisterMessage(UserNoticeMessage.class);
        this.RegisterMessage(ConnectionMessage.class);
    }

    public void RegisterMessage(Class messageClass) throws NoSuchMethodException {
        mRegisteredMessages.add(new RegisteredMessage(this, messageClass));
    }

    public void onNotice(Message message) {
        if (mListener != null) {
            mListener.onNotice(message);
        }
    }

    public void onConnect() {
        if (mListener != null) {
            mListener.onConnect();
            setConnected(true);
        }
    }

    public interface OnClientEventListener {
        public void onNotice(Message message);
        public void onConnect();
    }
}
