
package com.carzis.prefs;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.carzis.R;

public class Prefs extends PreferenceActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        addPreferencesFromResource(R.xml.preference);
    }	 
}