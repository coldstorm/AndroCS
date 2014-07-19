package tk.coldstorm.androcs.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tk.coldstorm.androcs.R;
import tk.coldstorm.androcs.models.ChatLine;

public class ChatLineAdapter extends ArrayAdapter<ChatLine> {
    public ChatLineAdapter(Context context, ArrayList<ChatLine> chatLines) {
        super(context, R.layout.item_chat_line, chatLines);
    }

    //region Overrides
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatLine line = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat_line, parent, false);
        }

        TextView chatLineTextView = (TextView) convertView.findViewById(R.id.chat_line_content);

        String timeStampString = String.format("[%1$tH:%1$tM]", line.getTimeStamp());
        SpannableString chatLineSpan = new SpannableString(String.format("%s %s %s", timeStampString, line.getUserItem().getIRCUser().getNickName(), line.getChat()));
        // TODO: Clean this up
        int timeStampStart = 0;
        int timeStampEnd = timeStampStart + timeStampString.length();
        int nickNameStart = timeStampEnd + 1;
        int nickNameEnd = nickNameStart + line.getUserItem().getIRCUser().getNickName().length();
        int chatStart = nickNameEnd + 1;
        int chatEnd = chatStart + line.getChat().length();

        chatLineSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.time_stamp_color)), timeStampStart, timeStampEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        chatLineSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_color)), nickNameStart, nickNameEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        chatLineSpan.setSpan(new StyleSpan(Typeface.BOLD), nickNameStart, nickNameEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        chatLineSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_color)), chatStart, chatEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        chatLineTextView.setText(chatLineSpan);

        return convertView;
    }
    //endregion
}
