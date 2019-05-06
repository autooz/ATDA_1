package mobi.tet_a_tet.atda.mutual;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import mobi.tet_a_tet.atda.R;

/**
 * Created by oleg on 05.07.15.
 */
public class InternetIsOffOrFirevalIsON {

    private Activity lActivity;

    public InternetIsOffOrFirevalIsON(Activity context) {
        lActivity = context;
    }


    public void show() {

        // Show the Eula
        String title = lActivity.getString(R.string.server_uncorrect);
        //Includes the updates as well so users know what changed.
        String message = lActivity.getString(R.string.server_instruction);

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



/*


    @Override
    public void onClick(View view) {

        String server = jabberUserLabel.getText().toString();
        String user = jabberUserLabel.getText().toString();
        String pass = jabberPasswordLabel.getText().toString();
        String disp = jabberDispLabel.getText().toString();
        Bundle jabloginBundel = new Bundle(5);
        jabloginBundel.putString("server", server);
        jabloginBundel.putString("user", user);
        jabloginBundel.putString("pass", pass);
        jabloginBundel.putString("disp", disp);


        switch (view.getId()) {
            case R.id.jabberAskRegButton:
                //TODO implemen
               // Do first registration on Jabber server
                Bundle loginpageBundel = new Bundle();
                if(isNetworkAvailable() && isOnline() ) {
                    firstRegistartion();
                }
                break;
        }
    }


    private void firstRegistartion(Message bl) {        //Chesk intrrnet is available



        if(!isOnline()){
            Toast.makeText(getActivity(), R.string.inetIsOffOrFwIsOn, Toast.LENGTH_SHORT).show();
            onDestroy();
        }
        else {
            Toast.makeText(getActivity(), "Все ОК", Toast.LENGTH_SHORT).show();
            // Make first registration on Jabberd.

        }

    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();}


    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
/*
    private boolean hasInternetAccess() {
        boolean hasInternetAccess = false;
        try {
            //I set google but you can try anything "reliable"...
            //isReachable(1) the timeout in seconds
            hasInternetAccess = InetAddress.getByName("www.google.com").isReachable(1);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hasInternetAccess;
    } */

}
