package com.fei435;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.fei435.Constant.CommandArray;

public class WifiCarSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private EditTextPreference mPrefRouterUrl;
	private EditTextPreference mPrefCameraUrl;
	private EditTextPreference mPrefRouterUrlTest;
	private EditTextPreference mPrefCameraUrlTest;

	private EditTextPreference mPrefLenOn;
	private EditTextPreference mPrefLenOff;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the value will automatically be saved to SharePreferences,in onCreate join wifi_car_settings
		addPreferencesFromResource(R.xml.wifi_car_settings);


		mPrefRouterUrl = (EditTextPreference)findPreference(Constant.PREF_KEY_ROUTER_URL);
		mPrefCameraUrl = (EditTextPreference)findPreference(Constant.PREF_KEY_CAMERA_URL);
		mPrefRouterUrlTest = (EditTextPreference)findPreference(Constant.PREF_KEY_ROUTER_URL_TEST);
		mPrefCameraUrlTest = (EditTextPreference)findPreference(Constant.PREF_KEY_CAMERA_URL_TEST);

		mPrefLenOn = (EditTextPreference)findPreference(Constant.PREF_KEY_LEN_ON);
		mPrefLenOff = (EditTextPreference)findPreference(Constant.PREF_KEY_LEN_OFF);

		initValue();
	}

	void initValue(){

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		String CameraUrl = settings.getString(Constant.PREF_KEY_CAMERA_URL, Constant.DEFAULT_VALUE_CAMERA_URL);
		mPrefCameraUrl.setSummary(CameraUrl);

		String RouterUrl = settings.getString(Constant.PREF_KEY_ROUTER_URL, Constant.DEFAULT_VALUE_ROUTER_URL);
		mPrefRouterUrl.setSummary(RouterUrl);


		String testCameraUrl = settings.getString(Constant.PREF_KEY_CAMERA_URL_TEST, Constant.DEFAULT_VALUE_CAMERA_URL_TEST);
		mPrefCameraUrlTest.setSummary(testCameraUrl);

		String testRouterUrl = settings.getString(Constant.PREF_KEY_ROUTER_URL_TEST, Constant.DEFAULT_VALUE_ROUTER_URL_TEST);
		mPrefRouterUrlTest.setSummary(testRouterUrl);

		String lenon = settings.getString(Constant.PREF_KEY_LEN_ON, Constant.DEFAULT_VALUE_LEN_ON);
		mPrefLenOn.setSummary(lenon);

		String lenoff = settings.getString(Constant.PREF_KEY_LEN_OFF, Constant.DEFAULT_VALUE_LEN_OFF);
		mPrefLenOff.setSummary(lenoff);

	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );
	}


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
		Preference pref = findPreference(key);
		if (pref instanceof EditTextPreference) {
			EditTextPreference etp = (EditTextPreference) pref;
			if (etp == mPrefLenOn || etp == mPrefLenOff) {
				String comm = etp.getText();
				CommandArray cmd = new CommandArray(comm);
				if (cmd.isValid() ) {
				} else {
					Toast.makeText(this, "command format error, please re-enter", Toast.LENGTH_SHORT).show();
				}
			}
			etp.setSummary(etp.getText());
		}
	}
}