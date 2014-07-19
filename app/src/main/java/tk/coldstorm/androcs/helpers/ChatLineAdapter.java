package tk.coldstorm.androcs.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
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

        // Create a timestamp span
        SpannableString timeStampSpan = new SpannableString(String.format("[%1$tH:%1$tM]", line.getTimeStamp()));
        timeStampSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.time_stamp_color)), 0, timeStampSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // Create a nickname span
        SpannableString nickNameSpan = new SpannableString(line.getUserItem().getIRCUser().getNickName());
        nickNameSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_color)), 0, nickNameSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        nickNameSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, nickNameSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // Create a text span
        SpannableString textSpan = new SpannableString(line.getText());
        textSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_color)), 0, textSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // Build the chat line from the spans
        SpannableStringBuilder chatLineSpan = new SpannableStringBuilder()
                .append(timeStampSpan)
                .append(" ")
                .append(nickNameSpan)
                .append(" ")
                .append(textSpan);

        chatLineTextView.setText(chatLineSpan);

        return convertView;
    }
    //endregion
}
