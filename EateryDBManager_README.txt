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
 public void addMenu(int date, List<Dish> dishes)
	��������� ����.
	���� � Unix time �������, List �� �������� Dish.
-----------
 public void deleteAllMenu()
	�������� ���� ����.
-----------
 public void deleteMenuAtDate(int date)
	�������� ���� �� ������������ ����.
	���� � Unix time �������.
-----------
 public List<Integer> getMenuDates()
	���������� ������ ��� �� ������� �������� ����.
	���������� List �� Intger Unix time ���. ���� ������������� �� �����������.
-----------
 public int getMenuDatesCount()
	���������� ���������� ��� �� ������� �������� ����.
-----------
 public List<Dish> getMenuListAtDate(int date)
	���������� ���� �� ������������ ����.
	���� � Unix time �������. ���������� List �������� Dish � ������:
		- id �����
		- ��������
		- ��������
		- ������������
		- ����
		- �������
		- ��������� ��� ������ �����
		- ���������� �����
-----------
 public int getMenuCountAtDate(int date)
	���������� ���������� ���� ����������� � ���� �� ������������ ����.
	���� � Unix time �������.
-----------
 public void addOrder(int date, List<Dish> dishes)
	���������� ������.
	���� � Unix time �������, List �� �������� Dish.
-----------
 public void deleteOrder(int date, int dishId)
	�������� ����� �� ������.
	���� � Unix time ������� � id �����.
-----------
 void deleteOrderAtDate(int date)
	�������� ����� ����������� �� ������������ ����.
	���� � Unix time �������.
-----------
 public void deleteAllOrder()
	�������� ���� �������.
-----------
 public int getOrderCount()
	���������� ���������� ��������� �������.
-----------
 public int getOrderCountAtDate(int date)
	���������� ���������� ���������� ���� �� ����������� ����.
	���� � Unix time �������.
-----------
 public List<Dish> getOrderListAtDate(int date)
	���������� ���������� �� ������������ ���� �����.
	���� � Unix time �������. ���������� List �������� Dish � ������:
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