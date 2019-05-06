package mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;

public class ContactInfoActivity extends Activity implements View.OnClickListener {

    private TableRow tableRowTitle;
    private TextView textViewContactInfoTitle;
    private TableRow tableRowText;
    private TextView textViewContactInfoTexi;
    private TableRow tableRowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        tableRowTitle = (TableRow) findViewById(R.id.TableRowTitle);
        textViewContactInfoTitle = (TextView) findViewById(R.id.textViewContactInfoTitle);
        tableRowText = (TableRow) findViewById(R.id.TableRowText);
        textViewContactInfoTexi = (TextView) findViewById(R.id.textViewContactInfoTexi);
        tableRowButton = (TableRow) findViewById(R.id.TableRowButton);
        findViewById(R.id.buttonClose).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonClose:
                //TODO implement
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}