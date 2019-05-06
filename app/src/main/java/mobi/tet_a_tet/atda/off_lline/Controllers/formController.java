package mobi.tet_a_tet.atda.off_lline.Controllers;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import mobi.tet_a_tet.atda.off_lline.OfflineMainActivity;
import mobi.tet_a_tet.atda.off_lline.addOrChangeListViewActivity;
import mobi.tet_a_tet.atda.off_lline.listViewActivity;
import mobi.tet_a_tet.atda.off_lline.loginActivity;
import mobi.tet_a_tet.atda.off_lline.settingActivity;
import mobi.tet_a_tet.atda.off_lline.workActivity;

public class formController extends Activity implements Parcelable {
    public static final Parcelable.Creator<formController> CREATOR = new Parcelable.Creator<formController>() {
        public formController createFromParcel(Parcel in) {
            return new formController(in);
        }

        public formController[] newArray(int size) {
            return new formController[size];
        }
    };
    public dbController db_controller = null;
    Context context = null;
    Intent intent = null;

    public formController(Context context) {
        db_controller = new dbController(context);
    }

    private formController(Parcel parcel) {
        db_controller = (dbController) parcel.readParcelable(dbController.class.getClassLoader());
    }

    public void showForm(int i, Context context, String type) {
        switch (i) {
            case 1:
                intent = new Intent(context, loginActivity.class);
                break;
            case 2:
                intent = new Intent(context, OfflineMainActivity.class);
                break;
            case 3:
                intent = new Intent(context, workActivity.class);
                break;
            case 4:
                intent = new Intent(context, settingActivity.class);
                break;
            case 5:
                intent = new Intent(context, listViewActivity.class);
                break;
            case 6:
                intent = new Intent(context, addOrChangeListViewActivity.class);
                break;
        }
        intent.putExtra("typeListView", type);//передаем переменную для адаптера, какого типа данных считывать
        intent.putExtra(formController.class.getCanonicalName(), this);//передаем класс контроллера форм
        context.startActivity(intent);
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(db_controller, 0);
    }

}

