package tk.coldstorm.androcs.messages;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import tk.coldstorm.androcs.Client;
import tk.coldstorm.androcs.models.irc.Message;

public class RegisteredMessage {
    private Client mClient;
    private Class mClass;
    private Method mCheckMessageMethod;

    public RegisteredMessage(Client client, Class messageClass) throws NoSuchMethodException, IllegalArgumentException {
        mClient = client;
        ArrayList<Class> interfaces = new ArrayList<Class>(Arrays.asList(messageClass.getInterfaces()));

        if (!interfaces.contains(ReceiveMessage.class)) {
            throw new IllegalArgumentException("type must implement ReceiveMessage interface");
        }

        Class[] methodArgs = new Class[2];
        methodArgs[0] = Message.class;
        methodArgs[1] = Client.class;
        mCheckMessageMethod = messageClass.getDeclaredMethod("CheckMessage", methodArgs);

        mClass = messageClass;
    }

    public Boolean CheckMessage(Message message) throws InvocationTargetException, IllegalAccessException {
        return (Boolean) mCheckMessageMethod.invoke(null, message, mClient);
    }

    public void ProcessMessage(Message message) throws InstantiationException, IllegalAccessException {
        ReceiveMessage instance = (ReceiveMessage) mClass.newInstance();
        instance.ProcessMessage(message, mClient);
    }
}
