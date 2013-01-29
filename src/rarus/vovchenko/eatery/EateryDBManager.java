package rarus.vovchenko.eatery;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Предоставляет интерфейс для работы с БД
 * 
 * @author Victor Vovchenko <v.vovchenko91@gmail.com>
 *
 */
public class EateryDBManager extends SQLiteOpenHelper {
	private final String LOG_TAG = this.getClass().getName();
	
    // файл БД
    private static final String DB_NAME = "Eatery.db";
    // версия БД
    private static final int DB_VERSION = 1;

    // таблицы 
    private static final String DB_TABLE_DISHES = "dishesTable";
    private static final String DB_TABLE_MENU = "menuTable";
    private static final String DB_TABLE_ORDERS = "ordersTable";
    private static final String DB_TABLE_SETTINGS = "settingsTable";

    // имя каждого столбца в БД
    public static final String KEY_ID = "_id";
    
    // таблица DISHES
    public static final String DISHES_NAME_NAME = "name";
    public static final String DISHES_DESCRIPTION_NAME = "description";
    public static final String DISHES_PORTIONED_NAME = "portioned";
    public static final String DISHES_PRICE_NAME = "price";
    public static final String DISHES_RATING_NAME = "rating";

    // таблица MENU
    public static final String MENU_DATE_NAME = "date";
    public static final String MENU_DISH_ID_NAME = "dish_id";
    public static final String MENU_AVALAM_NAME = "available_ammount";	
    public static final String MENU_ORDERAM_NAME = "ordered_ammount";

    // таблица ORDERS
    public static final String ORDERS_DATE_NAME = "date";
    public static final String ORDERS_DISH_ID_NAME = "dish_id";

    // таблица SETTINGS
    public static final String SETTINGS_SERVER_NAME = "server";
    public static final String SETTINGS_LOGIN_NAME = "login";
    public static final String SETTINGS_PASSWORD_NAME = "password";
    public static final String SETTINGS_MODE_NAME = "mode";

	// экземпляр БД
	private SQLiteDatabase mDb;

    /**
	 * Конструктор класса
	 * 
	 * @param _context
	 *     текущий {@link Context}
	 */
	public EateryDBManager(Context _context) {
		super(_context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * Открывает соединение с БД.
	 * Если открыть базу в режиме записи невозможно, база открывается в режиме чтения.
	 * 
	 * @throws SQLException
	 *     если БД не доступна для записи и открывает её в режиме чтения.
	 */
	public void open() throws SQLException {
		try {
			mDb = this.getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = this.getReadableDatabase();
			
			Log.e(LOG_TAG, "Failed to open database in read/write mode. DB is opened in read mode");
		}
	}

	/**
	 * Закрывает соединение с БД
	 */
	public void close() {
		mDb.close();
	}	

	// в случае если БД ещё не создана
	@Override
	public void onCreate(SQLiteDatabase _db) {
		StringBuilder query = new StringBuilder();
		
		// создаём необходимые таблицы
	    // DISHES
		query.append("CREATE TABLE ");
		query.append(DB_TABLE_DISHES).append(" (").append(KEY_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		query.append(DISHES_NAME_NAME).append(" TEXT NOT NULL, ");
		query.append(DISHES_DESCRIPTION_NAME).append(" TEXT NOT NULL, ");
		query.append(DISHES_PORTIONED_NAME).append(" INT DEFAULT 0, ");
		query.append(DISHES_PRICE_NAME).append(" FLOAT NOT NULL, ");
		query.append(DISHES_RATING_NAME).append(" TEXT DEFAULT 0);");		
		_db.execSQL(query.toString());
		
		query = new StringBuilder();
		
		// MENU		
		query.append("CREATE TABLE ");
		query.append(DB_TABLE_MENU).append(" (").append(KEY_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		query.append(MENU_DATE_NAME).append(" DATE NOT NULL, ");
		query.append(MENU_DISH_ID_NAME).append(" INTEGER NOT NULL, ");
		query.append(MENU_AVALAM_NAME).append(" FLOAT NOT NULL DEFAULT -1, ");
		query.append(MENU_ORDERAM_NAME).append(" FLOAT NOT NULL DEFAULT 0);");
		_db.execSQL(query.toString());
		
		query = new StringBuilder();
		
		// ORDERS
		query.append("CREATE TABLE ");
		query.append(DB_TABLE_ORDERS).append(" (").append(KEY_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		query.append(ORDERS_DATE_NAME).append(" DATE NOT NULL, ");
		query.append(ORDERS_DISH_ID_NAME).append(" INTEGER NOT NULL);");
		_db.execSQL(query.toString());
		
		query = new StringBuilder();
		
		// SETTINGS
		query.append("CREATE TABLE ");
		query.append(DB_TABLE_SETTINGS).append(" (").append(KEY_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		query.append(SETTINGS_SERVER_NAME).append(" TEXT NOT NULL, ");
		query.append(SETTINGS_LOGIN_NAME).append(" TEXT NOT NULL, ");
		query.append(SETTINGS_PASSWORD_NAME).append(" TEXT NOT NULL, ");
		query.append(SETTINGS_MODE_NAME).append(" TEXT NOT NULL DEFAULT online);");
		_db.execSQL(query.toString());
		
		Log.i(LOG_TAG, "Created database");
	}

	// в случае если существующая БД не соответствует необходимой версии
	// и нуждается в обновлении
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// БД у нас пока в разработке
		// в обновлении пока не нуждается
		// TODO: Реализовать процесс обновления БД в случае перехода к новой версии 
	}		
	
	
	// БЛЮДА

	/**
	 * Добавляет блюдо
	 * 
	 * @param name
	 *     название
	 * @param description
	 *     описание
	 * @param portioned
	 *     порционность
	 * @param price
	 *     цена
	 * @param rating
	 *     рейтинг
	 */
	public void addDish(String name, String description, boolean portioned, float price, String rating) {
		ContentValues data = new ContentValues();
	    data.put(DISHES_NAME_NAME, name);
	    data.put(DISHES_DESCRIPTION_NAME, description);
	    data.put(DISHES_PORTIONED_NAME, (portioned ? 1 : 0));
	    data.put(DISHES_PRICE_NAME, price);
	    data.put(DISHES_RATING_NAME, rating);
	    mDb.beginTransaction();
	    
		try {
		    mDb.insert(DB_TABLE_DISHES, null, data);		    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}	

	/**
	 * Удаляет блюдо
	 * 
	 * @param id
	 *    id блюда
	 */
	public void deleteDish(int id) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, KEY_ID + " = " + id, null);	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * Удаляет все блюда
	 */
	public void deleteAllDish() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, null, null);	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Deleted all dishes");			
		}
	}

	// изменение рейтинга блюда
	/**
	 * Изменяет рейтинг блюда
	 * 
	 * @param id
	 *     id блюда
	 * @param rating
	 *     рейтинг
	 */
	public void setDishRating(int id, String rating) {
		ContentValues data = new ContentValues();
		data.put(DISHES_RATING_NAME, rating);
		
		mDb.beginTransaction();		
		try {
			mDb.update(DB_TABLE_DISHES, data, KEY_ID + " = ?", new String[] {Integer.toString(id)});	    
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	
	// МЕНЮ

	/**
	 * Добавляет элемент в меню
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @param dishId
	 *     id блюда
	 * @param availbaleAmmount
	 *     доступный для заказа объём
	 * @param orderedAmmount
	 *     заказанный объём
	 */
	public void addMenu(String date, int dishId, float availbaleAmmount, float orderedAmmount) {
		ContentValues data = new ContentValues();
		data.put(MENU_DATE_NAME, date);
		data.put(MENU_DISH_ID_NAME, dishId);
		data.put(MENU_AVALAM_NAME, availbaleAmmount);
		data.put(MENU_ORDERAM_NAME, orderedAmmount);
		
		mDb.beginTransaction();
		try {
			mDb.insert(DB_TABLE_MENU, null, data);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * Удаляет элемент меню
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @param dishId
	 *     id блюда
	 */
	public void deleteMenu(String date, int dishId) {
		StringBuilder query = new StringBuilder();
		query.append(MENU_DATE_NAME).append(" = ? AND ").append(MENU_DISH_ID_NAME).append(" = ?");
		
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, query.toString(),
					new String[] {date, Integer.toString(dishId)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * Удаляет все меню
	 */
	public void deleteAllMenu() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, null, null);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Deleted all menus");
		}
	}

	/**
	 * Удаляет меню на определенную дату
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 */
	public void deleteMenuAtDate(String date) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_MENU, MENU_DATE_NAME + " = ?", new String[] {date});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Deleted menu, date (" + date + ")");
		}
	}  

	/**
	 * Возвращает массив строк с датами на которые доступно меню
	 * 
	 * @return
	 *     {@link List} из {@link String} дат.
	 *     Даты в формате YYYY-MM-DD. Отсортированы по возрастанию.
	 */
	public List<String> getMenuDates() {
		List<String> result = new ArrayList<String>();
		
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
	 * Вовзращает количество дат на которые доступно меню
	 * 
	 * @return
	 *     int с кол-вом дат
	 */
	public int getMenuDatesCount() {
		int result = 0;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(DISTINCT ").append(MENU_DATE_NAME).append(") ");
		query.append("FROM ").append(DB_TABLE_MENU);
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery(query.toString(), new String[] {});
			mDb.setTransactionSuccessful();			

			c.moveToFirst();
			result = c.getInt(0);
			c.close();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * Возвращает объекты {@link Dish} меню на определенную дату
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @return
	 *     {@link List} из объектов {@link Dish}
	 */
	public List<Dish> getMenuListAtDate(String date) {
		List<Dish> result = new ArrayList<Dish>();
		
		StringBuilder query = new StringBuilder();
		query.append(DB_TABLE_MENU).append(" AS MU INNER JOIN ").append(DB_TABLE_DISHES);
		query.append(" AS DS ON MU.").append(MENU_DISH_ID_NAME).append(" = DS.").append(KEY_ID);
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, query.toString(),
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
	 * Возвращает количество блюд в меню на определенную дату
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @return
	 *     int кол-во дат
	 */
	public int getMenuCountAtDate(String date) {
		int result = 0;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(").append(MENU_DISH_ID_NAME).append(") FROM ");
		query.append(DB_TABLE_MENU).append(" WHERE ").append(MENU_DATE_NAME).append(" = ?");
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery(query.toString(), new String[] {date});
			mDb.setTransactionSuccessful();			

			c.moveToFirst();
			result = c.getInt(0);
			c.close();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	
	// ЗАКАЗЫ

	/**
	 * Добавляет заказ
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @param dishId
	 *     id блюда
	 * @param orderAmmount
	 *     объём заказанного
	 */
	public void addOrder(String date, int dishId, float orderAmmount) {
		ContentValues data_orders = new ContentValues();
		data_orders.put(ORDERS_DATE_NAME, date);
		data_orders.put(ORDERS_DISH_ID_NAME, dishId);
		
		ContentValues data_menu = new ContentValues();
		data_menu.put(MENU_ORDERAM_NAME, orderAmmount);
		
		StringBuilder query = new StringBuilder();
		query.append(MENU_DATE_NAME).append(" = ? AND ").append(MENU_DISH_ID_NAME).append(" = ?");
		
		mDb.beginTransaction();
		try {
			mDb.insert(DB_TABLE_ORDERS, null, data_orders);
			
			mDb.update(DB_TABLE_MENU, data_menu, query.toString(),
					new String[] {date, Integer.toString(dishId)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * Удаляет заказ на определенную дату
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @param dishId
	 *     id блюда
	 */
	public void deleteOrder(String date, int dishId) {
		StringBuilder query = new StringBuilder();
		query.append(ORDERS_DATE_NAME).append(" = ? AND ").append(ORDERS_DISH_ID_NAME).append(" = ?");
		
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, query.toString(), new String[] {date, Integer.toString(dishId)});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * Удаляет все заказы на определенную дату
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 */
	public void deleteOrderAtDate(String date) {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, ORDERS_DATE_NAME + " = ?", new String[] {date});
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Deleted order, date (" + date + ")");
		}
	}

	/**
	 * Удаляет все заказы
	 */
	public void deleteAllOrder() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_ORDERS, null, null);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Deleted all orders");
		}
	}

	/**
	 * Возвращает общее количество заказов
	 * 
	 * @return
	 *     int кол-во заказов
	 */
	public int getOrderCount() {
		int result = 0;		

		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(DISTINCT ").append(ORDERS_DATE_NAME).append(") ");
		query.append("FROM ").append(DB_TABLE_ORDERS);
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery(query.toString(), new String[] {});			
			mDb.setTransactionSuccessful();
			
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * Возвращает количество заказанных блюд на определенную дату
	 * 
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @return
	 *     int кол-во заказанных блюд
	 */
	public int getOrderCountAtDate(String date) {
		int result = 0;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(").append(ORDERS_DISH_ID_NAME).append(") ").append("FROM ");
		query.append(DB_TABLE_ORDERS).append(" WHERE ").append(ORDERS_DATE_NAME).append(" = ?");
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.rawQuery(query.toString(), new String[] {date});
			mDb.setTransactionSuccessful();
			
			c.moveToFirst();
			result = c.getInt(0);
			c.close();
		} finally {
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * Возвращает объекты {@link Dish} заказов на определенную дату
	 * @param date
	 *     дата в формате YYYY-MM-DD
	 * @return
	 *     {@link List} из объектов {@link Dish}
	 */
	public List<Dish> getOrderListAtDate(String date) {
		List<Dish> result = new ArrayList<Dish>();
		
		StringBuilder query = new StringBuilder();
		query.append(DB_TABLE_ORDERS).append(" AS OS INNER JOIN ").append(DB_TABLE_DISHES);
		query.append(" AS DS ON OS.").append(ORDERS_DISH_ID_NAME).append(" = DS.").append(KEY_ID);
		query.append(" INNER JOIN ").append(DB_TABLE_MENU).append(" AS MU ON OS.");
		query.append(ORDERS_DISH_ID_NAME).append(" = MU.").append(MENU_DISH_ID_NAME);
		query.append(" AND OS.").append(ORDERS_DATE_NAME).append(" = MU.").append(MENU_DATE_NAME);
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, query.toString(),
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
	 * Удаляет все данные по блюдам, меню и заказам
	 */
	public void deleteAll() {
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_DISHES, null, null);
			mDb.delete(DB_TABLE_MENU, null, null);
			mDb.delete(DB_TABLE_ORDERS, null, null);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Database cleared");
		}
	}

	
	// НАСТРОЙКИ

	/**
	 * Сохраняет настройки
	 * 
	 * @param server
	 *     адрес сервера
	 * @param login
	 *     логин
	 * @param password
	 *     пароль
	 * @param mode
	 *     режим работы
	 */
	public void saveSettings(String server, String login, String password, String mode) {
		ContentValues data = new ContentValues();
		data.put(SETTINGS_SERVER_NAME, server);
		data.put(SETTINGS_LOGIN_NAME, login);
		data.put(SETTINGS_PASSWORD_NAME, password);
		data.put(SETTINGS_MODE_NAME, mode);
		
		mDb.beginTransaction();
		try {
			mDb.delete(DB_TABLE_SETTINGS, null, null);
			mDb.insert(DB_TABLE_SETTINGS, null, data);
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}
	}

	/**
	 * Возвращает настройки
	 * 
	 * @return
	 *     {@link List} из {@link String} с настройками:
	 *     адрес сервера, логин, пароль, режим работы
	 */
	public List<String> getSettings() {
		List<String> result = new ArrayList<String>();
		
		mDb.beginTransaction();
		try {
			Cursor c = mDb.query(false, DB_TABLE_SETTINGS, null, null, null, null, null, null, null);
			mDb.setTransactionSuccessful();
			
			c.moveToFirst();
			result.add(c.getString(1));
			result.add(c.getString(2));
			result.add(c.getString(3));
			result.add(c.getString(4));
			c.close();
		} finally {
			mDb.endTransaction();
			
			Log.i(LOG_TAG, "Settings updated");
		}
		return result;
	}
}