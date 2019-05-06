package mobi.tet_a_tet.atda.mutual;

/**
 * Created by oleg on 02.07.15.
 * USAGE: new mobi.tet_a_tet.atda.mutual.AppsEula(this).show();
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

public class AppsEula {

    private String EULA_PREFIX = "eula_";
    private Activity mActivity;

    public AppsEula(Activity context) {
        mActivity = context;
    }

    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    public void show() {
        PackageInfo versionInfo = getPackageInfo();

        // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
        final String eulaKey = TetGlobalData.EULA_APP_KEY;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean hasBeenShown = prefs.getBoolean(eulaKey, false);
        if (hasBeenShown == false) {

            // Show the Eula
            String title = mActivity.getString(R.string.title_activity_firs_start_page) + " v" + versionInfo.versionName;

            //Includes the updates as well so users know what changed.
            String message = mActivity.getString(R.string.license_title) + "\n\n" + mActivity.getString(R.string.license_content);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(R.string.am_agree, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mark this version as read.
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(eulaKey, true);
                            editor.commit();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.no_agree, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Close the activity as they have declined the EULA
                            mActivity.finish();
                        }

                    });
            builder.create().show();
        }
    }


    public void dismiss() {
        PackageInfo versionInfo = getPackageInfo();
        mActivity.finish();
    }
}