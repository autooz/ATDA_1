package mobi.tet_a_tet.atda.off_lline;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.off_lline.Controllers.messageController;

public class workActivity extends Activity implements OnClickListener, messageController.OnDismissListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_work_activity);
    }


    @Override
    public void OnDismissListener(int res) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

}
