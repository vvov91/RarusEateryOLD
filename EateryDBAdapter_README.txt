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
 void dishAdd(String _name, String _description, boolean _portioned, float _price, String _rating)
	��������� �����.
	��������, ��������, ������������, ����, �������.
-----------
 void dishDelete(int _id)
	������� ����� �� ��� id.
-----------
 void dishDeleteAll()
	������� ��� ��������� �����.
-----------
 void dishSetRating(int _id, String _rating)
	������ ������� ����� �� ��� id.
-----------
 void menuAdd(String _date, int _dish_id, float _availbale_ammount, float _ordered_ammount)
	��������� ������� ����.
	���� � ������� YYYY-MM-DD, id �����, ��������� ����� ��� ������, ���-�� �����������.
-----------
 void menuDelete(String _date, int _dish_id)
	������� ������� ���� �� id ����� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 void menuDeleteAll()
	�������� ���� ����.
-----------
 void menuDeleteAtDate(String _date)
	�������� ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 ArrayList<String> menuGetDates()
	������ ��� �� ������� �������� ����.
	���������� ArrayList �� ����� ��� � ������� YYYY-MM-DD. ���� ������������� �� �����������.
-----------
 int menuGetDatesCount()
	���������� ���������� ��� �� ������� �������� ����.
-----------
 ArrayList<Dish> menuGetListAtDate(String _date)
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
 int menuGetCountAtDate(String _date)
	���������� ���������� ���� ����������� � ���� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 void orderAdd(String _date, int _dish_id, float _order_ammount)
	���������� ����� � �����.
	���� � ������� YYYY-MM-DD, id ����� � ���������� �����������.
-----------
 void orderDelete(String _date, int _dish_id)
	�������� ����� �� ������.
	���� � ������� YYYY-MM-DD � id �����.
-----------
 void orderDeleteAtDate(String _date)
	�������� ����� ����������� �� ������������ ����.
	���� � ������� YYYY-MM-DD.
-----------
 void orderDeleteAll()
	�������� ���� �������.
-----------
 int orderGetCount()
	���������� ���������� ��������� �������.
-----------
 int orderGetCountAtDate(String _date)
	���������� ���������� ���������� ���� �� ����������� ����.
	���� � ������� YYYY-MM-DD.
-----------
 ArrayList<Dish> ordersGetListAtDate(String _date)
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
 void settingsSave(String _server, String _login, String _password, String _mode)
	��������� ���������.
	����� �������, �����, ������ � ����� ������ (online/offline).
-----------
 ArrayList<String> settingsGet()
	���������� ���������.
	ArrayList ����� � �����������: ����� �������, �����, ������ � ����� ������.
-----------