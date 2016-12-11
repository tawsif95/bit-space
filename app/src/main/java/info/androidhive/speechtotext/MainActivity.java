package info.androidhive.speechtotext;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
	private String mActivityTitle;
	private ListView mDrawerList;
	private ArrayAdapter<String> mAdapter;
	private TextView txtSpeechInput;
	private ImageButton btnSpeak;
	private final int REQ_CODE_SPEECH_INPUT = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//mDrawerList = (ListView)findViewById(R.id.navList);
		//mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		//mActivityTitle = getTitle().toString();

		//addDrawerItems();
		txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

		// hide the action bar
		getActionBar().hide();
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setHomeButtonEnabled(true);

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				promptSpeechInput();
			}
		});

	}

	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQ_CODE_SPEECH_INPUT: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

					if (result.get(0).contains("Sad") ||
							result.get(0).contains("sad")) {
						txtSpeechInput.setText("Nothing is impossible, " +
								"the word itself says 'I'm possible'!");
					} else if (result.get(0).contains("happy") ||
							result.get(0).contains("Happy")) {
						txtSpeechInput.setText("May the force be with you!");
					} else if (result.get(0).contains("not confident")) {
						txtSpeechInput.setText("Just Do It!");
					}else{
						txtSpeechInput.setText(result.get(0));
					}

			}
			break;
		}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//private void addDrawerItems() {
	//	String[] osArray = { "EQ Score", "Statistics", "Recordings", "Quiz", "Settings" };
	//	mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
	//	mDrawerList.setAdapter(mAdapter);
	//}

	//private void setupDrawer() {
	//	mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	//			R.string.drawer_open, R.string.drawer_close) {
//
//			/** Called when a drawer has settled in a completely open state. */
//			public void onDrawerOpened(View drawerView) {
//			}
//
//			/** Called when a drawer has settled in a completely closed state. */
//			public void onDrawerClosed(View view) {
//			}
//		};
	}

	//public void onDrawerOpened(View drawerView) {
	//	super.onDrawerOpened(drawerView);
	//	getSupportActionBar().setTitle("Navigation!");
	//	invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	//}

