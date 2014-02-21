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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pantech.fingerscan.FingerScanUtils;

public class SampleActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setTitle(getString(R.string.title_main));
		getActionBar().setIcon(R.drawable.ic_launcher);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.pt_ab_transparent_holo_dark));		
	}
	
	public void buttonVegaFingerClicked(View view){
		if(checkSupport()){
			if(FingerScanUtils.isEnrolled(getApplicationContext())){
				// you can use activity instead of fragment if you want
				//startActivity(new Intent(getApplicationContext(), SampleDeviceDefaultActivity.class));
				SampleDeviceDefaultFragment fragment = new SampleDeviceDefaultFragment();
				FragmentManager manager = getFragmentManager();
				FragmentTransaction transaction = manager.beginTransaction();
				transaction.add(android.R.id.content, fragment);
				transaction.commit();
			}else{
				EnrollDialogFragment enrollDialogFragment = EnrollDialogFragment.newInstnace();
				enrollDialogFragment.show(getFragmentManager(), "tag");
			}
		}
	}
	
	public void buttonRequestClicked(View view){
		if(checkSupport()){
			startActivity(new Intent(getApplicationContext(), SampleRequestActivity.class));
		}
	}
	public void buttonInfoClicked(View view){
		startActivity(new Intent(getApplicationContext(), SampleInfoActivity.class));		
	}
	public boolean checkSupport(){
		if(!FingerScanUtils.isDeviceSupportFingerScan(getApplicationContext())){
			NoSensorDialogFragment dialog = NoSensorDialogFragment.getNewInstance();
			dialog.show(getFragmentManager(), "NoSensorDialogFragment");
			return false;
		}
		return true;
	}
	

}
