-------------------------------------
	EateryDBManager
-------------------------------------
  ����� ��� ������ � ��
-------------------------------------
  �����������:
 EateryDBManager(Context _context)
-----------
  ������:
-----------
 public void open()
	��������� ���������� � ��.
-----------
 public void close()
	���������� ���������.
-----------
 public void deleteAllDish()
	������� ��� ��������� �����.
-----------
 public void setDishRating(int id, String rating)
	������ ������� ����� �� ��� id.
-----------
 public void addMenu(String date, List<Dish> dishes)
	��������� ����.
	���� � ������� YYYY-MM-DD, List �� �������� Dish.
-----------
 public void deleteAllMenu()
	�������� ���� ����.
-----------
 public void deleteMenuAtDate(String date)
	�������� ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 public List<String> getMenuDates()
	������ ��� �� ������� �������� ����.
	���������� List �� ����� ��� � ������� YYYY-MM-DD. ���� ������������� �� �����������.
-----------
 public int getMenuDatesCount()
	���������� ���������� ��� �� ������� �������� ����.
-----------
 public List<Dish> getMenuListAtDate(String date)
	���������� ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD. ���������� List �������� Dish � ������:
		- id �����
		- ��������
		- ��������
		- ������������
		- ����
		- �������
		- ��������� ��� ������ �����
		- ���������� �����
-----------
 public int getMenuCountAtDate(String date)
	���������� ���������� ���� ����������� � ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 public void addOrder(String date, List<Dish> dishes)
	���������� ������.
	���� � ������� YYYY-MM-DD, List �� �������� Dish.
-----------
 public void deleteOrder(String date, int dishId)
	�������� ����� �� ������.
	���� � ������� YYYY-MM-DD � id �����.
-----------
 void deleteOrderAtDate(String date)
	�������� ����� ����������� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 public void deleteAllOrder()
	�������� ���� �������.
-----------
 public int getOrderCount()
	���������� ���������� ��������� �������.
-----------
 public int getOrderCountAtDate(String date)
	���������� ���������� ���������� ���� �� ����������� ����.
	���� � ������� YYYY-MM-DD.
-----------
 public ArrayList<Dish> getOrderListAtDate(String date)
	���������� ���������� �� ������������ ���� �����.
	���� � ������� YYYY-MM-DD. ���������� List �������� Dish � ������:
		- id �����
		- ��������
		- ��������
		- ������������
		- ����
		- �������
		- ��������� ��� ������ �����
		- ���������� �����
-----------
 public void deleteAll()
	������� ��� ������ �� ������, ���� � �������.
-----------
 public void saveSettings(String server, String login, String password, String mode)
	��������� ���������.
	����� �������, �����, ������ � ����� ������ (online/offline).
-----------
 public List<String> getSettings()
	���������� ���������.
	List ����� � �����������: ����� �������, �����, ������ � ����� ������.
-----------