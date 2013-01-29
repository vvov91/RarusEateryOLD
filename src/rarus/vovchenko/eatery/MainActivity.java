package rarus.vovchenko.eatery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	// адаптер для работы с БД
	EateryDBManager db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // экземпляр адаптора для работы с БД
        db = new EateryDBManager(this);
        db.open();
        
        /*class myTask extends AsyncTask {

			@Override
			protected Object doInBackground(Object... arg0) {
				EateryWebService eWS = new EateryWebService();
		        eWS.getMenu();
				return null;
			}
        	
        }
        
        myTask task = new myTask();
        task.execute();  */  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	// закрываем соединение с БД
    	db.close();
    }
}