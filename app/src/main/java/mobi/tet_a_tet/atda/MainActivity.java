package mobi.tet_a_tet.atda;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.off_lline.StartOfLineSassistant;
import mobi.tet_a_tet.atda.tet_a_tet.StartTetFirstRegistrationActivity;
import mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.WhotIsTetAtetActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/*! \mainpage ATDA Index Page
 *
 * \section intro_sec Introduction
 *
 * This APP for Android is open-source software and allows you to create communication between Android mobile devices taxi drivers and Taxi cab dispatchers servers.
 *
 * Create your own GPStaksometry or connect your own server processors.
 *
 * Used standardized Android API interfaces which made the blocks is ready for create own modules.
 *
 * You or your programmers can use this Android application code and quickly make yours own APP using Android studio.
 *
 * You can add, cut, change functionality of APP to avoid spending own lot of time or programmers working hours.
 *
 *
 * \section functionality Functionality
 *
 * \subsection feature Feature:
 *
 * 1. On server side program interface APP Setting.
 *
 * 2. The application is always under the control of the server, but not workin as similar programs because APP  not "ping server" every regular intervals.
 *
 * 3. APP make communication with the server only when with the vehicle-side (taxi cab) change state. So has a positive effect in areas with an unstable GSM/CDMA bound.
 *
 * 4. Push messages is possibile. The server can initialise communication with individual or group call APPs at any time.
 * 5. For program there is no difference that will be added - Activiti or fragment. All communication between Activities or fragments is done via program controller ActivityController or FragmentController.
 *
 *
 *
* \subsection function Сustomizable features:
*
* -Language: EN,RU,UA  and raw translated IT
*
* -Currency
*
* -Distance Over which the server data will updated On upon the position car be changed.
*
* -Time of Periodic updates GPS information
*
* -Payment Per kilometer suburban one-way trips
*
* -Km Included in Minimal Payment
*
* -The minimal payment for the trip
*
* -Cost of travel by one kilometer
*
* -Price Minutes of inactivity
*
* -Time Free customer waiting for occupation
*
* -Time waiting included in the minimum payment
*
* -Minimum Time without movement after which the taximeter switches to time-based fare ...
*
* -Minimal Speed ​​at which a taximeter goes into the calculation of kilometers
*
* -Permission to select areas manually by Driver
*
* -Choose driver-zones manually only
*
* -Minimal Accuracy of the GPS
*
* -Permission to Driver for work without GPS
*
* -Сompensation for delivery Car  to the customer
*
* -Initial payment or occupation payment
*
* -The town or city where the taxi business is conducted
*
* -Is used or not of minimum payment in the tariff concept
*
* -The number of the Setting version which are last updated on Android APP
*
* -All Change settings to hide or reveal certain fields and field with null.
*
* -Auto Switching on the taximeter count suburban or urban fare
*
* -Automatic Registration in the vehicle in the area / sector / station.
*
* -APP version number
*
*
*
* \subsection license LICENSE
*
* ATDA PUBLIC LICENSE  Version 2, November 2015
*
* Copyright (C) 2015 Oleg Zhabko
*
*  1. You are clean to do with the code which is published as text for all you want without any restriction- copy, modify, make Fork, use the code to create new programs  or adoption through the program ATDA source code text.
*  2. You  are clean to use native or copied any files, classes, methods, variables, etc. that already exist in the source code ATDA, only if you are acting in accordance with paragraphs of this LICENSE: "Allowed", "Limitations", "Extras", "Geting project Android Studio source code", "Freedom from copyright".
*
*  "Allowed"
*
* You are allowed to add own files in the project directories or packages:
* mobi.tet_a_tet.atda.mutual - classes and files for general and mutual use mobi.tet_a_tet.atda.other_assistant.vasha_podprogramma - classes and files that are intended solely for the operation you contribute additions.
*
* And as well you are clear to change contents of the file in mobi.tet_a_tet.atda.other_assistant: OtherAsssintantActivity for your work routine so looks like mobi.tet_a_tet.atda.other_assistant.you_prog_or_company_name.
*
* And of course you can add to res   - directory of resources/ and add to all directory above mobi.tet_a_tet.atda.
*
* You are allowed to add binary and encrypted files if it does not change the operation of programs and sub-programs that already exist in the source code ATDA.
*
*
* "Limitations"
*
* You are not allowed, deleting, renaming and modification of the classes and files that already exist in the source code ATDA  except of cases when it is required to remove errors.
*
* You are prohibited for deleting, renaming and modification of mobi.tet_a_tet.atda.other_assistant. OtherAsssintantActivity other_assistants and that will do to for user programs unavailable or routines that already exist in the source code ATDA will broken.
*
* You are prohibited for pay received by distribution source code.
*
*
*
* "Geting project Android Studio source code"
*
* The source code for Android Studio project is carried out on the condition Donateware.
*
* The size of contributions set by the user based on the individual perception of the value of software.
*
* We're not going to lose time on about every sneeze of those people who does not feel  for it needs.
*
*
*
* "Freedom from copyright"
*
* If you pay for us 300 USD or its equivalent, you get the source code ATDA, except for those files that created by other creators and get a WTFPL License - Do What The Fuck You Want To Public License.
*
*
*   \section obtain_sec Get source Android Studio Project zipped.
 *
 *   To obtain compressed file with source, please contact us before:
 *
 *   Contact information:
 *
 *   site: http://tet-a-tet.mobi/en/contacts.html Language English
 *
 *   site: http://tet-a-tet.mobi/ru/contacts.html Language Russian
 *
 *   cellphone: +38 (067) 411-98-75 (Local Time: UTC or GMT +2)
 *
 *   Skype:oleg.zhabko (language: en,ru.ua,by,pl. Local Time: 	UTC or GMT +2)
 *
 * \section screenshots Screenshots QVGA 240x320 2,7'
 *
 * Скриншоты специально показанны с минимально возможным экраном Андроида 2,7 дюйма.
 *
 * ( The images are shown with intentional use of the smallest possible screen on Android)
 *
 *
 * \subsection online Working with online Tet-A-Tet service:
 *
 * @image html firststart.png "Соглашение пользователя при первом старте ATDA".
 * @image html firststart_en.png "APP EULA at the first start".
 * @image html start.png "Запуск АТДА".
 * @image html start_en.png "ATDA start".
 * @image html ifwith.png "Соглашение клинта Tet-A-Tet с правилами пользования услугой (Tet-A-Tet customer EULA at first connect)".
 *@image html ifgetdates.png "Если нет данных для подключения - можно их получить отправив СМС из APP.".
 * @image html selectcountry.png "Выбор страны для запроса данных".
 * @image html selectcountry_en.png "If customer have not dates for connection Tet-A-Tet by press button <Get date> and this window be open. Customer select country for request dates".
 * @image html selectcity.png "Выбор города где можно подключиться к Tet-A-Tet".
 * @image html selectcity_en.png "Customer select Town".
 * @image html reqcms.png "После ввода номера телефона и модели авто Запускается стандартное окно телефона отправки СМС. Никакого бекграунда - все честно и прозрачно!.
 * (If phone number and car model entered and button Send SMS pressed the standard Android system window SMS service will be open for confirm intent)".
 *
 * @image html firstreg.png "Окно первого подключеня к серверу Тет-A-Tet".
 *@image html firstreg_en.png "If customer has Tet-A-Tet connection information the first registration on communication gate is available".
 *
 * @image html fidsreg.png "Если регистрация на сервере успешна, тогда производится первая регистрация на  обслуживающем сервере Tet-A-Tet ".
 * @image html fidsreg_en.png "If server registration is OK, customer try to be registered in DB Tet-A-Tet customers".
 *
 * Если регистрация прошла успешно, тогда пользователь никогда больше не увидит вышеперечисленных окон, кроме самого первого.
 *
 * При последущех входах и выборе кнопки "С Tet-A-Tet", будут открываться нижеописанные окна.
 *
 *
 * (If the registration is successful, then the user will never see the above windows, except the first.
 *
 * The work will begin with the window "Start shift").
 *
 *
 * @image html startshift1.png "Окно начала смены".
 * @image html startshift2.png "Если выбор оплаты Почасово, тогда появится поле в котором можно указать количество рабочих часов за которые пользователь готов заплатить.".
 * @image html startshift_en1.png "Start Shift window".
 * @image html startshift_en1.png "If customer select Hourly input hours will possible ".
 *
 * @image html notgoodgps.png "Если в данный момент точность GPS не позволяет определить место, появится окно которое закроется автоматически при достижении минимальной точности.".
 * @image html notgoodgps_en.png "If current GPS accuracy is impossible to determine the place. The window will closed automatically upon minimum accuracy.".
 *
 *
 *
 *
 * ПОСЛЕ ЭТИХ ОКОН ОТКРЫВАЕТСЯ РАБОЧЕЕ ОКНО И ТАКСИСТ РАБОТАЕТ ВСЕГО ДВУМЯ КНОПКАМИ
 *
 * (СТОП ТАКСОМЕТР/СТАРТ ТАКСОМЕТР И СВОБОДЕН/ЗАНЯТ)
 *
 * ВСЕ ОСТАЛЬНОЕ ЗА НЕГО ДЕЛАЕТ АВТОМАТИКА. ОНА СНИМАЕТ ЕГО СО СТОЯНКИ. ФИКСИРУЕТ "ОТ БОРДЮРА" А ТАК ЖЕ ВЗЯТИЯ ЗАКАЗА.
 *
 * ПРИ ЗАКАЗЕ ПРИХОДИТ ЗВОНОК ЗАКАЗЧИКА НА ТЕЛЕФОН ТАКСИСТА И ОН ПРИНИМАЕТ ЗАКАЗ БЕЗ УЧАСТИЯ ДИСПЕТЧЕРА.
 *
 * ВОДИТЕТЕЛЮ ПОСЛЕ РАЗГОВОРА ОСТАНЕТСЯ ТОЛЬКО УКАЗАТЬ ПРИНЯЛ ОН ЗАКАЗ ИЛИ ОТКАЗАЛСЯ.
 *
 *
 *
 * \section var1 Вариант использования №1 В КАЧЕСТВЕ GPSТАКСОМЕТРА ДС
 *
 * (КАК ТЕРМИНАЛ ВОДИТЕЛЯ)
 *
 * @image html onstop.png "Зона определыется автоматически. Когда автомобиль движется в состоянии "свободен" зоны меняются автоматически при их пресечении"
 * @image html onstop1.png ""
 * @image html onstop2.png "".
 *
 * @image html byhand.png "Если политикой ДС разрешен выбор стоянок вручную или указано "только вручную" водитель может сам регистрироваться на любой стоянке из меню |Опции|".
 *
 * @image html free.png "Как видно, позывной может быть буквенным с символами так и цифровым".
 * @image html busy.png "Изменить состояние водитель может нажатие в правом верхнем углу |Занят/свободен|".
 *
 * @image html border.png "При старте таксометра начинает работу или Онлайн таксометр по тарифам водителя, или таксометр получающий таривы ДС (в зависимости от политики ДС) а сервер получает информацию <от бордюра>. (Открытие таксометра по заказу от ДС работает по другому)."
 *
 * Как видно из рисунка формирование цены для пассажира максимально прозрачно.
 *
 * Таксометр автоматически определят сколько услуги потреблено в городе. за городом. и по какому тарифу.
 *
 * При этом таксометр работает полностью в автономном режиме (не обменевается никакой информацией с сервером).
 *
 * Таксометр свяжется с сервером только когда автомобиль освободится. При этом никаких данных о поездке не отправляется (Это приватные данные предпринимателя).
  *
  * Однако, при необходимости отправки данных о всех поездках код может быть изменен по желанию ДС.
  *
  * @image html border1.png " В этом положении водитель может только приостановить таксометр кнопкой |Стоп| с последующей возможностью продолжить отсчет".
  *
  * @image html border1.png "Меню выбора действий остановленного таксометра".
  *
  * При указании того что автомобиль свободен, будет определена зона в которой освободился автомобиль и он в ней будет зарегистрирован сервером.
  *
  * Если же освобождение происходит за городом, тогда водителю предлагается вручную указать предпочтительную зону.
  *
  *  ВСЕ1 БАЗОВЫЙ ЦЫКЛ ИЛИ АЛГОРИТМ РАБОТЫ ВОДИТЕЛЯ ЗАКОНЧЕН И ТАК ВСЕ ПО КРУГУ ПОКА ВОДИТЕЛЬ НЕ ЗАВЕРШИТ СМЕНУ.
  *
  * ВСЕ ОСТАЛЬНО ЧТО МОЖЕТ ДЕЛАТЬ APP МОЖНО НАЗВАТЬ НАВОРОТАМИ ИЛИ ПОНТАМИ НАВОРАЧИВАТЬ КОТОРЫЕ МОЖНО БЕСКОНЕЧНО И В РАЗНЫХ ВАРИАНТАХ ЧТО ЛИШАЕТ СМЫСЛА ДЕЛАТЬ СКРИНШОТЫ ЭТИХ НАВОРОТОВ.
 *
 *
 * НАВОРОТЫ И НАСТРОЙКИ ВЫНЕСЕНЫ В МЕНЮ ОПЦИИ И ДОСТУПНЫ ТОЛЬКО ВОДИТЕЛЯМ ЗАРЕГИСТРИРОВАННЫМ НА СЕРВЕРЕ ДС. ТАК ЖЕ ВОДИТЕЛЬ МОЖЕТ САМОСТОЯТЕЛЬНО УСТАНАВЛИВАТЬ СВОИ НАСТРОЙКИ ДЛЯ ТЕХ РЕЖИМОВ КОГДА ИСПОЛЬЗУЕТСЯ ОФЛАЙН ТАКСОМЕТР.
 *
 * \subsection ofline OfLine GPSTaximetr
 *
 */




/*!
\brief  The parent class is not carrying any meaning

This class has only one simple goal: Start APP
 */
/*!
    AP when running, the user can switch to the other activities of their choice.
*/
public class MainActivity extends Activity implements View.OnClickListener {



    private LinearLayout fistPageLayayt;
    private TextView firstStartPageTextWiev;
    private String pseudo_tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== START APP MainActivity ===============");

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean wasShown = prefs.getBoolean(TetGlobalData.EULA_APP_KEY, false);

        if (!wasShown) { /*   check EULA show */
            new mobi.tet_a_tet.atda.mutual.AppsEula(this).show();/** opening eula*/
        }

        setContentView(R.layout.activity_main);


        findViewById(R.id.btnStartWithoutTetATet).setOnClickListener(this);
        findViewById(R.id.btnStartWithTetATet).setOnClickListener(this);
        findViewById(R.id.btnStartWithTetATetAndOther).setOnClickListener(this);
        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.buttonAboutTet).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(pseudo_tag, "onResume()");
    }


    @Override
    public void onClick(View view) { /** --Select Users for the following windows --*/
        switch (view.getId()) {
            case R.id.btnStartWithoutTetATet: /// start ofline taximetre
                Intent WaU = new Intent(getApplicationContext(), StartOfLineSassistant.class);
                WaU.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(WaU);
                finish();
                break;
            case R.id.btnStartWithTetATet:  /** start taximetre with connect to the Tet-A-Tet server */
                Intent tAt = new Intent(getApplicationContext(), StartTetFirstRegistrationActivity.class);
                tAt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tAt);
                finish();
                //TODO implement
                break;
            case R.id.btnStartWithTetATetAndOther: /** start mutual work with other onetime connected server*/
                //TODO implement
                Intent Oth = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.other_assistant.OtherAsssintantActivity.class);
                Oth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Oth);
                break;
            case R.id.btnExit: /** finish APP */
                finish();
                break;
            case R.id.buttonAboutTet: /** Whot is Tet-a-A-Tet information */
                //TODO implement
                Intent wHt = new Intent(getApplicationContext(), WhotIsTetAtetActivity.class);
                startActivity(wHt);
                break;
        }
        //  finish();
    }

    public void onBackPressed() {
        onResume();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
    }

}
