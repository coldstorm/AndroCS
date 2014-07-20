package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class ChatLine implements Parcelable {

    //region TimeStamp
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    private void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    //endregion

    //region UserItem
    private UserItem userItem;

    public UserItem getUserItem() {
        return userItem;
    }

    private void setUserItem(UserItem userItem) {
        this.userItem = userItem;
    }
    //endregion

    //region Text
    private String text;

    public String getText() {
        return text;
    }

    private void setText(String text) {
        this.text = text;
    }
    //endregion

    //region Constructors
    public ChatLine(long timeStamp, UserItem userItem, String text) {
        this.timeStamp = timeStamp;
        this.userItem = userItem;
        this.text = text;
    }

    public ChatLine(UserItem userItem, String text) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        this.timeStamp = calendar.getTimeInMillis();
        this.userItem = userItem;
        this.text = text;
    }
    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.timeStamp);
        dest.writeParcelable(this.userItem, 0);
        dest.writeString(this.text);
    }

    private ChatLine(Parcel in) {
        this.timeStamp = in.readLong();
        this.userItem = in.readParcelable(UserItem.class.getClassLoader());
        this.text = in.readString();
    }

    public static final Parcelable.Creator<ChatLine> CREATOR = new Parcelable.Creator<ChatLine>() {
        public ChatLine createFromParcel(Parcel source) {
            return new ChatLine(source);
        }

        public ChatLine[] newArray(int size) {
            return new ChatLine[size];
        }
    };
}
