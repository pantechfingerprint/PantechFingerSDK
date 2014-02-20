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
import android.widget.TextView;

import com.pantech.fingerscan.FingerScanUtils;

public class SampleInfoActivity extends Activity{

	private TextView mSupportTextView;
	private TextView mPermissionTextView;
	private TextView mEnrollTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);

		getActionBar().setTitle(getString(R.string.title_information));
		getActionBar().setIcon(R.drawable.main_sensor_info_icon);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.pt_ab_transparent_holo_dark));
		
		mSupportTextView = (TextView) findViewById(R.id.text_summary_info_support);
		mPermissionTextView = (TextView) findViewById(R.id.text_summary_permission_result);
		mEnrollTextView = (TextView) findViewById(R.id.text_summary_enroll_result);
		
		// initialize all states of this device...
		boolean isEnrolled = false;
		boolean supportFinger = false;
		boolean hasPermission = false;
		
		// 1. check this device supports fingerscan SDK
		supportFinger = FingerScanUtils.isDeviceSupportFingerScan(getApplicationContext());
		if(supportFinger){
			// 2. check this application has a permission for fingerscan 
			hasPermission = FingerScanUtils.hasFingerScanPermission(getApplicationContext());;
			// 3. check a user registers fingerprint
			isEnrolled = FingerScanUtils.isEnrolled(getApplicationContext());
		}
		
		mSupportTextView.setText(String.valueOf(supportFinger));
		mPermissionTextView.setText(String.valueOf(hasPermission));
		mEnrollTextView.setText(String.valueOf(isEnrolled));
	}

}
