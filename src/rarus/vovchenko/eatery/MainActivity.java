package rarus.vovchenko.eatery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	// ������� ��� ������ � ��
	EateryDBAdapter eateryDBAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // ��������� �������� ��� ������ � ��
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
    	
    	// ��������� ���������� � ��
    	eateryDBAdapter.close();
    }
}
