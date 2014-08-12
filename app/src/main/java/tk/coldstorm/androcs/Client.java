package tk.coldstorm.androcs;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import tk.coldstorm.androcs.messages.RegisteredMessage;
import tk.coldstorm.androcs.messages.SendMessage;
import tk.coldstorm.androcs.messages.receive.PingMessage;
import tk.coldstorm.androcs.messages.receive.WelcomeMessage;
import tk.coldstorm.androcs.messages.receive.UserNoticeMessage;
import tk.coldstorm.androcs.models.irc.Message;

public class Client {
    protected OnClientEventListener mListener;
    protected ArrayList<RegisteredMessage> mRegisteredMessages;

    public Client(Activity activity) throws NoSuchMethodException {
        mListener = (OnClientEventListener) activity;
        mRegisteredMessages = new ArrayList<RegisteredMessage>();

        this.RegisterMessage(UserNoticeMessage.class);
        this.RegisterMessage(WelcomeMessage.class);
        this.RegisterMessage(PingMessage.class);
    }

    public void RegisterMessage(Class messageClass) throws NoSuchMethodException {
        mRegisteredMessages.add(new RegisteredMessage(this, messageClass));
    }

    public void onNotice(Message message) {
        if (mListener != null) {
            mListener.onNotice(message);
        }
    }

    public void onWelcome() {
        if (mListener != null) {
            mListener.onWelcome();
        }
    }

    public void onPing(String source) {
        if (mListener != null) {
            mListener.onPing(source);
        }
    }

    public interface OnClientEventListener {
        public void onNotice(Message message);
        public void onWelcome();
        public void onPing(String source);
    }
}
