package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItem implements Parcelable {

    //region UserName
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    //endregion

    //region CountryCode
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    //endregion

    public UserItem(String userName, String countryCode) {
        this.userName = userName;
        this.countryCode = countryCode;
    }

    private UserItem(Parcel source) {
        userName = source.readString();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel source) {
            return new UserItem(source);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    //region Overrides
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
    }
    //endregion
}
