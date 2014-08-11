package tk.coldstorm.androcs;

import java.util.HashMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tk.coldstorm.androcs.models.irc.User;

public class UserFactory {

    private static final HashMap<String, User> mStore = new HashMap<String, User>();

    public UserFactory() {

    }

    protected void removeNickName(String nickName) {

    }

    protected User changeNickName(String original, String future) {
        User user = this.fromNickName(original);

        mStore.put(future, user);
        mStore.remove(user.getNickName());

        user.setNickName(future);

        return user;
    }

    public User fromNickName(String nickName) {
        if (mStore.containsKey(nickName)) {
            return mStore.get(nickName);
        }

        User user = new User(nickName);
        mStore.put(nickName, user);

        return user;
    }

    public User fromUserMask(String userMask) {
        Pattern userMaskPattern = Pattern.compile("^([a-z0-9_\\-\\[\\]\\\\^{}|`]+)!([a-z0-9_\\-\\~]+)\\@([a-z0-9\\.\\-]+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = userMaskPattern.matcher(userMask);

        if (!matcher.find()) {
            return null;
        }

        MatchResult result = matcher.toMatchResult();
        String nickName = result.group(1);
        String userName = result.group(2);
        String hostName = result.group(3);

        User user = this.fromNickName(nickName);
        user.setUserName(userName);
        user.setHostName(hostName);

        return user;
    }

    protected void setUser(String nickName, User user) {
        mStore.put(nickName, user);
    }
}
