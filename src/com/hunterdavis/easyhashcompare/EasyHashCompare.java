package com.hunterdavis.easyhashcompare;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class EasyHashCompare extends Activity {

	int SELECT_FILE = 122;
	int SELECT_FILE_2 = 123;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create an anonymous implementation of OnClickListener
		OnClickListener loadButtonListner = new OnClickListener() {
			public void onClick(View v) {
				// do something when the button is clicked

				// in onCreate or any event where your want the user to 
				Intent intent = new Intent(v.getContext(), FileDialog.class);
				intent.putExtra(FileDialog.START_PATH, "/sdcard");
				startActivityForResult(intent, SELECT_FILE);

			}
		};
		
		// Create an anonymous implementation of OnClickListener
		OnClickListener loadButtonListner2 = new OnClickListener() {
			public void onClick(View v) {
				// do something when the button is clicked

				// in onCreate or any event where your want the user to 
				Intent intent = new Intent(v.getContext(), FileDialog.class);
				intent.putExtra(FileDialog.START_PATH, "/sdcard");
				startActivityForResult(intent, SELECT_FILE_2);

			}
		};		

		Button loadButton = (Button) findViewById(R.id.loadButton);
		loadButton.setOnClickListener(loadButtonListner);
		
		Button loadButton2 = (Button) findViewById(R.id.loadButton2);
		loadButton2.setOnClickListener(loadButtonListner2);		
		
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

	}

	public void onActivityResult(final int requestCode,
			int resultCode, final Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_FILE) {
				String filePath = data.getStringExtra(FileDialog.RESULT_PATH);
				// set the filename txt
				changeFileNameText(filePath);
				// md5 the file
				String md5String = md5(filePath);
				changeMD5Text(md5String);
			}
			else if (requestCode == SELECT_FILE_2) {
				String filePath = data.getStringExtra(FileDialog.RESULT_PATH);
				// set the filename txt
				changeFileNameText2(filePath);
				// md5 the file
				String md5String = md5(filePath);
				changeMD5Text2(md5String);
			}			
		} else if (resultCode == RESULT_CANCELED) {
		}
	}

	public void changeFileNameText(String newFileName) {
		TextView t =  (TextView)findViewById(R.id.fileText);
		t.setText(newFileName);
	}
	
	public void changeMD5Text(String newMD5) {
		EditText t =  (EditText)findViewById(R.id.mdfive);
		t.setText(newMD5);
		testMatchAndSetText();
	}
	
	public void changeFileNameText2(String newFileName) {
		TextView t =  (TextView)findViewById(R.id.fileText2);
		t.setText(newFileName);
	}
	
	public void changeMD5Text2(String newMD5) {
		EditText t =  (EditText)findViewById(R.id.mdfive2);
		t.setText(newMD5);
		testMatchAndSetText();
	}	

	public void testMatchAndSetText() {
		EditText t =  (EditText)findViewById(R.id.mdfive);
		EditText t2 = (EditText)findViewById(R.id.mdfive2);
		TextView tv = (TextView)findViewById(R.id.resultsText);
		String s1 = t.getText().toString();
		String s2 = t2.getText().toString();
		if(s1.equals(s2))
		{
			tv.setText("MD5 values match!  Files are equal!");
		}
		else
		{
			tv.setText("MD5 values do not match!  Files are not equal!");
		}
		
	}	
	
	public static  String md5(String fileString) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			InputStream is  = null;
			try {
				is = new FileInputStream(fileString);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] buffer = new byte[8*1024];
			int read;
			try {
				while((read = is.read(buffer)) > 0){
					digest.update(buffer, 0, read);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}	
	
	
}