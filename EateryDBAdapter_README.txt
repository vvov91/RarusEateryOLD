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
 void addDish(String name, String description, boolean portioned, float price, String rating)
	Добавляет блюдо.
	Название, описание, порционность, цена, рейтинг.
-----------
 void deleteDish(int id)
	Удаляет блюдо по его id.
-----------
 void deleteAllDish()
	Удаляет все имеющиеся блюда.
-----------
 void dsetDishRating(int id, String rating)
	Меняет рейтинг блюда по его id.
-----------
 void addMenu(String date, int dishId, float availbaleAmmount, float orderedAmmount)
	Добавляет элемент меню.
	Дата в формате YYYY-MM-DD, id блюда, доступный объём для заказа, кол-во заказанного.
-----------
 void deleteMenu(String date, int dishId)
	Удаляет элемент меню по id блюда на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 void deleteAllMenu()
	Удаление всех меню.
-----------
 void deleteMenuAtDate(String date)
	Удаление меню на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 ArrayList<String> getMenuDates()
	Список дат на которые доступно меню.
	Возвращает ArrayList из строк дат в формате YYYY-MM-DD. Даты остортированы по возрастанию.
-----------
 int getMenuDatesCount()
	Возвращает количество дат на которые доступны меню.
-----------
 ArrayList<Dish> getMenuListAtDate(String date)
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
 int getMenuCountAtDate(String date)
	Возвращает количество блюд находящихся в меню на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 void addOrder(String date, int dishId, float orderAmmount)
	Добавление блюда в заказ.
	Дата в формате YYYY-MM-DD, id блюда и количество заказанного.
-----------
 void deleteOrder(String date, int dishId)
	Удаление блюда из заказа.
	Дата в формате YYYY-MM-DD и id блюда.
-----------
 void deleteOrderAtDate(String date)
	Удаление всего заказанного на определенную дату.
	Дата в формате YYYY-MM-DD.
-----------
 void deleteAllOrder()
	Удаление всех заказов.
-----------
 int getOrderCount()
	Возвращает количество имеющихся заказов.
-----------
 int getOrderCountAtDate(String date)
	Возвращает количество заказанных блюд на определённую дату.
	Дата в формате YYYY-MM-DD.
-----------
 ArrayList<Dish> getOrderListAtDate(String date)
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
 void saveSettings(String server, String login, String password, String mode)
	Сохраняет настройки.
	Адрес сервера, логин, пароль и режим работы (online/offline).
-----------
 ArrayList<String> getSettings()
	Возвращает настройки.
	ArrayList строк с настройками: адрес сервера, логин, пароль и режим работы.
-----------