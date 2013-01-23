-------------------------------------
	EateryDBAdapter
-------------------------------------
  Класс для работы с БД
-------------------------------------
  Конструктор:
 EateryDBAdapter(Context _context)
-----------
  Методы:
-----------
 void open()
	Открывает соединение с БД.
-----------
 void close()
	Соединение закрывает.
-----------
 void dishAdd(String _name, String _description, boolean _portioned, float _price, String _rating)
	Добавляет блюдо.
	Название, описание, порционность, цена, рейтинг.
-----------
 void dishDelete(int _id)
	Удаляет блюдо по его id.
-----------
 void dishDeleteAll()
	Удаляет все имеющиеся блюда.
-----------
 void dishSetRating(int _id, String _rating)
	Меняет рейтинг блюда по его id.
-----------
 void menuAdd(String _date, int _dish_id, float _availbale_ammount, float _ordered_ammount)
	Добавляет элемент меню.
	Дата в формате YYYY-MM-DD, id блюда, доступный объём для заказа, кол-во заказанного.
-----------
 void menuDelete(String _date, int _dish_id)
	Удаляет элемент меню по id блюда на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 void menuDeleteAll()
	Удаление всех меню.
-----------
 void menuDeleteAtDate(String _date)
	Удаление меню на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 ArrayList<String> menuGetDates()
	Список дат на которые доступно меню.
	Возвращает ArrayList из строк дат в формате YYYY-MM-DD. Даты остортированы по возрастанию.
-----------
 int menuGetDatesCount()
	Возвращает количество дат на которые доступны меню.
-----------
 ArrayList<Dish> menuGetListAtDate(String _date)
	Возвращает меню на определенную дату.
	Дата в формате YYYY-MM-DD. Возвращает объект Dish (блюдо) с полями:
		- id блюда
		- название
		- описание
		- порционность
		- цена
		- рейтинг
		- доступный для заказа объём
		- заказанный объём
-----------
 int menuGetCountAtDate(String _date)
	Возвращает количество блюд находящихся в меню на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 void orderAdd(String _date, int _dish_id, float _order_ammount)
	Добавление блюда в заказ.
	Дата в формате YYYY-MM-DD, id блюда и количество заказанного.
-----------
 void orderDelete(String _date, int _dish_id)
	Удаление блюда из заказа.
	Дата в формате YYYY-MM-DD и id блюда.
-----------
 void orderDeleteAtDate(String _date)
	Удаление всего заказанного на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 void orderDeleteAll()
	Удаление всех заказов.
-----------
 int orderGetCount()
	Возвращает количество имеющихся заказов.
-----------
 int orderGetCountAtDate(String _date)
	Возвращает количество заказанных блюд на определённую дату.
	Дата в формате YYYY-MM-DD.
-----------
 ArrayList<Dish> ordersGetListAtDate(String _date)
	Возвращает заказанные на определенную дату блюда.
	Дата в формате YYYY-MM-DD. Возвращает объект Dish (блюдо) с полями:
		- id блюда
		- название
		- описание
		- порционность
		- цена
		- рейтинг
		- доступный для заказа объём
		- заказанный объём
-----------
 void deleteAll()
	Удаляет все данные по блюдам, меню и заказам.
-----------
 void settingsSave(String _server, String _login, String _password, String _mode)
	Сохраняет настройки.
	Адрес сервера, логин, пароль и режим работы (online/offline).
-----------
 ArrayList<String> settingsGet()
	Возвращает настройки.
	ArrayList строк с настройками: адрес сервера, логин, пароль и режим работы.
-----------