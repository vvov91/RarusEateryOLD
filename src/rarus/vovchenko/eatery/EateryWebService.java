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
	
	// адрес web-сервиса
	private static final String S_ADDRESS = "http://178.219.241.102:8090/DiningRoomTest_kuev/ws/mobileEda";
	
	// логин
	private static final String S_LOGIN = "mobile";
	
	// пароль
	private static final String S_PASSWORD = "mobile";
	
	// режим работы
	private static final String MODE = "offline";
	
	// экземпляр менеджера БД 
	private final EateryDBManager db  = new EateryDBManager(this);
	
	// префиксы XML
	private static final String SOAP_PREFIX = "http://schemas.xmlsoap.org/soap/envelope/";
	private static final String MOB_PREFIX = "http://mobileEda";

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public int onStartCommand(Intent intent, int flags, int startId) {		
		// получение меню с сервера
		getMenu();
		
		return super.onStartCommand(intent, flags, startId);	
	}
	
	void getMenu() {
		// генерация SOAP запроса
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
	        
	        // выполнение запроса
	        q.execute(writer.toString());
	        
	        // результат запроса
	        Log.d(LOG_TAG, "Result: " + q.get());
		} catch (Exception e) {
	        throw new RuntimeException(e);
		}
	}
	
	// класс для ассинхронной обработки запросов 
	static class aTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... query) {
			HTTPPostRequest wsRequest = new HTTPPostRequest(S_ADDRESS, S_LOGIN, S_PASSWORD,
					query[0]);
			return wsRequest.getResult();
		}		
	}
}