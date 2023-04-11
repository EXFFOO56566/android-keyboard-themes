package gt.keybord;

import gt.module.constants.AppConstants;
import gt.module.constants.ApplicationPrefs;

import com.example.android.softkeyboard.adapter.ThemesBackgroundAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ThemesBackgroundActivity extends Activity implements OnItemClickListener{
	
	ApplicationPrefs applicationPrefs;
	private GridView listView_themes_list;
	private ThemesBackgroundAdapter listAdapter;
	InterstitialAd mInterstitialAd;
	private StartAppAd startAppAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 StartAppSDK.init(ThemesBackgroundActivity.this, AppConstants.DeveloperID, AppConstants.ApplicationID, true);
			
		setContentView(R.layout.activity_themes_list);
		applicationPrefs = ApplicationPrefs.getInstance(this);
		 startAppAd = new StartAppAd(getApplicationContext());
		 mInterstitialAd = new InterstitialAd(this);
	        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
	        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
	        mInterstitialAd.loadAd(adRequest1);
	        mInterstitialAd.setAdListener(new AdListener() {
				public void onAdLoaded() {
					// Call displayInterstitial() function
					showInterstitial();
				}
			});
		
		initUI();
	}
	 private void showInterstitial() {
	        // Show the ad if it's ready. Otherwise toast and restart the game.
	            mInterstitialAd.show();
	       
	    }
	private void initUI() {
		// TODO Auto-generated method stub
		
		listAdapter = new ThemesBackgroundAdapter(getApplicationContext());
		listView_themes_list = (GridView) findViewById(R.id.listView_themes_list);	
		listView_themes_list.setAdapter(listAdapter);
		listView_themes_list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub 
		
		applicationPrefs.setCustomeThemesBackground(AppConstants.LAYOUT_LIST[position]);
		 startAppAd.showAd(); // show the ad
		 startAppAd.loadAd(); // load the next ad
		 Intent intent=new Intent();  
         intent.putExtra("MESSAGE","done");  
         setResult(1,intent);  
         finish();//finishing activity  
	}

}
