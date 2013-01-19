package rarus.vovchenko.eatery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	// ������� ��� ������ � ��
	EateryDBAdapter db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // ��������� �������� ��� ������ � ��
        db = new EateryDBAdapter(this);
        db.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	// ��������� ���������� � ��
    	db.close();
    }
}
