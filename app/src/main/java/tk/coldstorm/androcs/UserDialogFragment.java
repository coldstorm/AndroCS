package tk.coldstorm.androcs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import tk.coldstorm.androcs.models.UserItem;

public class UserDialogFragment extends DialogFragment {
    private static final String ARG_USER_ITEM = "user_item";

    private UserItem mUserItem;

    public static UserDialogFragment newInstance(UserItem userItem) {
        UserDialogFragment userDialogFragment = new UserDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER_ITEM, userItem);
        userDialogFragment.setArguments(args);
        return userDialogFragment;
    }

    public UserDialogFragment() {
    }

    //region Overrides
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (getArguments() != null) {
            mUserItem = getArguments().getParcelable(ARG_USER_ITEM);
        }

        // Inflate the dialog with a custom layout
        View dialogView = inflater.inflate(R.layout.fragment_user_dialog, null);
        TextView titleTextView = (TextView) dialogView.findViewById(R.id.user_dialog_title);
        TextView contentTextView = (TextView) dialogView.findViewById(R.id.user_dialog_content);

        // Set the title using the TextView
        titleTextView.append(mUserItem.getUserName());

        // Set the dialog text content using the TextView
        contentTextView.append("Country code: " + mUserItem.getCountryCode() + "\n");

        builder.setView(dialogView);

        // Add close button
        builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing.
            }
        });

        return builder.create();
    }
    //endregion
}
