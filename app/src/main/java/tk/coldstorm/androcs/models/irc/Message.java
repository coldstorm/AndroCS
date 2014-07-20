package tk.coldstorm.androcs.models.irc;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message implements Parcelable {
    //region Prefix
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    private void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    //endregion

    //region Command
    private String command;

    public String getCommand() {
        return command;
    }

    private void setCommand(String command) {
        this.command = command;
    }
    //endregion

    //region Parameters
    private String[] parameters;

    public String[] getParameters() {
        return parameters;
    }

    private void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
    //endregion

    //region TotalParameters
    private int totalParameters;

    public int getTotalParameters() {
        return this.parameters.length;
    }
    //endregion

    //region Raw
    private String raw;

    public String getRaw() {
        return raw;
    }

    private void setRaw(String raw) {
        this.raw = raw;
    }
    //endregion

    public Message(String raw) {
        this.raw = raw;
        this.parameters = new String[0];

        Pattern messagePattern = Pattern.compile("^(:(\\S+) )?(\\S+)( (?!:)(.+?))?( :(.*))?$");
        Matcher matcher = messagePattern.matcher(raw);

        if (!matcher.find()) {
            return;
        }

        MatchResult result = matcher.toMatchResult();
        prefix = result.group(2);
        command = result.group(3);
        String parametersStr = result.group(5);

        if (parametersStr != null && !parametersStr.isEmpty()) {
            parameters = parametersStr.split(" ");
        }

        String trail = result.group(7);

        if (trail != null && !trail.isEmpty()) {
            ArrayList<String> parametersList = new ArrayList<String>(Arrays.asList(parameters));
            parametersList.addAll(Arrays.asList(trail));
            parameters = parametersList.toArray(new String[parametersList.size()]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prefix);
        dest.writeString(this.command);
        dest.writeStringArray(this.parameters);
        dest.writeInt(this.totalParameters);
        dest.writeString(this.raw);
    }

    private Message(Parcel in) {
        this.prefix = in.readString();
        this.command = in.readString();
        this.parameters = in.createStringArray();
        this.totalParameters = in.readInt();
        this.raw = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
