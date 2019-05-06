package mobi.tet_a_tet.atda.mutual.communications;

import android.content.Context;

/**
 * Created by oleg on 07.03.16.
 */
public interface eJabberdSenderInteface {
    void onRecievedMessage(Context mContext, String msg);

    void onLogin(boolean aTrue);

    void onPostExecute(boolean b);
}
