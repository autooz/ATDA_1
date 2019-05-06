package mobi.tet_a_tet.atda.off_lline.Controllers;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import mobi.tet_a_tet.atda.R;

public class soundController extends Activity {
    Context context;
    private MediaPlayer mediaPlayer;
    private int[] sound = {
            R.raw.ding,
            R.raw.error
    };

    public soundController(Context context) {
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void play(int id) {

        mediaPlayer = MediaPlayer.create(context, sound[id]);
        mediaPlayer.start();
    }

}
