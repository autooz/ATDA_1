package mobi.tet_a_tet.atda.off_lline.Controllers;

/**
 * Created by oleg on 27.08.15.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import mobi.tet_a_tet.atda.R;

public class messageController extends DialogFragment implements OnClickListener {
    AlertDialog.Builder alert_dialog_builder;//нативный компонент, показывающий окошки пользователю
    int type, msg;//type - тип окошка(с одной кнопкой или тремя), msg - сообщения для пользователя, хранится в String.xml
    private int res = -1;//необходим для вывода результата
    String[] message;//массив самих сообщений
    String tag;//используется для отладочной информации
    private OnDismissListener mCallback;//чтобы мы могли узнать, что выбрал пользователь
    private soundController sound_contreller;//контроллер звуков
    private Context context;//необходимый контекст для работы

    public messageController() {
        Context cnt = context;
        // new messageController(cnt);
    }

    @SuppressLint("ValidFragment")
    public messageController(Context cnt) {

        sound_contreller = new soundController(cnt);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        tag = getResources().getString(R.string.tag);
        message = getResources().getStringArray(R.array.msg);

        //get args
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");//получем тип окошка (одна кнопка или три)
        msg = getArguments().getInt("msg");//получаем id сообщения, который хранится в String.xml

        //set title and body for message
        alert_dialog_builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.warning)//устанавливаем заголовок сообщения
                .setMessage(message[msg]);//устанавливаем тело сообщения

        switch (type) {
            case 1:    //добавляем три кнопки для сообщения
                alert_dialog_builder.setPositiveButton(R.string.yes, this)
                        .setNegativeButton(R.string.no, this)
                        .setNeutralButton(R.string.cancel, this);
                break;
            case 2:    //добавляем одну кнопку для сообщения
                alert_dialog_builder.setPositiveButton(R.string.ok, this);
                break;

            default:
                break;

        }

        sound_contreller.play(1);//проигрываем звук
        return alert_dialog_builder.create();//показываем само сообщение
    }


    @Override
    public void onClick(DialogInterface Dialog, int i) {//узнаем на какую кнопку нажал пользователь
        switch (i) {
            case -1:
                res = 1;
                break;

            case -2:
                res = 2;
                break;

            case -3:
                res = 0;
                break;

            default:
                break;
        }


        mCallback.OnDismissListener(res);
        res = -1;
    }

    public interface OnDismissListener {//необходимо для работы

        public void OnDismissListener(int res);
    }

    @Override
    public void onAttach(Activity activity) {//необходимо для работы
        super.onAttach(activity);
        mCallback = (OnDismissListener) activity;
    }


}