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
 public void addMenu(int date, List<Dish> dishes)
	Добавляет меню.
	Дата в Unix time формате, List из объектов Dish.
-----------
 public void deleteAllMenu()
	Удаление всех меню.
-----------
 public void deleteMenuAtDate(int date)
	Удаление меню на определенную дату.
	Дата в Unix time формате.
-----------
 public List<Integer> getMenuDates()
	Возвращает список дат на которые доступно меню.
	Возвращает List из Intger Unix time дат. Даты остортированы по возрастанию.
-----------
 public int getMenuDatesCount()
	Возвращает количество дат на которые доступны меню.
-----------
 public List<Dish> getMenuListAtDate(int date)
	Возвращает меню на определенную дату.
	Дата в Unix time формате. Возвращает List объектов Dish с полями:
		- id блюда
		- название
		- описание
		- порционность
		- цена
		- рейтинг
		- доступный для заказа объём
		- заказанный объём
-----------
 public int getMenuCountAtDate(int date)
	Возвращает количество блюд находящихся в меню на определенную дату.
	Дата в Unix time формате.
-----------
 public void addOrder(int date, List<Dish> dishes)
	Добавление заказа.
	Дата в Unix time формате, List из объектов Dish.
-----------
 public void deleteOrder(int date, int dishId)
	Удаление блюда из заказа.
	Дата в Unix time формате и id блюда.
-----------
 void deleteOrderAtDate(int date)
	Удаление всего заказанного на определенную дату.
	Дата в Unix time формате.
-----------
 public void deleteAllOrder()
	Удаление всех заказов.
-----------
 public int getOrderCount()
	Возвращает количество имеющихся заказов.
-----------
 public int getOrderCountAtDate(int date)
	Возвращает количество заказанных блюд на определённую дату.
	Дата в Unix time формате.
-----------
 public List<Dish> getOrderListAtDate(int date)
	Возвращает заказанные на определенную дату блюда.
	Дата в Unix time формате. Возвращает List объектов Dish с полями:
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