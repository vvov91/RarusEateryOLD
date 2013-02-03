-------------------------------------
	EateryDBManager
-------------------------------------
  Класс для работы с БД
-------------------------------------
  Конструктор:
 EateryDBManager(Context _context)
-----------
  Методы:
-----------
 public void open()
	Открывает соединение с БД.
-----------
 public void close()
	Соединение закрывает.
-----------
 public void deleteAllDish()
	Удаляет все имеющиеся блюда.
-----------
 public void setDishRating(int id, String rating)
	Меняет рейтинг блюда по его id.
-----------
 public void addMenu(String date, List<Dish> dishes)
	Добавляет меню.
	Дата в формате YYYY-MM-DD, List из объектов Dish.
-----------
 public void deleteAllMenu()
	Удаление всех меню.
-----------
 public void deleteMenuAtDate(String date)
	Удаление меню на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 public List<String> getMenuDates()
	Список дат на которые доступно меню.
	Возвращает List из строк дат в формате YYYY-MM-DD. Даты остортированы по возрастанию.
-----------
 public int getMenuDatesCount()
	Возвращает количество дат на которые доступны меню.
-----------
 public List<Dish> getMenuListAtDate(String date)
	Возвращает меню на определенную дату.
	Дата в формате YYYY-MM-DD. Возвращает List объектов Dish с полями:
		- id блюда
		- название
		- описание
		- порционность
		- цена
		- рейтинг
		- доступный для заказа объём
		- заказанный объём
-----------
 public int getMenuCountAtDate(String date)
	Возвращает количество блюд находящихся в меню на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 public void addOrder(String date, List<Dish> dishes)
	Добавление заказа.
	Дата в формате YYYY-MM-DD, List из объектов Dish.
-----------
 public void deleteOrder(String date, int dishId)
	Удаление блюда из заказа.
	Дата в формате YYYY-MM-DD и id блюда.
-----------
 void deleteOrderAtDate(String date)
	Удаление всего заказанного на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 public void deleteAllOrder()
	Удаление всех заказов.
-----------
 public int getOrderCount()
	Возвращает количество имеющихся заказов.
-----------
 public int getOrderCountAtDate(String date)
	Возвращает количество заказанных блюд на определённую дату.
	Дата в формате YYYY-MM-DD.
-----------
 public ArrayList<Dish> getOrderListAtDate(String date)
	Возвращает заказанные на определенную дату блюда.
	Дата в формате YYYY-MM-DD. Возвращает List объектов Dish с полями:
		- id блюда
		- название
		- описание
		- порционность
		- цена
		- рейтинг
		- доступный для заказа объём
		- заказанный объём
-----------
 public void deleteAll()
	Удаляет все данные по блюдам, меню и заказам.
-----------
 public void saveSettings(String server, String login, String password, String mode)
	Сохраняет настройки.
	Адрес сервера, логин, пароль и режим работы (online/offline).
-----------
 public List<String> getSettings()
	Возвращает настройки.
	List строк с настройками: адрес сервера, логин, пароль и режим работы.
-----------