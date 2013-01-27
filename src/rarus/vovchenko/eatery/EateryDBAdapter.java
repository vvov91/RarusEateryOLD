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

/**
 * ������������� ��������� ��� ������ � ��
 * 
 * @author Victor Vovchenko <v.vovchenko91@gmail.com>
 *
 */
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

    // ��� ������� ������� � ��
    public static final String KEY_ID = "_id";
    
    // ������� DISHES
    public static final String DISHES_NAME_NAME = "name";
    public static final String DISHES_DESCRIPTION_NAME = "description";
    public static final String DISHES_PORTIONED_NAME = "portioned";
    public static final String DISHES_PRICE_NAME = "price";
    public static final String DISHES_RATING_NAME = "rating";

    // ������� MENU
    public static final String MENU_DATE_NAME = "date";
    public static final String MENU_DISH_ID_NAME = "dish_id";
    public static final String MENU_AVALAM_NAME = "available_ammount";	
    public static final String MENU_ORDERAM_NAME = "ordered_ammount";

    // ������� ORDERS
    public static final String ORDERS_DATE_NAME = "date";
    public static final String ORDERS_DISH_ID_NAME = "dish_id";

    // ������� SETTINGS
    public static final String SETTINGS_SERVER_NAME = "server";
    public static final String SETTINGS_LOGIN_NAME = "login";
    public static final String SETTINGS_PASSWORD_NAME = "password";
    public static final String SETTINGS_MODE_NAME = "mode";

    // ������� ��� �������� ������
    // DISHES
    private static final String DB_CREATE_DISHES = "CREATE TABLE " +
        DB_TABLE_DISHES + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        DISHES_NAME_NAME + " TEXT NOT NULL, " +
        DISHES_DESCRIPTION_NAME + " TEXT NOT NULL, " +
        DISHES_PORTIONED_NAME + " INT DEFAULT 0, " +
        DISHES_PRICE_NAME + " FLOAT NOT NULL, " +
        DISHES_RATING_NAME + " TEXT DEFAULT 0);";

    // MENU
    private static final String DB_CREATE_MENU = "CREATE TABLE " +
        DB_TABLE_MENU + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        MENU_DATE_NAME + " DATE NOT NULL, " +
        MENU_DISH_ID_NAME + " INTEGER NOT NULL, " +
        MENU_AVALAM_NAME + " FLOAT NOT NULL DEFAULT -1, " +
        MENU_ORDERAM_NAME + " FLOAT NOT NULL DEFAULT 0);";

    // ORDERS
    private static final String DB_CREATE_ORDERS = "CREATE TABLE " +
        DB_TABLE_ORDERS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        ORDERS_DATE_NAME + " DATE NOT NULL, " +
        ORDERS_DISH_ID_NAME + " INTEGER NOT NULL);";

    // SETTINGS
    private static final String DB_CREATE_SETTINGS = "CREATE TABLE " +
        DB_TABLE_SETTINGS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        SETTINGS_SERVER_NAME + " TEXT NOT NULL, " +
        SETTINGS_LOGIN_NAME + " TEXT NOT NULL, " +
        SETTINGS_PASSWORD_NAME + " TEXT NOT NULL, " +
        SETTINGS_MODE_NAME + " TEXT NOT NULL DEFAULT online);";
	
	// ��������� ��
	private SQLiteDatabase mDb;
	
	// ������ Context ����������, ������� ���������� ��
	private final Context context;
	
	// ������ ���������������� ������ ��� ������ � �� 
	private DBHelper mDbHelper;

    /**
	 * ����������� ������
	 * 
	 * @param _context
	 *     ������� {@link Context}
	 */
	public EateryDBAdapter(Context _context) {
		this.context = _context;
		mDbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * ��������� ���������� � ��.
	 * ���� ������� ���� � ������ ������ ����������, ���� ����������� � ������ ������.
	 * 
	 * @throws SQLException
	 *     ���� �� �� �������� ��� ������ � ��������� � � ������ ������.
	 */
	public void open() throws SQLException {
		try {
			mDb = mDbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = mDbHelper.getReadableDatabase();
		}
	}

	/**
	 * ��������� ���������� � ��
	 */
	public void close() {
		mDb.close();
	}	

	/**
	 * ��������� ������ �������� � ���������� ��
	 */
	private static class DBHelper extends SQLiteOpenHelper {
		/**
		 * ����������� ������
		 * 
		 * @param context
		 *     ������� {@link Context}
		 * @param name
		 *     ��� ����� ��
		 * @param factory
		 *     ������� ��������
		 * @param version
		 *     ������ ��
		 */
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		// � ������ ���� �� ��� �� �������
		@Override
		public void onCreate(SQLiteDatabase _db) {
			// ������ ����������� �������
			_db.execSQL(DB_CREATE_DISHES);
			_db.execSQL(DB_CREATE_MENU);
			_db.execSQL(DB_CREATE_ORDERS);
			_db.execSQL(DB_CREATE_SETTINGS);
		}

		// � ������ ���� ������������ �� �� ������������� ����������� ������
		// � ��������� � ����������
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// �� � ��� ���� � ����������
			// � ���������� ���� �� ���������
			// TODO: ����������� ������� ���������� �� � ������ �������� � ����� ������ 
		}		
	}

	
	// �����

	/**
	 * ��������� �����
	 * 
	 * @param _name
	 *     ��������
	 * @param _description
	 *     ��������
	 * @param _portioned
	 *     ������������
	 * @param _price
	 *     ����
	 * @param _rating
	 *     �������
	 */
	void dishAdd(String _name, String _description, boolean _portioned, float _price, String _rating) {
		mDb.beginTransaction();
		try {
			ContentValues data = new ContentValues();
		    data.put(DISHES_NAME_NAME, _name);
		    data.put(DISHES_DESCRIPTION_NAME, _description);
		    data.put(DISHES_PORTIONED_NAME, (_portioned ? 1 : 0));
		    data.put(DISHES_PRICE_NAME, _price);
		    data.put(DISHES_RATING_NAME, _rating);
		    mDb.insert(DB_TABLE_DISHES, null, data);		    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}	

	/**
	 * ������� �����
	 * 
	 * @param _id
	 *    id �����
	 */
	void dishDelete(int _id) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, KEY_ID + " = " + _id, null);	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� �����
	 */
	void dishDeleteAll() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, null, null);	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	// ��������� �������� �����
	/**
	 * �������� ������� �����
	 * 
	 * @param _id
	 *     id �����
	 * @param _rating
	 *     �������
	 */
	void dishSetRating(int _id, String _rating) {
		mDb.beginTransaction();
		try {
			ContentValues data = new ContentValues();
			data.put(DISHES_RATING_NAME, _rating);
			mDb.update(DB_TABLE_DISHES, data, KEY_ID + " = ?",
					new String[] {Integer.toString(_id)});	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	
	// ����

	/**
	 * ��������� ������� � ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @param _dish_id
	 *     id �����
	 * @param _availbale_ammount
	 *     ��������� ��� ������ �����
	 * @param _ordered_ammount
	 *     ���������� �����
	 */
	void menuAdd(String _date, int _dish_id, float _availbale_ammount, float _ordered_ammount) {
		mDb.beginTransaction();
		try {
			ContentValues data = new ContentValues();
			data.put(MENU_DATE_NAME, _date);
			data.put(MENU_DISH_ID_NAME, _dish_id);
			data.put(MENU_AVALAM_NAME, _availbale_ammount);
			data.put(MENU_ORDERAM_NAME, _ordered_ammount);
			mDb.insert(DB_TABLE_MENU, null, data);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ������� ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @param _dish_id
	 *     id �����
	 */
	void menuDelete(String _date, int _dish_id) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ? AND " + MENU_DISH_ID_NAME + " = ?",
					new String[] {_date, Integer.toString(_dish_id)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� ����
	 */
	void menuDeleteAll() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, null, null);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ���� �� ������������ ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 */
	void menuDeleteAtDate(String _date) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ?", new String[] {_date});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}  

	/**
	 * ���������� ������ ����� � ������ �� ������� �������� ����
	 * 
	 * @return
	 *     {@link ArrayList} �� {@link String} ���.
	 *     ���� � ������� YYYY-MM-DD. ������������� �� �����������.
	 */
	ArrayList<String> menuGetDates() {
		ArrayList<String> result = new ArrayList<String>();
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(true, DB_TABLE_MENU, new String[] {MENU_DATE_NAME}, null, null, 
					null, null, MENU_DATE_NAME + " ASC", null);
			mDb.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					result.add(c.getString(0));
				} while(c.moveToNext());
				c.close();
			}
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * ���������� ���������� ��� �� ������� �������� ����
	 * 
	 * @return
	 *     int � ���-��� ���
	 */
	int menuGetDatesCount() {
		int result = 0;
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery("SELECT COUNT(DISTINCT " + MENU_DATE_NAME + ") " +
					"FROM " + DB_TABLE_MENU, new String[] {});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * ���������� ������� {@link Dish} ���� �� ������������ ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     {@link ArrayList} �� �������� {@link Dish}
	 */
	ArrayList<Dish> menuGetListAtDate(String _date) {
		ArrayList<Dish> result = new ArrayList<Dish>();
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, DB_TABLE_MENU + " AS MU INNER JOIN " + DB_TABLE_DISHES +
					" AS DS ON MU." + MENU_DISH_ID_NAME + " = DS." + KEY_ID,
					new String[] {"DS." + KEY_ID, DISHES_NAME_NAME, DISHES_DESCRIPTION_NAME, 
					DISHES_PORTIONED_NAME, DISHES_PRICE_NAME, DISHES_RATING_NAME, 
					MENU_AVALAM_NAME, MENU_ORDERAM_NAME},
					MENU_DATE_NAME + " = ?", new String[] {_date}, null, null, null, null);
			mDb.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					Dish dish = new Dish();
					dish.set_id(c.getInt(0));
					dish.setName(c.getString(1));
					dish.setDescription(c.getString(2));
					dish.setPortioned((c.getInt(3) == 0 ? false : true));
					dish.setPrice(c.getFloat(4));
					dish.setRating(c.getString(5));
					dish.setAvailable_ammount(c.getFloat(6));
					dish.setOrdered_ammount(c.getFloat(7));
					result.add(dish);
				} while(c.moveToNext());
				c.close();
			}
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * ���������� ���������� ���� � ���� �� ������������ ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     int ���-�� ���
	 */
	int menuGetCountAtDate(String _date) {
		int result = 0;
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery("SELECT COUNT(" + MENU_DISH_ID_NAME + ") FROM " + 
					DB_TABLE_MENU + " WHERE " + MENU_DATE_NAME + " = ?", new String[] {_date});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	
	// ������

	/**
	 * ��������� �����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @param _dish_id
	 *     id �����
	 * @param _order_ammount
	 *     ����� �����������
	 */
	void orderAdd(String _date, int _dish_id, float _order_ammount) {
		mDb.beginTransaction();
		try {
			ContentValues data_orders = new ContentValues();
			data_orders.put(ORDERS_DATE_NAME, _date);
			data_orders.put(ORDERS_DISH_ID_NAME, _dish_id);
			mDb.insert(DB_TABLE_ORDERS, null, data_orders);
			
			ContentValues data_menu = new ContentValues();
			data_menu.put(MENU_ORDERAM_NAME, _order_ammount);
			mDb.update(DB_TABLE_MENU, data_menu, MENU_DATE_NAME + " = ? AND " +
					MENU_DISH_ID_NAME + " = ?", new String[] {_date, Integer.toString(_dish_id)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ����� �� ������������ ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @param _dish_id
	 *     id �����
	 */
	void orderDelete(String _date, int _dish_id) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ? AND " + 
					ORDERS_DISH_ID_NAME + " = ?", new String[] {_date, Integer.toString(_dish_id)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� ������ �� ������������ ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 */
	void orderDeleteAtDate(String _date) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ?", new String[] {_date});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� ������
	 */
	void orderDeleteAll() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, null, null);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ���������� ����� ���������� �������
	 * 
	 * @return
	 *     int ���-�� �������
	 */
	int orderGetCount() {
		int result = 0;
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery("SELECT COUNT(DISTINCT " + ORDERS_DATE_NAME + ") " +
					"FROM " + DB_TABLE_ORDERS, new String[] {});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * ���������� ���������� ���������� ���� �� ������������ ����
	 * 
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     int ���-�� ���������� ����
	 */
	int orderGetCountAtDate(String _date) {
		int result = 0;
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery("SELECT COUNT(" + ORDERS_DISH_ID_NAME + ") " +
					"FROM " + DB_TABLE_ORDERS + " WHERE " + ORDERS_DATE_NAME + " = ?", 
					new String[] {_date});
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * ���������� ������� {@link Dish} ������� �� ������������ ����
	 * @param _date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     {@link ArrayList} �� �������� {@link Dish}
	 */
	ArrayList<Dish> orderGetListAtDate(String _date) {
		ArrayList<Dish> result = new ArrayList<Dish>();
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, DB_TABLE_ORDERS + " AS OS INNER JOIN " + DB_TABLE_DISHES + 
					" AS DS ON OS." + ORDERS_DISH_ID_NAME + " = DS." + KEY_ID + 
					" INNER JOIN " + DB_TABLE_MENU + " AS MU ON OS." + ORDERS_DISH_ID_NAME + 
					" = MU." +  MENU_DISH_ID_NAME + " AND OS." + ORDERS_DATE_NAME + " = MU." 
					+ MENU_DATE_NAME,
					new String[] {"DS." + KEY_ID, DISHES_NAME_NAME, DISHES_DESCRIPTION_NAME, 
					DISHES_PORTIONED_NAME, DISHES_PRICE_NAME, DISHES_RATING_NAME, 
					MENU_AVALAM_NAME, MENU_ORDERAM_NAME},
					"OS." + ORDERS_DATE_NAME + " = ?", new String[] {_date}, null, null, null, null);
			mDb.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					Dish dish = new Dish();
					dish.set_id(c.getInt(0));
					dish.setName(c.getString(1));
					dish.setDescription(c.getString(2));
					dish.setPortioned((c.getInt(3) == 0 ? false : true));
					dish.setPrice(c.getFloat(4));
					dish.setRating(c.getString(5));
					dish.setAvailable_ammount(c.getFloat(6));
					dish.setOrdered_ammount(c.getFloat(7));
					result.add(dish);
				} while(c.moveToNext());
				c.close();
			}
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	
	/**
	 * ������� ��� ������ �� ������, ���� � �������
	 */
	void deleteAll() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, null, null);
			mDb.delete(DB_TABLE_MENU, null, null);
			mDb.delete(DB_TABLE_ORDERS, null, null);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	
	// ���������

	/**
	 * ��������� ���������
	 * 
	 * @param _server
	 *     ����� �������
	 * @param _login
	 *     �����
	 * @param _password
	 *     ������
	 * @param _mode
	 *     ����� ������
	 */
	void settingsSave(String _server, String _login, String _password, String _mode) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_SETTINGS, null, null);
			ContentValues data = new ContentValues();
			data.put(SETTINGS_SERVER_NAME, _server);
			data.put(SETTINGS_LOGIN_NAME, _login);
			data.put(SETTINGS_PASSWORD_NAME, _password);
			data.put(SETTINGS_MODE_NAME, _mode);
			mDb.insert(DB_TABLE_SETTINGS, null, data);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ���������� ���������
	 * 
	 * @return
	 *     {@link ArrayList} �� {@link String} � �����������:
	 *     ����� �������, �����, ������, ����� ������
	 */
	ArrayList<String> settingsGet() {
		ArrayList<String> result = new ArrayList<String>();
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, DB_TABLE_SETTINGS, null, null, null, null, null, null, null);
			c.moveToFirst();
			result.add(c.getString(1));
			result.add(c.getString(2));
			result.add(c.getString(3));
			result.add(c.getString(4));
			c.close();
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}
}