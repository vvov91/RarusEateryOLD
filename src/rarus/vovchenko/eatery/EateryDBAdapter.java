package rarus.vovchenko.eatery;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class EateryDBAdapter {
	// файл БД
	private static final String DB_NAME = "Eatery.db";
	// версия БД
	private static final int DB_VERSION = 1;
	
	// таблицы 
	private static final String DB_TABLE_DISHES = "dishesTable";
	private static final String DB_TABLE_MENU = "menuTable";
	private static final String DB_TABLE_ORDERS = "ordersTable";
	private static final String DB_TABLE_SETTINGS = "settingsTable";
	
	// индекс (ключ) столбца
	public static final String KEY_ID = "_id";

	//-----------------------------------------------------
	// имя и индекс каждого столбца в БД
	// DISHES
	public static final String DISHES_NAME_NAME = "name";
	public static final int DISHES_NAME_COLUMN = 1;
	
	public static final String DISHES_DESCRIPTION_NAME = "description";
	public static final int DISHES_DESCRIPTION_COLUMN = 2;
	
	public static final String DISHES_PRICE_NAME = "price";
	public static final int DISHES_PRICE_COLUMN = 3;
	
	public static final String DISHES_RATING_NAME = "rating";
	public static final int DISHES_RATING_COLUMN = 4;
	//-----------------------------------------------------
	// MENU
	public static final String MENU_DATE_NAME = "date";
	public static final int MENU_DATE_COLUMN = 1;
		
	public static final String MENU_ID_DISH_NAME = "id_dish";
	public static final int MENU_ID_DISH_COLUMN = 2;
		
	public static final String MENU_AVAL_NAME = "aval";
	public static final int MENU_AVAL_COLUMN = 3;
	//-----------------------------------------------------
	// ORDERS
	public static final String ORDERS_DATE_NAME = "date";
	public static final int ORDERS_DATE_COLUMN = 1;
		
	public static final String ORDERS_ID_DISH_NAME = "id_dish";
	public static final int ORDERS_ID_DISH_COLUMN = 2;
		
	public static final String ORDERS_AMMOUNT_NAME = "ammount";
	public static final int ORDERS_AMMOUNT_COLUMN = 3;
	//-----------------------------------------------------
	// SETTINGS
	public static final String SETTINGS_LOGIN_NAME = "login";
	public static final int SETTINGS_LOGIN_COLUMN = 1;
		
	public static final String SETTINGS_PASS_NAME = "pass";
	public static final int SETTINGS_PASS_COLUMN = 2;
		
	public static final String SETTINGS_SERVER_NAME = "server";
	public static final int SETTINGS_SERVER_COLUMN = 3;
	//-----------------------------------------------------

	//-----------------------------------------------------
	// запросы для создания таблиц
	// DISHES
	private static final String DB_CREATE_DISHES = "CREATE TABLE " +
		DB_TABLE_DISHES + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		DISHES_NAME_NAME + " TEXT NOT NULL, " +
		DISHES_DESCRIPTION_NAME + " TEXT NOT NULL, " +
		DISHES_PRICE_NAME + " FLOAT NOT NULL, " +
		DISHES_RATING_NAME + " FLOAT NOT NULL DEFAULT 0);";
	//-----------------------------------------------------
	// DISHES
	private static final String DB_CREATE_MENU = "CREATE TABLE " +
		DB_TABLE_MENU + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		MENU_DATE_NAME + " DATE NOT NULL, " +
		MENU_ID_DISH_NAME + " INTEGER NOT NULL, " +
		MENU_AVAL_NAME + " INTEGER NOT NULL DEFAULT -1);";
	//-----------------------------------------------------
	// ORDERS
	private static final String DB_CREATE_ORDERS = "CREATE TABLE " +
			DB_TABLE_ORDERS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			ORDERS_DATE_NAME + " DATE NOT NULL, " +
			ORDERS_ID_DISH_NAME + " INTEGER NOT NULL, " +
			ORDERS_AMMOUNT_NAME + " INTEGER NOT NULL);";
	//-----------------------------------------------------
	// SETTINGS
	private static final String DB_CREATE_SETTINGS = "CREATE TABLE " +
			DB_TABLE_SETTINGS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			SETTINGS_LOGIN_NAME + " TEXT NOT NULL, " +
			SETTINGS_PASS_NAME + " TEXT NOT NULL, " +
			SETTINGS_SERVER_NAME + " TEXT NOT NULL);";
	//-----------------------------------------------------
	
	// экземпляр БД
	private SQLiteDatabase db;
	
	// объект Context приложения, которое использует БД
	private final Context context;
	
	// класс для взаимодействия с БД
	private DBHelper dbHelper;
	
	//-----------------------------------------------------
	// конструктор
	public EateryDBAdapter(Context _context) {
		this.context = _context;
		dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
	}
	//-----------------------------------------------------
	// открытие соединения с БД
	// используется исключение
	// если открыть базу в режиме записи невозможно
	// база открывается в режиме чтения
	public void open() throws SQLException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}
	//-----------------------------------------------------
	// закрытие соединения с БД
	public void close() {
		db.close();
	}	
	//-----------------------------------------------------
	// вспомогательный класс для взаимодействия с БД
	private static class DBHelper extends SQLiteOpenHelper {
		// конструктор
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		// в случае если БД ещё не создана
		@Override
		public void onCreate(SQLiteDatabase _db) {
			// создаём необходимые таблицы
			_db.execSQL(DB_CREATE_DISHES);
			_db.execSQL(DB_CREATE_MENU);
			_db.execSQL(DB_CREATE_ORDERS);
			_db.execSQL(DB_CREATE_SETTINGS);
		}

		// в случае если существующая БД не соответствует необходимой версии
		// и нуждается в обновлении
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// заглушка :)
		}
	}
}