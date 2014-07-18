package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.Formatter;
import java.util.Locale;

import tk.coldstorm.androcs.R;

public class ChatLine implements Parcelable {

    //region TimeStamp
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

    public CharSequence toCharSequence() {
        SpannableString timeStampSpan = new SpannableString(String.format("[%]", timeStamp));
        timeStampSpan.setSpan(new ForegroundColorSpan(R.color.time_stamp_color), 0, timeStampSpan.length(), 0);

        SpannableString nickNameSpan = new SpannableString(userItem.getUserName());
        nickNameSpan.setSpan(new ForegroundColorSpan(R.color.chat_color), 0, nickNameSpan.length(), 0);

        SpannableString chatSpan = new SpannableString(chat);
        chatSpan.setSpan(new ForegroundColorSpan(R.color.chat_color), 0, chatSpan.length(), 0);

        return TextUtils.concat(timeStampSpan + " " + nickNameSpan + " " + chatSpan);
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
