-------------------------------------
	EateryDBAdapter
-------------------------------------
  ����� ��� ������ � ��
-------------------------------------
  �����������:
 EateryDBAdapter(Context _context)
-----------
  ������:
-----------
 void open()
	��������� ���������� � ��.
-----------
 void close()
	���������� ���������.
-----------
 void addDish(String name, String description, boolean portioned, float price, String rating)
	��������� �����.
	��������, ��������, ������������, ����, �������.
-----------
 void deleteDish(int id)
	������� ����� �� ��� id.
-----------
 void deleteAllDish()
	������� ��� ��������� �����.
-----------
 void dsetDishRating(int id, String rating)
	������ ������� ����� �� ��� id.
-----------
 void addMenu(String date, int dishId, float availbaleAmmount, float orderedAmmount)
	��������� ������� ����.
	���� � ������� YYYY-MM-DD, id �����, ��������� ����� ��� ������, ���-�� �����������.
-----------
 void deleteMenu(String date, int dishId)
	������� ������� ���� �� id ����� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 void deleteAllMenu()
	�������� ���� ����.
-----------
 void deleteMenuAtDate(String date)
	�������� ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 ArrayList<String> getMenuDates()
	������ ��� �� ������� �������� ����.
	���������� ArrayList �� ����� ��� � ������� YYYY-MM-DD. ���� ������������� �� �����������.
-----------
 int getMenuDatesCount()
	���������� ���������� ��� �� ������� �������� ����.
-----------
 ArrayList<Dish> getMenuListAtDate(String date)
	���������� ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD. ���������� ������ Dish (�����) � ������:
		- id �����
		- ��������
		- ��������
		- ������������
		- ����
		- �������
		- ��������� ��� ������ �����
		- ���������� �����
-----------
 int getMenuCountAtDate(String date)
	���������� ���������� ���� ����������� � ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 void addOrder(String date, int dishId, float orderAmmount)
	���������� ����� � �����.
	���� � ������� YYYY-MM-DD, id ����� � ���������� �����������.
-----------
 void deleteOrder(String date, int dishId)
	�������� ����� �� ������.
	���� � ������� YYYY-MM-DD � id �����.
-----------
 void deleteOrderAtDate(String date)
	�������� ����� ����������� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 void deleteAllOrder()
	�������� ���� �������.
-----------
 int getOrderCount()
	���������� ���������� ��������� �������.
-----------
 int getOrderCountAtDate(String date)
	���������� ���������� ���������� ���� �� ����������� ����.
	���� � ������� YYYY-MM-DD.
-----------
 ArrayList<Dish> getOrderListAtDate(String date)
	���������� ���������� �� ������������ ���� �����.
	���� � ������� YYYY-MM-DD. ���������� ������ Dish (�����) � ������:
		- id �����
		- ��������
		- ��������
		- ������������
		- ����
		- �������
		- ��������� ��� ������ �����
		- ���������� �����
-----------
 void deleteAll()
	������� ��� ������ �� ������, ���� � �������.
-----------
 void saveSettings(String server, String login, String password, String mode)
	��������� ���������.
	����� �������, �����, ������ � ����� ������ (online/offline).
-----------
 ArrayList<String> getSettings()
	���������� ���������.
	ArrayList ����� � �����������: ����� �������, �����, ������ � ����� ������.
-----------