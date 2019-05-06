package mobi.tet_a_tet.atda.off_lline.Controllers;

/**
 * Created by oleg on 27.08.15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.off_lline.Adapters.varsForAdapter;

public class dbController extends SQLiteOpenHelper implements Parcelable {
    //важная вещь - не менять
    public static final Parcelable.Creator<dbController> CREATOR = new Parcelable.Creator<dbController>() {
        public dbController createFromParcel(Parcel in) {
            return new dbController(in);
        }

        public dbController[] newArray(int size) {
            return new dbController[size];
        }
    };
    static Context cnt;//контекст, необходим для работы контроллера
    public String tag;//для отладочной работы, на работу приложения не влияет
    //result - необхом для возврата результата обратно в окно
    public int tempId = -1;//необходим для хранения промежуточных id (id тарифа, полигона и прочих)
    String query;//для sql запросов
    Cursor cursor = null;//курсор для обхода информации, полученной из бд
    SQLiteDatabase database;//нативный компонент, позволяющий работать с бд
    private int userId, result;//userId - переменная для id пользователя, который работает с программой

    public dbController(Context applicationcontext) {
        super(applicationcontext, "driversBase.db_controller", null, 1);
        cnt = applicationcontext;
        tag = cnt.getResources().getString(R.string.tag);
        database = this.getWritableDatabase();
    }

    private dbController(Parcel parcel) {//читает данные для другого окна
        super(cnt, "driversBase.db_controller", null, 1);

        userId = parcel.readInt();
        tempId = parcel.readInt();
        tag = parcel.readString();

        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {//создание базы данных при первом запуске программы
        query = "DROP TABLE IF EXISTS driver;"
                + "DROP TABLE IF EXISTS server;"
                + "DROP TABLE IF EXISTS mode;"
                + "DROP TABLE IF EXISTS polygon;"
                + "DROP TABLE IF EXISTS tarif;";
        database.execSQL(query);
        query = "CREATE TABLE driver ( id INTEGER PRIMARY KEY, fName varchar(20), sName varchar(20), tName varchar(20),"
                + "password varchar(20), licence varchar(20), spd varchar(20),"
                + "serialCar varchar(20), cityWork varchar(20), nameIDE varchar(20),"
                + "cityIDE varchar(20), streetIDE varchar(20), houseIDE varchar(20),"
                + "roomIDE varchar(20), okpo varchar(20) );";
        database.execSQL(query);
        query = "create table server(id INTEGER PRIMARY KEY,"
                + "userId integer, nameServer varchar(20),"
                + "ipMain varchar(20), portMain varchar(20),"
                + "ipSafe varchar(20), portSafe varchar(20),"
                + "ipGPS varchar(20), portGPS varchar(20), workServer boolean);";
        database.execSQL(query);
        query = "create table mode(id integer primary key,"
                + "userId integer, name varchar(255), val varchar(255), work boolean);";
        database.execSQL(query);
        query = "create table polygon(id integer primary key,"
                + "userId integer, name varchar(255), address varchar(255));";
        database.execSQL(query);
        query = "create table tarif (id integer primary key,"
                + "userId integer, sitdown varchar(20), minCost varchar(20), kmCost varchar(20),"
                + "minuteCostStay varchar(20), minKmCost varchar(20), minuteMinCost varchar(20),"
                + "countryCost varchar(20), countryBackCost varchar(20), minSpeed varchar(20));";
        database.execSQL(query);

        String[] driverSql = {
                "insert into driver (id, serialCar, password) values (1, 1234, 1234);",
                "insert into tarif (userId, sitdown, minCost, kmCost, minuteCostStay, minKmCost, minuteMinCost, countryCost, countryBackCost, minSpeed) values "
                        + "(1, '100', '100', '100', '100', '100', '100', '100', '100', '100');",
                "insert into mode (userId, name, val, work) values (1, 'Программа не будет подключаться к серверу', 'connectToServer', 'true');",
                "insert into mode (userId, name, val, work) values (1, 'Зоны будут указываться водителем вручную', 'manualSetting', 'true');",
                "insert into mode (userId, name, val, work) values (1, 'Показывать копейки', 'showMoney', 'true');"
        };
        for (String str : driverSql) database.execSQL(str);//выполняем сами запросы
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {//при изменении версии базы данных
        query = "DROP TABLE IF EXISTS driver;"
                + "DROP TABLE IF EXISTS server;"
                + "DROP TABLE IF EXISTS mode;"
                + "DROP TABLE IF EXISTS polygon;"
                + "DROP TABLE IF EXISTS tarif;";
        database.execSQL(query);
        onCreate(database);
    }

    public int countDrivers() {//возвращает кол-во пользователей в бд

        query = "select * from driver";
        cursor = database.rawQuery(query, null);
        result = cursor.getCount();
        cursor.close();

        if (result == 1) userId = 1;


        return result;
    }

    public int checkLogin(String login, String pass) { //проверка авторизации

        query = "select id from driver where serialCar = " + login + " and password = " + pass + ";";
        cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
            return userId;
        } else userId = 0;
        cursor.close();

        return userId;
    }

    public void test() {//ничего особенного
        /*String query = "insert into server(userId, nameServer, ipMain, portMain, ipSafe, portSafe, ipGPS, portGPS, workServer)"
                + "values (1, 'ema1', '127.0.0.1', '5555', '127.0.0.1', '5555', '127.0.0.1', '5555', 'true');";
		database.execSQL(query);

		query = "insert into server(userId, nameServer, ipMain, portMain, ipSafe, portSafe, ipGPS, portGPS, workServer)"
				+ "values (1, 'ema2', '127.0.0.2', '5555', '127.0.0.2', '5555', '127.0.0.2', '5555', 'false');";
		database.execSQL(query);

		query = "insert into mode (userId, name, val, work) values (1, 'Программа не будет подключаться к серверу', 'connectToServer', 'true');";
		database.execSQL(query);

		query = "insert into mode (userId, name, val, work) values (1, 'Зоны будут указываться водителем вручную', 'manualSetting', 'true');";
		database.execSQL(query);

		query = "insert into mode (userId, name, val, work) values (1, ' Показывать копейки', 'showMoney', 'true');";
		database.execSQL(query);

		query = "insert into polygon (userId, name, address) values (1, 'ema1', '12.13.15,14.15.18');";
		database.execSQL(query);

		query = "insert into polygon (userId, name, address) values (1, 'ema2', '12.13.15,14.15.18');";
		database.execSQL(query);*/

        query = "insert into tarif (userId, sitdown, minCost, kmCost, minuteCostStay, minKmCost, minuteMinCost, countryCost, countryBackCost, minSpeed) values "
                + "(1, '100', '100', '100', '100', '100', '100', '100', '100', '100')";
        database.execSQL(query);
    }

    public int getUserId() {//возвращает id пользователя
        return userId;
    }

    public ArrayList<varsForAdapter> selectDriver() {//ничего особенного

        ArrayList<varsForAdapter> data = new ArrayList<varsForAdapter>();
        varsForAdapter temp;

        query = "select * from driver where id = " + tempId + ";";
        cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        do {
            temp = new varsForAdapter();

            for (String str : cursor.getColumnNames()) {
                temp.driver.add(cursor.getString(cursor.getColumnIndex(str)));
            }

            data.add(temp);

        } while (cursor.moveToNext());
        cursor.close();

        return data;

    }

    public ArrayList<varsForAdapter> select(String type) {//делаем запрос из бд
		/* возвращает массив, состоящий из классов varsForAdapter
		 * (внутри этого класса есть еще один массив из строк, который будет заполняться
		 * информацией из бд)
		 * !!!такое извращение необходимо для адаптера!!!
		 *
		 * например - делаем запрос
		 * query = "select id, serialCar, fName, sName, tName from driver;";
		 * cursor = database.rawQuery(query, null);
		 * находим первый элемент для начала перебора данных
			if(!cursor.moveToFirst()) {
				Log.w(tag, "нет ни одной записи, удовл данному запросу");
				return data;
			}

			do {
				temp = new varsForAdapter();//создаем новую переменную

				for(String str : cursor.getColumnNames()){
					temp.driver.add(cursor.getString(cursor.getColumnIndex(str)));
				}

				полученная структура для нашего запроса, после этого блока будет иметь вид
				temp.driver = {<id>, <serialCar>, <fName>, <sName>, <tName>}

				data.add(temp);полученную переменную добавляем в переменную для возврата

	    	} while (cursor.moveToNext());
			cursor.close();

			return data;
		 *
		 *
		 *
		 */
        ArrayList<varsForAdapter> data = new ArrayList<varsForAdapter>();
        varsForAdapter temp;

        switch (type) {
            case "selectAllDrivers":
                query = "select id, serialCar, fName, sName, tName from driver;";
                break;
            case "selectAllAboutDriver":
                query = "select * from driver where id = " + tempId + ";";
                break;
            case "selectAllServers":
                query = "select id, nameServer, workServer from server where userId = " + userId + ";";
                break;
            case "selectAllAboutServer":
                query = "select * from server where id = " + tempId + ";";
                break;
            case "selectAllPolygon":
                query = "select id, name from polygon where userId = " + userId;
                break;
            case "selectAllAboutPolygon":
                query = "select * from polygon where id = " + tempId;
                break;
            case "selectAllModes":
                query = "select id, name, work from mode where userId = " + userId;
                break;
            case "selectAllAboutMode":
                query = "select * from mode where id = " + tempId;
                break;
            case "selectTarif":
                query = "select id from tarif where userId = " + userId;
                break;
            case "selectInfoTarif":
                query = "select * from tarif where id = " + tempId;
                break;

        }

        //Log.w(tag, "query = " + query);

        cursor = database.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            Log.w(tag, "нет ни одной записи, удовл данному запросу");
            return data;
        }

        do {
            temp = new varsForAdapter();

            for (String str : cursor.getColumnNames()) {
                temp.driver.add(cursor.getString(cursor.getColumnIndex(str)));
            }

            data.add(temp);

        } while (cursor.moveToNext());
        cursor.close();

        return data;

    }

    public void update(String type, ArrayList<String> strArr) {//фунция обновления информации
		/*
		 * для обновления информации о каком-либо типе данных принимает массив из данных (строк)
		 * этот массив формируется и сортируется еще до отправки в эту функцию таким образом, чтобы
		 * соответствовать структуре бд необходимого типа данных.
		 *
		 * например, структура бд mode следующая
		 * поля : id, userId, name, val, work
		 *
		 * поэтому массив будет формироваться следующим образом:
		 * <массив> = {name, val, work } - эти данные будут обновляться
		 * поле id узнаем из переменной tempId, которую установили из вызываемой формы
		 * поле userId узнаем из переменной userId, которая устанавливается при авторизации пользователя
		 *
		 * поэтому при переборе мы опускаем эти поля id, userId.
		 */
        //Log.w(tag, "type = "+strArr.size());
        switch (type) {
            case "driver":
                query = "select * from driver where id = " + tempId;
                cursor = database.rawQuery(query, null);
                query = "";
                for (String str : cursor.getColumnNames())
                    if (!str.equals("id"))
                        if (cursor.getColumnIndex(str) + 1 != cursor.getColumnCount())
                            query = query + str + " = '" + strArr.get(cursor.getColumnIndex(str) - 1) + "', ";
                        else
                            query = query + str + " = '" + strArr.get(cursor.getColumnIndex(str) - 1) + "' ";
                break;

            default:
                query = "select * from " + type + " where id = " + tempId;
                cursor = database.rawQuery(query, null);
                query = "";
                for (String str : cursor.getColumnNames())
                    if (!str.equals("id") & !str.equals("userId"))
                        if (cursor.getColumnIndex(str) + 1 != cursor.getColumnCount())
                            query = query + str + " = '" + strArr.get(cursor.getColumnIndex(str) - 2) + "', ";
                        else
                            query = query + str + " = '" + strArr.get(cursor.getColumnIndex(str) - 2) + "' ";
                break;
        }

        query = "update '" + type + "' set " + query + "where id = " + tempId;
        Log.w(tag, query);
        //query = "update "+type+" set name = 'ema' where id = "+tempId;
        database.execSQL(query);
        cursor.close();
        tempId = -1;
    }

    public void delete(String type) {//фунция удаления записей
        query = "delete from " + type + " where id = " + tempId;
        database.execSQL(query);

        if (type.equals("driver")) {
            String strArr[] = {
                    "delete from server where UserId = " + tempId,
                    "delete from mode where UserId = " + tempId,
                    "delete from polygon where UserId = " + tempId,
                    "delete from tarif where UserId = " + tempId
            };

            for (String str : strArr) database.execSQL(str);
        }
        tempId = -1;
    }

    public void insert(String type, ArrayList<String> strArr) {
		/*
		 * массив данных формируется таким же образом, как и для функции update() см. выше
		 */
        String val = "", key = "";

        switch (type) {
            case "driver"://для таблицы пользователей свой алгоритм добавления записей
                query = "select * from driver";
                cursor = database.rawQuery(query, null);

                for (String str : cursor.getColumnNames())
                    if (!str.equals("id"))
                        if (cursor.getColumnIndex(str) + 1 != cursor.getColumnCount()) {
                            key = key + str + ", ";
                            val = val + "'" + strArr.get(cursor.getColumnIndex(str) - 1) + "', ";
                        } else {
                            key = key + str;
                            val = val + "'" + strArr.get(cursor.getColumnIndex(str) - 1) + "'";
                        }

                //узнаем id вставленной записи
                query = "select max(id) as insertId from driver";
                cursor = database.rawQuery(query, null);
                cursor.moveToFirst();
                tempId = cursor.getInt(cursor.getColumnIndex("insertId"));

                //вставляем записи в базу данных настроек и тарифа для нового пользователя
                String[] driverSql = {
                        "insert into tarif (userId, sitdown, minCost, kmCost, minuteCostStay, minKmCost, minuteMinCost, countryCost, countryBackCost, minSpeed) values "
                                + "(" + tempId + ", '100', '100', '100', '100', '100', '100', '100', '100', '100')",
                        "insert into mode (userId, name, val, work) values (" + tempId + ", 'Программа не будет подключаться к серверу', 'connectToServer', 'true');",
                        "insert into mode (userId, name, val, work) values (" + tempId + ", 'Зоны будут указываться водителем вручную', 'manualSetting', 'true');",
                        "insert into mode (userId, name, val, work) values (" + tempId + ", ' Показывать копейки', 'showMoney', 'true');"
                };
                for (String str : driverSql) database.rawQuery(str, null);//выполняем сами запросы


                break;
            default://для остальных таблиц свой алгоритм
                query = "select * from " + type;
                cursor = database.rawQuery(query, null);

                for (String str : cursor.getColumnNames())
                    if (!str.equals("id") & !str.equals("userId"))
                        if (cursor.getColumnIndex(str) + 1 != cursor.getColumnCount()) {
                            key = key + str + ", ";
                            val = val + "'" + strArr.get(cursor.getColumnIndex(str) - 2) + "', ";
                        } else {
                            key = key + str;
                            val = val + "'" + strArr.get(cursor.getColumnIndex(str) - 2) + "'";
                        }
                key = key + ", userId";
                val = val + ", '" + userId + "'";
                break;
        }

        query = "insert into " + type + " (" + key + ") values (" + val + ")";

        Log.w(tag, "query = " + query);
        database.execSQL(query);
    }

    @Override//важная вещь - не менять
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {//записывает данные, необходимые для другого окна
        parcel.writeInt(userId);
        parcel.writeInt(tempId);
        parcel.writeString(tag);

    }
}