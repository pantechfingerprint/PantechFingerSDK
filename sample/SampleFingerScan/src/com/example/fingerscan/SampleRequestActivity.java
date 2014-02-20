/*
 * Copyright (C) 2014 Pantech Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.fingerscan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pantech.fingerscan.FingerScanHelper;
import com.pantech.fingerscan.OnTimeoutListener;
import com.pantech.fingerscan.OnVerifyListener;

public class SampleRequestActivity extends Activity implements OnVerifyListener, OnCheckedChangeListener, OnTimeoutListener {

	private FingerScanHelper mFingerScanHelper;
	private int mCount = 0;
	private boolean mIsRequested = false;
	private TextView mResultTextView;
	private TextView mCountTextView;
	private ToggleButton mToggleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		getActionBar().setTitle(getString(R.string.title_request));
		getActionBar().setIcon(R.drawable.fp_sensor_icon);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.pt_ab_transparent_holo_dark));
		mCount = 0;
				
		mCountTextView = (TextView) findViewById(R.id.text_summary_request_count);
		mResultTextView = (TextView) findViewById(R.id.text_summary_request_result);
		
		mCountTextView.setText(Integer.toString(mCount));
		try {
			//create instance of fingerscan helper
			mFingerScanHelper = new FingerScanHelper(getApplicationContext());			
		} catch (IllegalAccessException e) {
			// this exception occur if this device does not support finger scan			
			e.printStackTrace();
			finish();
		} catch (SecurityException e) {
			// this exception occur if this device declare permission of finger scan			
			e.printStackTrace();
			finish();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(mIsRequested) stopRequest();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_toggle, menu);
		MenuItem item = menu.findItem(R.id.menu_item_toggle);		
		View view = item.getActionView();
		mToggleButton = (ToggleButton) view.findViewById(R.id.toggle_button);
		mToggleButton.setOnCheckedChangeListener(this);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mIsRequested = isChecked;
		if(isChecked){
			startRequest();
		}else{
			stopRequest();
		}
	}
	
	public void startRequest(){		
		try{
			mCount = 0;
			mCountTextView.setText(Integer.toString(mCount));
			mFingerScanHelper.requestVerify(this);
			mFingerScanHelper.setOnTimeoutListener(this);
			mIsRequested  = true;
		}catch(UnsupportedOperationException e){// this exception occur if user does not enroll finger print.
			e.printStackTrace();
			mToggleButton.setChecked(false);
			// show a dialog to enroll finger print 
			EnrollDialogFragment enrollDialogFragment = EnrollDialogFragment.newInstnace();
			enrollDialogFragment.show(getFragmentManager(), "tag");
		}
	}
	
	public void stopRequest(){
		mCount = 0;
		mIsRequested  = false;
		mToggleButton.setChecked(false);
		// Must call stop service if requested
		mFingerScanHelper.stopRequestVerify();
	}
	
	@Override
	public void onVerified(boolean isSuccess) {
		mCountTextView.setText(Integer.toString(++mCount));
		if(isSuccess){			
			mResultTextView.setText("OK");
			stopRequest();
		}else{
			mResultTextView.setText("FAIL");
		}
	}

	@Override
	public void onTimeouted() {
		if(mIsRequested) stopRequest();
	}

}
