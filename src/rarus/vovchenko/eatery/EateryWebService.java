package rarus.vovchenko.eatery;

import java.io.StringWriter;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;

public class EateryWebService extends Service {
	private final String LOG_TAG = this.getClass().getName();
	
	// ����� web-�������
	private static String S_ADDRESS;
	
	// �����
	private static String S_LOGIN;
	
	// ������
	private static String S_PASSWORD;
	
	// ����� ������
	private static String MODE;
	
	// ��������� ��������� �� 
	private final EateryDBManager db  = new EateryDBManager(this);
	
	// �������� XML
	private static final String SOAP_PREFIX = "http://schemas.xmlsoap.org/soap/envelope/";
	private static final String MOB_PREFIX = "http://mobileEda";

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		// �������� �������� �� ��
		loadSettings();
		
		// ��������� ���� � �������
		getMenu();
		
		return super.onStartCommand(intent, flags, startId);	
	}
	
	void loadSettings() {
		db.open();
		List<String> settings = db.getSettings();
		db.close();
		
		S_ADDRESS = settings.get(0);
		S_LOGIN = settings.get(1);
		S_PASSWORD = settings.get(2);
		MODE = settings.get(3);
	}
	
	void getMenu() {
		// ��������� SOAP �������
		XmlSerializer sz = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			sz.setOutput(writer);
			sz.setPrefix("soapenv", SOAP_PREFIX);
			sz.setPrefix("mob", MOB_PREFIX);
			sz.startTag(SOAP_PREFIX, "Envelope");
			sz.startTag(SOAP_PREFIX, "Header");
			sz.endTag(SOAP_PREFIX, "Header");
			sz.startTag(SOAP_PREFIX, "Body");
			sz.startTag(MOB_PREFIX, "getMenu");
			sz.startTag(MOB_PREFIX, "getMenuData");
			sz.endTag(MOB_PREFIX, "getMenuData");
			sz.endTag(MOB_PREFIX, "getMenu");
			sz.endTag(SOAP_PREFIX, "Body");
			sz.endTag(SOAP_PREFIX, "Envelope");
	        sz.endDocument();
	        
	        
	        aTask q = new aTask();
	        
	        // ���������� �������
	        q.execute(writer.toString());
	        
	        // ��������� �������
	        Log.d(LOG_TAG, "Result: " + q.get());
		} catch (Exception e) {
	        throw new RuntimeException(e);
		}
	}
	
	// ����� ��� ������������ ��������� �������� 
	static class aTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... query) {
			HTTPPostRequest wsRequest = new HTTPPostRequest(S_ADDRESS, S_LOGIN, S_PASSWORD,
					query[0]);
			return wsRequest.getResult();
		}		
	}
}