package rarus.vovchenko.eatery;

import rarus.vovchenko.eatery.InterceptLinearLayout.OnInterceptListener;
import android.os.Bundle;
import android.os.Handler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ContainerActivity {
	private static final String TAG_LIST = "tag_list";
	
	private FlyoutActionModeCallback flyoutMenuCallback;
	private ActionMode actionMode = null;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setupFlyout();
    }
    
    @Override
	protected int getContentResource() {
		return R.layout.activity_main;
	}
    
    @Override
	protected Fragment createFragment(Bundle savedInstanceState) {
    	return null;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				showFlyoutMenu();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
    
    public void showFlyoutMenu() {
		if (actionMode == null) {
			actionMode = startActionMode(flyoutMenuCallback);
		}
	}

	public void hideFlyoutMenu() {
		if (actionMode != null) {
			actionMode.finish();
			actionMode = null;
		}
	}
    
	public void setSelectedPage(int id) {
		View flyoutView = findViewById(R.id.flyout_menu);
		TextView menuView = (TextView) flyoutView.findViewById(R.id.page_menu);
		//TextView tagsView = (TextView) flyoutView.findViewById(R.id.page_tags);
		
		menuView.setCompoundDrawables(null, null, null, null);
		//tagsView.setCompoundDrawables(null, null, null, null);
		
		if (id == R.id.page_menu) {
			menuView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flyout_item_indicator, 0);
		}/* else if (id == R.id.page_tags) {
			tagsView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flyout_item_indicator, 0);
		}*/
	}
	
	private void setupFlyout() {
		flyoutMenuCallback = new FlyoutActionModeCallback();
		
		View flyoutView = findViewById(R.id.flyout_menu);
		TextView menuView = (TextView) flyoutView.findViewById(R.id.page_menu);
		//TextView tagsView = (TextView) flyoutView.findViewById(R.id.page_tags);
		
		menuView.setOnClickListener(flyoutMenuCallback);
		//tagsView.setOnClickListener(flyoutMenuCallback);

		final InterceptLinearLayout interceptView = (InterceptLinearLayout) findViewById(R.id.container);
		interceptView.setOnInterceptListener(new OnInterceptListener() {
			public boolean isFlyoutOpen() {
				return actionMode != null;
			}
			
			public void shouldOpen() {
				showFlyoutMenu();
			}
			
			public void shouldClose() {
				hideFlyoutMenu();
			}
		});
	}
	
	private class FlyoutActionModeCallback implements ActionMode.Callback, View.OnClickListener {
		private static final float ALPHA = 0.6f;
		
		private Handler handler;
		private Paint contentPaint;
		
		public FlyoutActionModeCallback() {
			handler = new Handler();
			contentPaint = new Paint();

			// This will desaturate colors
			float[] transform = {
				1, 0, 0, 0, 0, 
				0, 1, 0, 0, 0,
				0, 0, 1, 0, 0, 
				0, 0, 0, 1, 0
			};
			
			ColorMatrixColorFilter filter = new ColorMatrixColorFilter(transform);
			contentPaint.setColorFilter(filter);
		}
		
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle(R.string.menu_title);

			final View flyoutView = findViewById(R.id.flyout_menu);
			final View containerView = findViewById(R.id.container);
			
			int width = flyoutView.getWidth();
			flyoutView.animate().translationX(0f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					flyoutView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
					flyoutView.setVisibility(View.VISIBLE);
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					flyoutView.setLayerType(View.LAYER_TYPE_NONE, null);
				}
			});
			
			containerView.animate().translationX(width).alpha(ALPHA).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					containerView.setLayerType(View.LAYER_TYPE_HARDWARE, contentPaint);
				}
			});
			
			return true;
		}
		
		public void onDestroyActionMode(ActionMode mode) {
			final View flyoutView = findViewById(R.id.flyout_menu);
			final View containerView = findViewById(R.id.container);

			int width = flyoutView.getWidth();
			flyoutView.animate().translationX(-width).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					flyoutView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					flyoutView.setLayerType(View.LAYER_TYPE_NONE, null);
					flyoutView.setVisibility(View.INVISIBLE);
				}
			});
			containerView.animate().translationX(0f).alpha(1f).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					containerView.setLayerType(View.LAYER_TYPE_NONE, null);
				}
			});
			
			actionMode = null;
		}
		
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return false;
		}

		/**
		 * Called when the user clicks a flyout menu option
		 */
		public void onClick(View v) {
			int id = v.getId();
			handler.postDelayed(new PageSwitcher(id), 300);
			
			if (actionMode != null) {
				actionMode.finish();
			}
		}
		
		private class PageSwitcher implements Runnable {
			private int id;
			
			public PageSwitcher(int id) {
				this.id = id;
			}

			public void run() {
				switch (id) {
					case R.id.page_menu:
						getFragmentManager().popBackStack(TAG_LIST, FragmentManager.POP_BACK_STACK_INCLUSIVE);
						break;
						/*case R.id.page_tags:
						FragmentManager fm = getFragmentManager();
						if (!fm.popBackStackImmediate(TAG_LIST, 0)
								&& fm.findFragmentByTag(TAG_LIST) == null) {
							// Only add tag list if we can't find it
							fm.beginTransaction()
									.replace(R.id.content, new TagListFragment(), TAG_LIST)
									.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
									.addToBackStack(TAG_LIST)
									.commit();
						}
						break;*/
				}
			}
		}
	}
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }	
}