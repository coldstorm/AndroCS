package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatLine implements Parcelable {

    //region TimeStamp
    // TODO: Make this a Calendar object
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    private void setTimeStamp(String timeStamp) {
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

    //region Chat
    private String chat;

    public String getChat() {
        return chat;
    }

    private void setChat(String chat) {
        this.chat = chat;
    }
    //endregion

    public ChatLine(String timeStamp, UserItem userItem, String chat) {
        this.timeStamp = timeStamp;
        this.userItem = userItem;
        this.chat = chat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.timeStamp);
        dest.writeParcelable(this.userItem, 0);
        dest.writeString(this.chat);
    }

    private ChatLine(Parcel in) {
        this.timeStamp = in.readString();
        this.userItem = in.readParcelable(UserItem.class.getClassLoader());
        this.chat = in.readString();
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
