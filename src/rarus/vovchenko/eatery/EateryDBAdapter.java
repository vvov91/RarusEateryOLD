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
	// ���� ��
	private static final String DB_NAME = "Eatery.db";
	// ������ ��
	private static final int DB_VERSION = 1;
	
	// ������� 
	private static final String DB_TABLE_DISHES = "dishesTable";
	private static final String DB_TABLE_MENU = "menuTable";
	private static final String DB_TABLE_ORDERS = "ordersTable";
	private static final String DB_TABLE_SETTINGS = "settingsTable";
	
	// ������ (����) �������
	public static final String KEY_ID = "_id";

	//-----------------------------------------------------
	// ��� ������� ������� � ��
	// DISHES
	// ��������
	public static final String DISHES_NAME_NAME = "name";
	// ��������
	public static final String DISHES_DESCRIPTION_NAME = "description";
	// ������������
	public static final String DISHES_PORTIONED_NAME = "portioned";
	// ����
	public static final String DISHES_PRICE_NAME = "price";
	// �������
	public static final String DISHES_RATING_NAME = "rating";
	//-----------------------------------------------------
	// MENU
	// ����
	public static final String MENU_DATE_NAME = "date";
	// id �����
	public static final String MENU_DISH_ID_NAME = "dish_id";
	// ��������� ��� ������ �����
	public static final String MENU_AVALAM_NAME = "available_ammount";	
	// ����� �����������
	public static final String MENU_ORDERAM_NAME = "ordered_ammount";
	//-----------------------------------------------------
	// ORDERS
	// ����
	public static final String ORDERS_DATE_NAME = "date";
	// id �����
	public static final String ORDERS_DISH_ID_NAME = "dish_id";
	//-----------------------------------------------------
	// SETTINGS
	// ����� �������	
	public static final String SETTINGS_SERVER_NAME = "server";
	// �����
	public static final String SETTINGS_LOGIN_NAME = "login";
	// ������
	public static final String SETTINGS_PASSWORD_NAME = "password";
	// ����� ������
	public static final String SETTINGS_MODE_NAME = "mode";
	//-----------------------------------------------------

	//-----------------------------------------------------
	// ������� ��� �������� ������
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
	
	// ��������� ��
	private SQLiteDatabase db;
	
	// ������ Context ����������, ������� ���������� ��
	private final Context context;
	
	// ������ ���������������� ������ ��� ������ � �� 
	private DBHelper dbHelper;
		
	//-----------------------------------------------------
	// �����������
	public EateryDBAdapter(Context _context) {
		this.context = _context;
		dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
	}
	//-----------------------------------------------------
	
	//-----------------------------------------------------
	// �������� ���������� � ��
	// ������������ ����������
	// ���� ������� ���� � ������ ������ ����������
	// ���� ����������� � ������ ������
	public void open() throws SQLException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}
	//-----------------------------------------------------
	// �������� ���������� � ��
	public void close() {
		db.close();
	}	
	//-----------------------------------------------------
	
	//-----------------------------------------------------
	// ��������������� ����� ��� ������ � ��
	private static class DBHelper extends SQLiteOpenHelper {
		// �����������
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}
		//----------------------------------------------------
		// � ������ ���� �� ��� �� �������
		@Override
		public void onCreate(SQLiteDatabase _db) {
			// ������ ����������� �������
			_db.execSQL(DB_CREATE_DISHES);
			_db.execSQL(DB_CREATE_MENU);
			_db.execSQL(DB_CREATE_ORDERS);
			_db.execSQL(DB_CREATE_SETTINGS);
		}
		//-----------------------------------------------------
		// � ������ ���� ������������ �� �� ������������� ����������� ������
		// � ��������� � ����������
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// �� � ��� ���� � ����������
			// � ���������� ���� �� ���������
			// ������� ��� ������ ��������
		}		
		//-----------------------------------------------------
	}
	//-----------------------------------------------------
	
	
	//-----------------------------------------------------
	// �����
	//-----------------------------------------------------
	// ���������� �����
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
	// �������� �����
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
	// �������� ���� ����
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
	// ��������� �������� �����
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
	// ����
	//-----------------------------------------------------
	// ���������� �������� ����
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
	// �������� �������� ����
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
	// �������� ���� ����
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
	// �������� ���� �� ������������ ����
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
	// ��������� ������� ����� � ������ �� ������� �������� ����
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
	// ��������� ���������� ��� �� ������� �������� ����
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
	// ��������� ���� �� ����������� ����
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
	// ��������� ���������� ���� � ���� �� ������������ ����
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
	// ������
	//-----------------------------------------------------
	// ���������� ������
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
	// �������� ����������� ����� �� ������������ ����
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
	// �������� ���� ������� �� ������������ ����
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
	// �������� ���� �������
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
	// ��������� ���������� �������
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
	// ��������� ���������� ���������� ���� �� ����������� ����
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
	// ��������� ���������� �� ����������� ���� ����
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
	// ������� ��� ������ �� ������, ���� � �������
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
	// ���������
	//-----------------------------------------------------
	// ���������� ��������
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
	// ��������� ��������
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