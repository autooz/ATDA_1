package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Properties;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.communications.JabberListenerService;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSListnerZone;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSbyTimeListner;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerService;

import static java.lang.System.exit;

public class GoodByActivity extends Activity {

    private int soundID;
    private AudioManager audioManager;
    private Properties soundPool;
    private String pseudo_tag
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        Intent PLS = new Intent(getApplicationContext(), ActivityControllerService.class);
        stopService(PLS);
        Intent JS = new Intent(getApplicationContext(), JabberListenerService.class);
        stopService(JS);
        Intent TLS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        stopService(TLS);
        Intent PS = new Intent(getApplicationContext(), GPSListnerZone.class);
        stopService(PS);
        setContentView(R.layout.activity_good_by);
        Toast.makeText(this, R.string.jabListnerStarted, Toast.LENGTH_LONG).show();
//        try {
//            // Create objects of the 2 required classes
//            AssetManager assetManager = this.getAssets();
//            AssetFileDescriptor descriptor;
//            descriptor = assetManager.openFd(String.valueOf(R.raw.ding));
//            // Load our fx in memory ready for use
//            descriptor = assetManager.openFd("ding.wav");
//            soundID = soundPool.load(descriptor, 0);
//
//        } catch (IOException e) {
//            // Print an error message to the console
//            Log.e("error", "failed to load sound files");
//        }
//
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.STREAM_MUSIC);
//        if (DriverTuning.TurnSpeakerOn) {
//            audioManager.setSpeakerphoneOn(true);
//        }
//        int actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        //int volume = actualVolume / maxVolume;
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
//
//
//        soundPool.play(soundID, 1, 1, 0, 0, 1);
        finish();
        exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_good_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
    }
}
