package rarus.vovchenko.eatery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	// адаптер для работы с БД
	EateryDBAdapter eateryDBAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // экземпляр адаптора для работы с БД
        eateryDBAdapter = new EateryDBAdapter(this);
        eateryDBAdapter.open();
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
    	eateryDBAdapter.close();
    }
}
