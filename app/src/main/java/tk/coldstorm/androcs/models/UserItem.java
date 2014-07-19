package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItem implements Parcelable {

    //region IRCUser
    private IRCUser ircUser;

    public IRCUser getIRCUser() {
        return ircUser;
    }

    private void setIRCUser(IRCUser ircUser) {
        this.ircUser = ircUser;
    }
    //endregion

    //region CountryCode
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    private void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    //endregion

    public UserItem(IRCUser ircUser, String countryCode) {
        this.ircUser = ircUser;
        this.countryCode = countryCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.ircUser, 0);
        dest.writeString(this.countryCode);
    }

    private UserItem(Parcel in) {
        this.ircUser = in.readParcelable(IRCUser.class.getClassLoader());
        this.countryCode = in.readString();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        public UserItem createFromParcel(Parcel source) {
            return new UserItem(source);
        }

        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };
}
