package tk.coldstorm.androcs;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;

import tk.coldstorm.androcs.messages.SendMessage;
import tk.coldstorm.androcs.messages.send.JoinMessage;
import tk.coldstorm.androcs.messages.send.NickMessage;
import tk.coldstorm.androcs.messages.send.PongMessage;
import tk.coldstorm.androcs.messages.send.UserMessage;
import tk.coldstorm.androcs.models.Chat;
import tk.coldstorm.androcs.models.ChatLine;
import tk.coldstorm.androcs.models.irc.Message;
import tk.coldstorm.androcs.models.irc.User;
import tk.coldstorm.androcs.models.UserItem;
import tk.coldstorm.androcs.services.IRCService;

public class MainActivity
        extends Activity
        implements
            NavigationDrawerFragment.NavigationDrawerCallbacks,
            ChatFragment.OnFragmentInteractionListener,
            Client.OnClientEventListener {
    private Client mClient;

    /**
     * Fragments managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mLeftNavigationDrawerFragment;
    private NavigationDrawerFragment mRightNavigationDrawerFragment;

    private ArrayList<Chat> mChats = new ArrayList<Chat>();
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private IRCMessageReceiver mIRCMessageReceiver;

    //region Overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mClient = new Client(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        mLeftNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_navigation_drawer);
        mRightNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.right_navigation_drawer);

        mChats.add(new Chat("Channel 1", new ArrayList<ChatLine>()));
        mChats.add(new Chat("Channel 2", new ArrayList<ChatLine>()));
        mChats.add(new Chat("Channel 3", new ArrayList<ChatLine>()));

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

        IntentFilter mIRCMessageIntentFilter = new IntentFilter(Constants.ACTION_IRC_MESSAGE_RECEIVED);
        mIRCMessageReceiver = new IRCMessageReceiver(mClient);

        LocalBroadcastManager.getInstance(this).registerReceiver(mIRCMessageReceiver, mIRCMessageIntentFilter);

        IRCService.startActionConnect(this, "irc.frogbox.es", 6660, new User("test2", "test2", "test2"));
    }

    @Override
    public void onNavigationDrawerItemSelected(NavigationDrawerFragment sender, int position, String content) {
        // Handle the item selection based on the sender
        if (sender == mLeftNavigationDrawerFragment) {
            // Find the corresponding Chat model
            Chat selectedChat = null;
            ChatFragment chatFragment = null;
            for (Chat chat : mChats) {
                if (content.equals(chat.getTitle())) {
                    selectedChat = chat;
                    break;
                }
            }

            if (selectedChat != null) {
                replaceFragment(ChatFragment.newInstance(selectedChat));
                selectedChat.addLine(new ChatLine(new UserItem(new User("test"), "QQ"), String.format("Welcome to %s", selectedChat.getTitle())));
            }

            // Set the title to this fragment's title; it will be updated next time restoreActionBar() is called.
            mTitle = content;
        } else if (sender == mRightNavigationDrawerFragment) {
            showUserDialog(UserDialogFragment.newInstance(new UserItem(new User(content), "QQ")));
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

    @Override
    public void onWelcome() {
        IRCService.startActionSend(this, new JoinMessage("#test"));
    }

    @Override
    public void onNotice(Message message) {
    }

    @Override
    public void onPing(String source) {
        IRCService.startActionSend(this, new PongMessage(source));
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
    }

    public void showUserDialog(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        UserDialogFragment userDialogFragment = (UserDialogFragment) fragment;

        userDialogFragment.show(fragmentManager, null);
    }
    //endregion
}
