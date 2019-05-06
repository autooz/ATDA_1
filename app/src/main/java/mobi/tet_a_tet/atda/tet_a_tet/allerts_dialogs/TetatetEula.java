package mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs;

/**
 * Created by oleg on 02.07.15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.widget.Button;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.StartTetFirstRegistrationActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;


public class TetatetEula extends StartTetFirstRegistrationActivity {

    private String EULA_TET = TetGlobalData.TAG;
    private Activity mActivity;

    public TetatetEula(Activity context) {
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
        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== Showing EULA===============");
        // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
        final String tetateteulaKey = TetGlobalData.TAG_TET_A_TET_KEY;
        final String tetatetversion = TetGlobalData.TAG_REV_VALUE;
        final String agreement = TetGlobalData.TAG_EULA_KEY_AGREEMENT;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean EulahasBeenShown = prefs.getBoolean(tetateteulaKey, false);
        if (EulahasBeenShown == false) {

            // Show the Eula
            String title = mActivity.getString(R.string.title_activity_firs_start_tet_a_tet);
            Button buttoniHaveNotLogin;

            //Includes the updates as well so users know what changed.
            String message = mActivity.getString(R.string.license_title_tet_a_tet) + "\n\n" + mActivity.getString(R.string.licence_tet_a_tet);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(title)
                    .setNeutralButton(R.string.getDates, new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //    Intent nE = new Intent(mActivity, mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.NotRegisteredYetActivity.class);
                            // Oth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            stActivity(mActivity);
                            dialogInterface.dismiss();

                        }
                    })
                    .setMessage(message)
                    .setPositiveButton(R.string.am_agree, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mark this version as read.
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(agreement, true);
                            editor.putString(tetateteulaKey, tetatetversion);
                            editor.commit();
                            restartActivity(mActivity);
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

    public static void stActivity(Activity act) {
        Intent intent = new Intent();
        intent.setClass(act, mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.NotRegisteredYetActivity.class);
        act.startActivity(intent);
        // act.finish();

    }

    public static void restartActivity(Activity act) {
        Intent intent = new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();
    }

}