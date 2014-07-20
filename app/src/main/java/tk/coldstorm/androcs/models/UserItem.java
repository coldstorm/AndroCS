package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

import tk.coldstorm.androcs.models.irc.User;

public class UserItem implements Parcelable {

    //region IRCUser
    private User user;

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
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

    public UserItem(User user, String countryCode) {
        this.user = user;
        this.countryCode = countryCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.countryCode);
    }

    private UserItem(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
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
