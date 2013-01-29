package rarus.vovchenko.eatery;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ������������� ��������� ��� ������ � ��
 * 
 * @author Victor Vovchenko <v.vovchenko91@gmail.com>
 *
 */
public class EateryDBManager extends SQLiteOpenHelper {
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
		
    /**
	 * ����������� ������
	 * 
	 * @param _context
	 *     ������� {@link Context}
	 */
	public EateryDBManager(Context _context) {
		super(_context, DB_NAME, null, DB_VERSION);
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
			mDb = this.getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = this.getReadableDatabase();
		}
	}

	/**
	 * ��������� ���������� � ��
	 */
	public void close() {
		mDb.close();
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
	
	
	// �����

	/**
	 * ��������� �����
	 * 
	 * @param name
	 *     ��������
	 * @param description
	 *     ��������
	 * @param portioned
	 *     ������������
	 * @param price
	 *     ����
	 * @param rating
	 *     �������
	 */
	void addDish(String name, String description, boolean portioned, float price, String rating) {
		mDb.beginTransaction();
		try {
			ContentValues data = new ContentValues();
		    data.put(DISHES_NAME_NAME, name);
		    data.put(DISHES_DESCRIPTION_NAME, description);
		    data.put(DISHES_PORTIONED_NAME, (portioned ? 1 : 0));
		    data.put(DISHES_PRICE_NAME, price);
		    data.put(DISHES_RATING_NAME, rating);
		    mDb.insert(DB_TABLE_DISHES, null, data);		    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}	

	/**
	 * ������� �����
	 * 
	 * @param id
	 *    id �����
	 */
	void deleteDish(int id) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, KEY_ID + " = " + id, null);	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� �����
	 */
	void deleteAllDish() {
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
	 * @param id
	 *     id �����
	 * @param rating
	 *     �������
	 */
	void setDishRating(int id, String rating) {
		mDb.beginTransaction();
		try {
			ContentValues data = new ContentValues();
			data.put(DISHES_RATING_NAME, rating);
			mDb.update(DB_TABLE_DISHES, data, KEY_ID + " = ?",
					new String[] {Integer.toString(id)});	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	
	// ����

	/**
	 * ��������� ������� � ����
	 * 
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @param dishId
	 *     id �����
	 * @param availbaleAmmount
	 *     ��������� ��� ������ �����
	 * @param orderedAmmount
	 *     ���������� �����
	 */
	void addMenu(String date, int dishId, float availbaleAmmount, float orderedAmmount) {
		mDb.beginTransaction();
		try {
			ContentValues data = new ContentValues();
			data.put(MENU_DATE_NAME, date);
			data.put(MENU_DISH_ID_NAME, dishId);
			data.put(MENU_AVALAM_NAME, availbaleAmmount);
			data.put(MENU_ORDERAM_NAME, orderedAmmount);
			mDb.insert(DB_TABLE_MENU, null, data);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ������� ����
	 * 
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @param dishId
	 *     id �����
	 */
	void deleteMenu(String date, int dishId) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ? AND " + MENU_DISH_ID_NAME + " = ?",
					new String[] {date, Integer.toString(dishId)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� ����
	 */
	void deleteAllMenu() {
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
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 */
	void deleteMenuAtDate(String date) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ?", new String[] {date});
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
	ArrayList<String> getMenuDates() {
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
	int getMenuDatesCount() {
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
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     {@link ArrayList} �� �������� {@link Dish}
	 */
	ArrayList<Dish> getMenuListAtDate(String date) {
		ArrayList<Dish> result = new ArrayList<Dish>();
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, DB_TABLE_MENU + " AS MU INNER JOIN " + DB_TABLE_DISHES +
					" AS DS ON MU." + MENU_DISH_ID_NAME + " = DS." + KEY_ID,
					new String[] {"DS." + KEY_ID, DISHES_NAME_NAME, DISHES_DESCRIPTION_NAME, 
					DISHES_PORTIONED_NAME, DISHES_PRICE_NAME, DISHES_RATING_NAME, 
					MENU_AVALAM_NAME, MENU_ORDERAM_NAME},
					MENU_DATE_NAME + " = ?", new String[] {date}, null, null, null, null);
			mDb.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					Dish dish = new Dish();
					dish.setId(c.getInt(0));
					dish.setName(c.getString(1));
					dish.setDescription(c.getString(2));
					dish.setPortioned((c.getInt(3) == 0 ? false : true));
					dish.setPrice(c.getFloat(4));
					dish.setRating(c.getString(5));
					dish.setAvailableAmmount(c.getFloat(6));
					dish.setOrderedAmmount(c.getFloat(7));
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
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     int ���-�� ���
	 */
	int getMenuCountAtDate(String date) {
		int result = 0;
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery("SELECT COUNT(" + MENU_DISH_ID_NAME + ") FROM " + 
					DB_TABLE_MENU + " WHERE " + MENU_DATE_NAME + " = ?", new String[] {date});
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
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @param dishId
	 *     id �����
	 * @param orderAmmount
	 *     ����� �����������
	 */
	void addOrder(String date, int dishId, float orderAmmount) {
		mDb.beginTransaction();
		try {
			ContentValues data_orders = new ContentValues();
			data_orders.put(ORDERS_DATE_NAME, date);
			data_orders.put(ORDERS_DISH_ID_NAME, dishId);
			mDb.insert(DB_TABLE_ORDERS, null, data_orders);
			
			ContentValues data_menu = new ContentValues();
			data_menu.put(MENU_ORDERAM_NAME, orderAmmount);
			mDb.update(DB_TABLE_MENU, data_menu, MENU_DATE_NAME + " = ? AND " +
					MENU_DISH_ID_NAME + " = ?", new String[] {date, Integer.toString(dishId)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ����� �� ������������ ����
	 * 
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @param dishId
	 *     id �����
	 */
	void deleteOrder(String date, int dishId) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ? AND " + 
					ORDERS_DISH_ID_NAME + " = ?", new String[] {date, Integer.toString(dishId)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� ������ �� ������������ ����
	 * 
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 */
	void deleteOrderAtDate(String date) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ?", new String[] {date});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * ������� ��� ������
	 */
	void deleteAllOrder() {
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
	int getOrderCount() {
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
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     int ���-�� ���������� ����
	 */
	int getOrderCountAtDate(String date) {
		int result = 0;
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery("SELECT COUNT(" + ORDERS_DISH_ID_NAME + ") " +
					"FROM " + DB_TABLE_ORDERS + " WHERE " + ORDERS_DATE_NAME + " = ?", 
					new String[] {date});
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
	 * @param date
	 *     ���� � ������� YYYY-MM-DD
	 * @return
	 *     {@link ArrayList} �� �������� {@link Dish}
	 */
	ArrayList<Dish> getOrderListAtDate(String date) {
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
					"OS." + ORDERS_DATE_NAME + " = ?", new String[] {date}, null, null, null, null);
			mDb.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					Dish dish = new Dish();
					dish.setId(c.getInt(0));
					dish.setName(c.getString(1));
					dish.setDescription(c.getString(2));
					dish.setPortioned((c.getInt(3) == 0 ? false : true));
					dish.setPrice(c.getFloat(4));
					dish.setRating(c.getString(5));
					dish.setAvailableAmmount(c.getFloat(6));
					dish.setOrderedAmmount(c.getFloat(7));
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
	 * @param server
	 *     ����� �������
	 * @param login
	 *     �����
	 * @param password
	 *     ������
	 * @param mode
	 *     ����� ������
	 */
	void saveSettings(String server, String login, String password, String mode) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_SETTINGS, null, null);
			ContentValues data = new ContentValues();
			data.put(SETTINGS_SERVER_NAME, server);
			data.put(SETTINGS_LOGIN_NAME, login);
			data.put(SETTINGS_PASSWORD_NAME, password);
			data.put(SETTINGS_MODE_NAME, mode);
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
	ArrayList<String> getSettings() {
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