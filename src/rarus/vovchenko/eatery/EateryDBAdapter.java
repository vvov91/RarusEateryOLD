package rarus.vovchenko.eatery;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	// имя каждого столбца в БД
	// DISHES
	// название
	public static final String DISHES_NAME_NAME = "name";
	// описание
	public static final String DISHES_DESCRIPTION_NAME = "description";
	// порционность
	public static final String DISHES_PORTIONED_NAME = "portioned";
	// цена
	public static final String DISHES_PRICE_NAME = "price";
	// рейтинг
	public static final String DISHES_RATING_NAME = "rating";
	//-----------------------------------------------------
	// MENU
	// дата
	public static final String MENU_DATE_NAME = "date";
	// id блюда
	public static final String MENU_DISH_ID_NAME = "dish_id";
	// доступный для заказа объём
	public static final String MENU_AVALAM_NAME = "available_ammount";	
	// объём заказанного
	public static final String MENU_ORDERAM_NAME = "ordered_ammount";
	//-----------------------------------------------------
	// ORDERS
	// дата
	public static final String ORDERS_DATE_NAME = "date";
	// id блюда
	public static final String ORDERS_DISH_ID_NAME = "dish_id";
	//-----------------------------------------------------
	// SETTINGS
	// адрес сервера	
	public static final String SETTINGS_SERVER_NAME = "server";
	// логин
	public static final String SETTINGS_LOGIN_NAME = "login";
	// пароль
	public static final String SETTINGS_PASSWORD_NAME = "password";
	// режим работы
	public static final String SETTINGS_MODE_NAME = "mode";
	//-----------------------------------------------------

	//-----------------------------------------------------
	// запросы для создания таблиц
	// DISHES
	private static final String DB_CREATE_DISHES = "CREATE TABLE " +
		DB_TABLE_DISHES + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		DISHES_NAME_NAME + " TEXT NOT NULL, " +
		DISHES_DESCRIPTION_NAME + " TEXT NOT NULL, " +
		DISHES_PORTIONED_NAME + " INT DEFAULT 0, " +
		DISHES_PRICE_NAME + " FLOAT NOT NULL, " +
		DISHES_RATING_NAME + " TEXT DEFAULT 0);";
	//-----------------------------------------------------
	// MENU
	private static final String DB_CREATE_MENU = "CREATE TABLE " +
		DB_TABLE_MENU + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		MENU_DATE_NAME + " DATE NOT NULL, " +
		MENU_DISH_ID_NAME + " INTEGER NOT NULL, " +
		MENU_AVALAM_NAME + " FLOAT NOT NULL DEFAULT -1, " +
		MENU_ORDERAM_NAME + " FLOAT NOT NULL DEFAULT 0);";
	//-----------------------------------------------------
	// ORDERS
	private static final String DB_CREATE_ORDERS = "CREATE TABLE " +
		DB_TABLE_ORDERS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		ORDERS_DATE_NAME + " DATE NOT NULL, " +
		ORDERS_DISH_ID_NAME + " INTEGER NOT NULL);";
	//-----------------------------------------------------
	// SETTINGS
	private static final String DB_CREATE_SETTINGS = "CREATE TABLE " +
		DB_TABLE_SETTINGS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		SETTINGS_SERVER_NAME + " TEXT NOT NULL, " +
		SETTINGS_LOGIN_NAME + " TEXT NOT NULL, " +
		SETTINGS_PASSWORD_NAME + " TEXT NOT NULL, " +
		SETTINGS_MODE_NAME + " TEXT NOT NULL DEFAULT online);";
	//-----------------------------------------------------
	
	// экземпляр БД
	private SQLiteDatabase db;
	
	// объект Context приложения, которое использует БД
	private final Context context;
	
	// объект вспомогательного класса для работы с БД 
	private DBHelper dbHelper;
		
	//-----------------------------------------------------
	// конструктор
	public EateryDBAdapter(Context _context) {
		this.context = _context;
		dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
	}
	//-----------------------------------------------------
	
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
	
	//-----------------------------------------------------
	// вспомогательный класс для работы с БД
	private static class DBHelper extends SQLiteOpenHelper {
		// конструктор
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}
		//----------------------------------------------------
		// в случае если БД ещё не создана
		@Override
		public void onCreate(SQLiteDatabase _db) {
			// создаём необходимые таблицы
			_db.execSQL(DB_CREATE_DISHES);
			_db.execSQL(DB_CREATE_MENU);
			_db.execSQL(DB_CREATE_ORDERS);
			_db.execSQL(DB_CREATE_SETTINGS);
		}
		//-----------------------------------------------------
		// в случае если существующая БД не соответствует необходимой версии
		// и нуждается в обновлении
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// БД у нас пока в разработке
			// в обновлении пока не нуждается
			// поэтому это просто заглушка
		}		
		//-----------------------------------------------------
	}
	//-----------------------------------------------------
	
	
	//-----------------------------------------------------
	// БЛЮДА
	//-----------------------------------------------------
	// добавление блюда
	void dishAdd(String _name, String _description, int _portioned, float _price, String _rating) {
		db.beginTransaction();
		try {
			ContentValues data = new ContentValues();
		    data.put(DISHES_NAME_NAME, _name);
		    data.put(DISHES_DESCRIPTION_NAME, _description);
		    data.put(DISHES_PORTIONED_NAME, _portioned);
		    data.put(DISHES_PRICE_NAME, _price);
		    data.put(DISHES_RATING_NAME, _rating);
		    db.insert(DB_TABLE_DISHES, null, data);		    
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}	
	//-----------------------------------------------------
	// удаление блюда
	void dishDelete(int _id) {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_DISHES, KEY_ID + " = " + _id, null);	    
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление всех блюд
	void dishDeleteAll() {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_DISHES, null, null);	    
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// изменение рейтинга блюда
	void dishSetRating(int _id, String _rating) {
		db.beginTransaction();
		try {
			ContentValues data = new ContentValues();
			data.put(DISHES_RATING_NAME, _rating);
			db.update(DB_TABLE_DISHES, data, KEY_ID + " = ?", new String[] {Integer.toString(_id)});	    
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	
	
	//-----------------------------------------------------
	// МЕНЮ
	//-----------------------------------------------------
	// добавление элемента меню
	void menuAdd(String _date, int _dish_id, float _availbale_ammount, float _ordered_ammount) {
		db.beginTransaction();
		try {
			ContentValues data = new ContentValues();
			data.put(MENU_DATE_NAME, _date);
			data.put(MENU_DISH_ID_NAME, _dish_id);
			data.put(MENU_AVALAM_NAME, _availbale_ammount);
			data.put(MENU_ORDERAM_NAME, _ordered_ammount);
			db.insert(DB_TABLE_MENU, null, data);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление элемента меню
	void menuDelete(String _date, int _dish_id) {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ? AND " + MENU_DISH_ID_NAME + " = ?", new String[] {_date, Integer.toString(_dish_id)});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление всех меню
	void menuDeleteAll() {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_MENU, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление меню на определенную дату
	void menuDeleteAtDate(String _date) {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ?", new String[] {_date});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}  
	//-----------------------------------------------------
	// получение массива строк с датами на которые доступны меню
	ArrayList<String> menuGetDates() {
		ArrayList<String> result = new ArrayList<String>();
		db.beginTransaction();
		try {
			Cursor c = db.query(true, DB_TABLE_MENU, new String[] {MENU_DATE_NAME}, null, null, null, null, MENU_DATE_NAME + " ASC", null);
			db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					result.add(c.getString(0));
				} while(c.moveToNext());
				c.close();
			}
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	// получение количества дат на которые доступны меню
	int menuGetDatesCount() {
		int result = 0;
		db.beginTransaction();
		try {
			Cursor c = db.rawQuery("SELECT COUNT(DISTINCT " + MENU_DATE_NAME + ") FROM " + DB_TABLE_MENU, new String[] {});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	// получение меню на определённую дату
	ArrayList<Dish> menuGetListAtDate(String _date) {
		ArrayList<Dish> result = new ArrayList<Dish>();
		db.beginTransaction();
		try {
			Cursor c = db.query(false, DB_TABLE_MENU + " AS MU INNER JOIN " + DB_TABLE_DISHES + " AS DS ON MU." + MENU_DISH_ID_NAME + " = DS." + KEY_ID,
					new String[] {"DS." + KEY_ID, DISHES_NAME_NAME, DISHES_DESCRIPTION_NAME, DISHES_PORTIONED_NAME, DISHES_PRICE_NAME, DISHES_RATING_NAME, MENU_AVALAM_NAME, MENU_ORDERAM_NAME},
					MENU_DATE_NAME + " = ?", new String[] {_date}, null, null, null, null);
			db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					Dish dish = new Dish();
					dish.set_id(c.getInt(0));
					dish.setName(c.getString(1));
					dish.setDescription(c.getString(2));
					dish.setPortioned(c.getInt(3));
					dish.setPrice(c.getFloat(4));
					dish.setRating(c.getString(5));
					dish.setAvailable_ammount(c.getFloat(6));
					dish.setOrdered_ammount(c.getFloat(7));
					result.add(dish);
				} while(c.moveToNext());
				c.close();
			}
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	// получение количества блюд в меню на определенную дату
	int menuGetCountAtDate(String _date) {
		int result = 0;
		db.beginTransaction();
		try {
			Cursor c = db.rawQuery("SELECT COUNT(" + MENU_DISH_ID_NAME + ") FROM " + DB_TABLE_MENU + " WHERE " + MENU_DATE_NAME + " = ?", new String[] {_date});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	
	
	//-----------------------------------------------------
	// ЗАКАЗЫ
	//-----------------------------------------------------
	// добавление заказа
	void orderAdd(String _date, int _dish_id, float _order_ammount) {
		db.beginTransaction();
		try {
			ContentValues data_orders = new ContentValues();
			data_orders.put(ORDERS_DATE_NAME, _date);
			data_orders.put(ORDERS_DISH_ID_NAME, _dish_id);
			db.insert(DB_TABLE_ORDERS, null, data_orders);
			
			ContentValues data_menu = new ContentValues();
			data_menu.put(MENU_ORDERAM_NAME, _order_ammount);
			db.update(DB_TABLE_MENU, data_menu, MENU_DATE_NAME + " = ? AND " + MENU_DISH_ID_NAME + " = ?", new String[] {_date, Integer.toString(_dish_id)});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление заказанного блюда на определенную дату
	void orderDelete(String _date, int _dish_id) {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ? AND " + ORDERS_DISH_ID_NAME + " = ?", new String[] {_date, Integer.toString(_dish_id)});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление всех заказов на определенную дату
	void orderDeleteAtDate(String _date) {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ?", new String[] {_date});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// удаление всех заказов
	void orderDeleteAll() {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_ORDERS, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// получение количества заказов
	int orderGetCount() {
		int result = 0;
		db.beginTransaction();
		try {
			Cursor c = db.rawQuery("SELECT COUNT(DISTINCT " + ORDERS_DATE_NAME + ") FROM " + DB_TABLE_ORDERS, new String[] {});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	// получение количества заказанных блюд на определённую дату
	int orderGetCountAtDate(String _date) {
		int result = 0;
		db.beginTransaction();
		try {
			Cursor c = db.rawQuery("SELECT COUNT(" + ORDERS_DISH_ID_NAME + ") FROM " + DB_TABLE_ORDERS + " WHERE " + ORDERS_DATE_NAME + " = ?", new String[] {_date});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	// получение заказанных на определённую дату блюд
	ArrayList<Dish> ordersGetListAtDate(String _date) {
		ArrayList<Dish> result = new ArrayList<Dish>();
		db.beginTransaction();
		try {
			Cursor c = db.query(false, DB_TABLE_ORDERS + " AS OS INNER JOIN " + DB_TABLE_DISHES + " AS DS ON OS." + ORDERS_DISH_ID_NAME + " = DS." + KEY_ID + 
					" INNER JOIN " + DB_TABLE_MENU + " AS MU ON OS." + ORDERS_DISH_ID_NAME + " = MU." +  MENU_DISH_ID_NAME + " AND OS." + ORDERS_DATE_NAME + " = MU." + MENU_DATE_NAME,
					new String[] {"DS." + KEY_ID, DISHES_NAME_NAME, DISHES_DESCRIPTION_NAME, DISHES_PORTIONED_NAME, DISHES_PRICE_NAME, DISHES_RATING_NAME, MENU_AVALAM_NAME, MENU_ORDERAM_NAME},
					"OS." + ORDERS_DATE_NAME + " = ?", new String[] {_date}, null, null, null, null);
			db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					Dish dish = new Dish();
					dish.set_id(c.getInt(0));
					dish.setName(c.getString(1));
					dish.setDescription(c.getString(2));
					dish.setPortioned(c.getInt(3));
					dish.setPrice(c.getFloat(4));
					dish.setRating(c.getString(5));
					dish.setAvailable_ammount(c.getFloat(6));
					dish.setOrdered_ammount(c.getFloat(7));
					result.add(dish);
				} while(c.moveToNext());
				c.close();
			}
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
	
	
	//-----------------------------------------------------
	// удалить все данные по блюдам, меню и заказам
	void deleteAll() {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_DISHES, null, null);
			db.delete(DB_TABLE_MENU, null, null);
			db.delete(DB_TABLE_ORDERS, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	
	
	//-----------------------------------------------------
	// НАСТРОЙКИ
	//-----------------------------------------------------
	// сохранение настроек
	void settingsSave(String _server, String _login, String _password, String _mode) {
		db.beginTransaction();
		try {
			db.delete(DB_TABLE_SETTINGS, null, null);
			ContentValues data = new ContentValues();
			data.put(SETTINGS_SERVER_NAME, _server);
			data.put(SETTINGS_LOGIN_NAME, _login);
			data.put(SETTINGS_PASSWORD_NAME, _password);
			data.put(SETTINGS_MODE_NAME, _mode);
			db.insert(DB_TABLE_SETTINGS, null, data);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	//-----------------------------------------------------
	// получение настроек
	ArrayList<String> settingsGet() {
		ArrayList<String> result = new ArrayList<String>();
		db.beginTransaction();
		try {
			Cursor c = db.query(false, DB_TABLE_SETTINGS, null, null, null, null, null, null, null);
			c.moveToFirst();
			result.add(c.getString(1));
			result.add(c.getString(2));
			result.add(c.getString(3));
			result.add(c.getString(4));
			c.close();
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return result;
	}
	//-----------------------------------------------------
}