package tk.coldstorm.androcs;

import android.app.Activity;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import tk.coldstorm.androcs.R;
import tk.coldstorm.androcs.models.ChatLine;
import tk.coldstorm.androcs.models.UserItem;

public class MainActivity
        extends Activity
        implements
            NavigationDrawerFragment.NavigationDrawerCallbacks,
            ChatFragment.OnFragmentInteractionListener {

    /**
     * Fragments managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mLeftNavigationDrawerFragment;
    private NavigationDrawerFragment mRightNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //region Overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLeftNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_navigation_drawer);
        mRightNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.right_navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mLeftNavigationDrawerFragment.setUp(
                R.id.left_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                new String[]{"Channel 1", "Channel 2", "Channel 3"});
        mRightNavigationDrawerFragment.setUp(
                R.id.right_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                new String[]{"User 1", "User 2", "User 3"});
    }

    @Override
    public void onNavigationDrawerItemSelected(NavigationDrawerFragment sender, int position, String content) {
        // Handle the item selection based on the sender
        if (sender == mLeftNavigationDrawerFragment) {
            replaceFragment(ChatFragment.newInstance(content));
            // Set the title to this fragment's title; it will be updated next time restoreActionBar() is called.
            mTitle = content;
        } else if (sender == mRightNavigationDrawerFragment) {
            showUserDialog(UserDialogFragment.newInstance(new UserItem(content, "QQ")));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mLeftNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
    //endregion

    //region Helpers
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void replaceFragment(Fragment replacer) {
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, replacer)
                .commit();

        ChatFragment chatFragment = (ChatFragment) replacer;
        chatFragment.addLine(new ChatLine(new UserItem("test", "QQ"), "test chat"));
    }

    public void showUserDialog(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        UserDialogFragment userDialogFragment = (UserDialogFragment) fragment;

        userDialogFragment.show(fragmentManager, null);
    }
    //endregion
}
