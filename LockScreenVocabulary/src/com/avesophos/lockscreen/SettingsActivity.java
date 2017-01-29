
package com.avesophos.lockscreen;

import android.app.Activity;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.widget.SeekBar.*;
import java.util.*;
import android.preference.*;

public class SettingsActivity extends Activity
{
	Spinner settings_language;
	SeekBar settings_difficulty;
	CheckBox settings_security;
	CheckBox settings_tts;
	EditText settings_password;
	int language = Language.LANG_FR;
	
	private final boolean PRO = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		int[] scores = Language.getDefaultScores();
		boolean tts = true;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		for (int i = 0; i < Language.TOTAL_LANGUAGES; i++)
			scores[i] = prefs.getInt("score"+i, 50);
		language = prefs.getInt("language", Language.LANG_FR);
		tts = prefs.getBoolean("tts", true);
		
        // Populate language choice
		settings_language = (Spinner) findViewById(R.id.settings_language);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Language.getDisplayLanguages());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		settings_language.setAdapter(adapter);
		settings_language.setSelection(language);
		final Context context = this;
		settings_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { 
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
				int[] scores = Language.getDefaultScores();
				for (int i = 0; i < Language.TOTAL_LANGUAGES; i++)
					scores[i] = prefs.getInt("score"+i, 50);
				settings_difficulty = (SeekBar) findViewById(R.id.settings_difficulty);
				settings_difficulty.setMax(Language.getWordCount(position)-8);
				settings_difficulty.setProgress(scores[position]-8);
			}

			@Override public void onNothingSelected(AdapterView<?> parent) {

			} 
		});
		
		// Populate tts
		settings_tts = (CheckBox) findViewById(R.id.settings_tts);
		settings_tts.setChecked(tts);

		// Populate difficulty
		settings_difficulty = (SeekBar) findViewById(R.id.settings_difficulty);
		settings_difficulty.setMax(Language.getWordCount(language));
		settings_difficulty.setProgress(scores[language]-8);

		// Disable security
		settings_security = (CheckBox) findViewById(R.id.settings_security);
		settings_password = (EditText) findViewById(R.id.settings_password);
		if (!PRO) {
			TextView lbl_difficulty = (TextView) findViewById(R.id.lbl_difficulty);
			settings_difficulty.setEnabled(false);
			lbl_difficulty.setEnabled(false);	
		}
		
		TextView lbl_password = (TextView) findViewById(R.id.lbl_password);
		TextView lbl_security = (TextView) findViewById(R.id.lbl_security);
		lbl_security.setEnabled(false);	
		lbl_password.setEnabled(false);	
		settings_security.setEnabled(false);
		settings_password.setEnabled(false);
		
	}
	
	public void save(View v) {  
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit(); 
		editor.putInt("score"+settings_language.getSelectedItemPosition(), settings_difficulty.getProgress() + 8);
		editor.putInt("language", settings_language.getSelectedItemPosition());
		editor.putBoolean("tts", settings_tts.isChecked());
		editor.putBoolean("security", settings_security.isChecked());
		editor.putString("password", settings_password.getText().toString());
		editor.commit();
		finish();
	}
}
