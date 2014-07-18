package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItem implements Parcelable {

    //region UserName
    private String userName;

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
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

    public UserItem(String userName, String countryCode) {
        this.userName = userName;
        this.countryCode = countryCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.countryCode);
    }

    private UserItem(Parcel in) {
        this.userName = in.readString();
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
