package tk.coldstorm.androcs.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Chat implements Parcelable {
    //region Title
    private String title;

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }
    //endregion

    //region Lines
    private ArrayList<ChatLine> lines;

    public ArrayList<ChatLine> getLines() {
        return lines;
    }

    private void setLines(ArrayList<ChatLine> lines) {
        this.lines = lines;
    }
    //endregion

    //region Constructors
    public Chat(String title, ArrayList<ChatLine> lines) {
        this.title = title;
        this.lines = lines;
    }
    //endregion

    public void addLine(ChatLine line) {
        this.lines.add(line);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeSerializable(this.lines);
    }

    private Chat(Parcel in) {
        this.title = in.readString();
        this.lines = (ArrayList<ChatLine>) in.readSerializable();
    }

    public static final Parcelable.Creator<Chat> CREATOR = new Parcelable.Creator<Chat>() {
        public Chat createFromParcel(Parcel source) {
            return new Chat(source);
        }

        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}
