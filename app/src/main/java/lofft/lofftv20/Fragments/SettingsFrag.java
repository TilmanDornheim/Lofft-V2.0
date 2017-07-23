package lofft.lofftv20.Fragments;

import android.os.Bundle;
import android.support.v7.preference.XpPreferenceFragment;

import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-20.
 */

public class SettingsFrag extends XpPreferenceFragment {




	@Override
	public void onCreatePreferences2(Bundle savedInstanceState, String rootKey) {

		addPreferencesFromResource(R.xml.preference);

		//TODO Use Shared Prefs for settings to have actual effect

	}


}
