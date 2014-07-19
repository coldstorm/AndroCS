package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class IRCUser implements Parcelable {
    //region NickName
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    //endregion

    //region UserName
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    //endregion

    //region HostName
    private String hostName;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    //endregion

    //region RealName
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    //endregion

    //region IsAway
    private boolean isAway;

    public boolean isAway() {
        return isAway;
    }

    public void setAway(boolean isAway) {
        this.isAway = isAway;
    }
    //endregion

    //region AwayMessage
    private String awayMessage;

    public String getAwayMessage() {
        return awayMessage;
    }

    public void setAwayMessage(String awayMessage) {
        this.awayMessage = awayMessage;
    }
    //endregion

    //region Constructors
    public IRCUser(String nickName) {
        this.nickName = nickName;
    }

    public IRCUser(String nickName, String userName) {
        this.nickName = nickName;
        this.userName = userName;
    }

    public IRCUser(String nickName, String userName, String realName) {
        this.nickName = nickName;
        this.userName = userName;
        this.realName = realName;
    }
    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickName);
        dest.writeString(this.userName);
        dest.writeString(this.hostName);
        dest.writeString(this.realName);
        dest.writeByte(isAway ? (byte) 1 : (byte) 0);
        dest.writeString(this.awayMessage);
    }

    private IRCUser(Parcel in) {
        this.nickName = in.readString();
        this.userName = in.readString();
        this.hostName = in.readString();
        this.realName = in.readString();
        this.isAway = in.readByte() != 0;
        this.awayMessage = in.readString();
    }

    public static final Parcelable.Creator<IRCUser> CREATOR = new Parcelable.Creator<IRCUser>() {
        public IRCUser createFromParcel(Parcel source) {
            return new IRCUser(source);
        }

        public IRCUser[] newArray(int size) {
            return new IRCUser[size];
        }
    };
}
