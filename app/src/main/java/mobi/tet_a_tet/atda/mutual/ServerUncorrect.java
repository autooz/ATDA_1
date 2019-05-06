package mobi.tet_a_tet.atda.mutual;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import mobi.tet_a_tet.atda.R;

/**
 * Created by oleg on 03.07.15.
 * USAGE: new mobi.tet_a_tet.atda.mutual.ServerUncorrect(this).show();
 */
public class ServerUncorrect {


    private Activity sActivity;

    public ServerUncorrect(Activity context) {
        sActivity = context;
    }


    public void show() {

        // Show the Eula
        String title = sActivity.getString(R.string.login_uncorrect);
        //Includes the updates as well so users know what changed.
        String message = sActivity.getString(R.string.server_instruction);

        AlertDialog.Builder builder = new AlertDialog.Builder(sActivity)
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
        String title = sActivity.getString(R.string.login_uncorrect);
        //Includes the updates as well so users know what changed.
        String message = sActivity.getString(R.string.server_instruction);

        AlertDialog.Builder builder = new AlertDialog.Builder(sActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close Dialog
                        dialogInterface.dismiss();
                    }
                });
        builder.create().dismiss();
    }

}


