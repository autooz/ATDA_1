package mobi.tet_a_tet.atda.mutual;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import mobi.tet_a_tet.atda.R;

/**
 * Created by oleg on 03.07.15.
 * USAGE: new mobi.tet_a_tet.atda.mutual.LoginUncorrect(this).show();
 */
public class LoginUncorrect {

    private Activity lActivity;

    public LoginUncorrect(Activity context) {
        lActivity = context;
    }


    public void show() {

        // Show the Eula
        String title = lActivity.getString(R.string.server_uncorrect);
        //Includes the updates as well so users know what changed.
        String message = lActivity.getString(R.string.login_instruction);

        AlertDialog.Builder builder = new AlertDialog.Builder(lActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close Dialog
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();

    }

    public void dissmis() {
        // Show the Eula
        String title = lActivity.getString(R.string.server_uncorrect);
        //Includes the updates as well so users know what changed.
        String message = lActivity.getString(R.string.login_instruction);

        AlertDialog.Builder builder = new AlertDialog.Builder(lActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close Dialog
                        lActivity.finish();
                        dialogInterface.dismiss();
                    }
                });
        builder.create().dismiss();
    }
}