package tk.coldstorm.androcs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class UserDialogFragment extends DialogFragment {
    private static final String ARG_USER_NAME = "user_name";

    private String mUserName;

    public static UserDialogFragment newInstance(String userName) {
        UserDialogFragment userDialogFragment = new UserDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
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
            mUserName = getArguments().getString(ARG_USER_NAME);
        }

        // Inflate the dialog with a custom layout
        builder.setView(inflater.inflate(R.layout.fragment_user_dialog, null));

        // Set title
        builder.setTitle(mUserName);

        return builder.create();
    }
    //endregion
}
