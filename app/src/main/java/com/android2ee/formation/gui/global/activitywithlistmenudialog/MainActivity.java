package com.android2ee.formation.gui.global.activitywithlistmenudialog;

import java.util.ArrayList;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The ArrayAdapter to use
	 */
	private ArrayAdapter<String> arrayAdapter;
	/**
	 * The EditText
	 */
	private EditText editText;
	/**
	 * The TextView
	 */
	private TextView txvTitle;
	/**
	 * The ListView
	 */
	private ListView listView;
	/**
	 * The AddButton
	 */
	private Button button;
	/**
	 * The shape of the title
	 */
	private GradientDrawable shapeTitle;
	/**
	 * The shape of the edittext
	 */
	private GradientDrawable shapeEditText;

	/**
	 * The shape of the description text
	 */
	private GradientDrawable shapeList;
	/**
	 * The shape of the description text
	 */
	private StateListDrawable shapeButton;
	/**
	 * The key of the items list
	 */
	private static final String LIST_ITEM = "items";
	/**
	 * The dialog ID
	 */
	private static final int DIALOG_ID = 1;
	/**
	 * L'animator set for PostHC
	 */
	AnimatorSet set;
	/**
	 * The animation for preHC
	 */
	Animation anim;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Building mainLayout
		setContentView(R.layout.activity_main);
		// Retrieve the ListView, EditText and Button
		listView = (ListView) findViewById(R.id.myListView);
		editText = (EditText) findViewById(R.id.editTask);
		button = (Button) findViewById(R.id.addButton);
		txvTitle = (TextView) findViewById(R.id.txvHellox);
		if (getResources().getBoolean(R.bool.postHC)) {
			set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.edit_text_anim);

		} else {
			anim = AnimationUtils.loadAnimation(this, R.animator.edit_text_anim);
		}
		// Création de la liste des to do items
		final ArrayList<String> items = new ArrayList<String>();
		// Création de l'array adapter pour lier l'array à la listview
		arrayAdapter = new SimpleArrayAdapter(this, items);
		// Liaison de l'array adapter à la listview.
		listView.setAdapter(arrayAdapter);
		addListeners();
		instanciateShapes();

	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			showTheDialog();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * Add the listeners to the ListView and the Button
	 */
	private void addListeners() {
		// Add a listener on the listView
		listView.setClickable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				itemSelected(position);
			}
		});
		// Add the button listener
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateList();
			}
		});
	}

	/**
	 * Instantiate the shapes objects and set them to the graphics components
	 */
	private void instanciateShapes() {
		shapeEditText = (GradientDrawable) getResources().getDrawable(R.drawable.edittext);
		shapeList = (GradientDrawable) getResources().getDrawable(R.drawable.listview);
		shapeButton = (StateListDrawable) getResources().getDrawable(R.drawable.button);
		txvTitle.setBackgroundDrawable(shapeTitle);
		listView.setBackgroundDrawable(shapeList);
		button.setBackgroundDrawable(shapeButton);
		editText.setBackgroundDrawable(shapeEditText);
	}

	/******************************************************************************************/
	/** Managing Option Menu **************************************************************************/
	/******************************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	/******************************************************************************************/
	/** AlertDialog Methods **************************************************************************/
	/******************************************************************************************/

	/**
	 * Show an alertDialog
	 */
	private void showTheDialog() {
		showDialog(DIALOG_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_ID:
			dialog = buildAlertdDialog();
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	/**
	 * @return a simple AlertDialog
	 */
	private Dialog buildAlertdDialog() {
		// Creation of the AlertDialog Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// No cancel button
		builder.setMessage(getString(R.string.dialog_message)).setCancelable(false);
		// Define the OK button, it's message and its listener
		builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				showCustomToast(true);
			}
		});
		// Define the KO button, it's message and its listener
		builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				showCustomToast(false);
			}
		});
		builder.setTitle(getString(R.string.dialog_Title));
		// Then create the Dialog
		return builder.create();
	}

	/**
	 * Display a Custom Toast to show the dialog close result
	 * 
	 * @param isOk
	 */
	private void showCustomToast(boolean isOk) {
		// Call the Layout Inflater to build the View object from an Xml description
		LayoutInflater inflater = getLayoutInflater();
		// Build the view using the file R.layout.toast_layout using the R.id.toast_layout_root
		// element as the root view
		View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		// set the text
		((TextView) layout.findViewById(R.id.text)).setText(String.format(getString(R.string.ToastMessage), isOk));
		// This work too and duplicate the view in the Toast
		// View layout = inflater.inflate(R.layout.main,null);

		// then create the Toast
		Toast toast = new Toast(this);
		// Define the gravity can be all the gravity constant and can be associated using |
		// (exemple: Gravity.TOP|Gravity.LEFT)
		// the xOffset and yOffSet moves the Toast (in pixel)
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		// define the time duration of the Toast
		toast.setDuration(Toast.LENGTH_LONG);
		// Set the layout of the toast
		toast.setView(layout);
		// And display it
		toast.show();
	}

	/******************************************************************************************/
	/** Listeners' Methods **************************************************************************/
	/******************************************************************************************/

	/**
	 * Update the EditText according to the selected item
	 * 
	 * @param position
	 */
	private void itemSelected(int position) {
		String item = (String) arrayAdapter.getItem(position);
		editText.setText(item);
	}

	/**
	 * Update the list using the EditText value
	 */
	@SuppressLint("NewApi")
	private void updateList() {
		// Make the animation
		if (getResources().getBoolean(R.bool.postHC)) {
			set.setTarget(editText);
			set.start();
		} else {

			editText.startAnimation(anim);
		}
		// Add the value of the EditText in the list
		arrayAdapter.add(editText.getText().toString());
		// Delete the content of the Edit text
		editText.setText("");
	}

	/******************************************************************************************/
	/** Life cycle management **************************************************************************/
	/******************************************************************************************/
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		ArrayList<String> items = savedInstanceState.getStringArrayList(LIST_ITEM);
		arrayAdapter.clear();
		for (String item : items) {
			arrayAdapter.add(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < arrayAdapter.getCount(); i++) {
			items.add(arrayAdapter.getItem(i));
		}
		outState.putStringArrayList(LIST_ITEM, items);
		super.onSaveInstanceState(outState);
	}
}
