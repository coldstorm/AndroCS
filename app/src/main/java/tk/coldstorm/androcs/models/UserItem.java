package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItem implements Parcelable {
    private String userName;

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
