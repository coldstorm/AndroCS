package tk.coldstorm.androcs.models.irc;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Channel implements Parcelable {
    //region Name
    private String name;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
    //endregion

    //region FullName
    private String fullName;

    public String getFullName() {
        return '#' + name;
    }
    //endregion

    //region Users
    private HashMap<String, User> users;

    public HashMap<String, User> getUsers() {
        return users;
    }
    //endregion

    //region Constructors
    public Channel(String name) {
        if (name.startsWith("#")) {
            this.name = name.substring(0, 1);
        } else {
            this.name = name;
        }
    }
    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.fullName);
        dest.writeMap(this.users);
    }

    private Channel(Parcel in) {
        this.name = in.readString();
        this.fullName = in.readString();
        in.readMap(this.users, User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
