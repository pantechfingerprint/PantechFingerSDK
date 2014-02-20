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

import android.os.Bundle;

import com.pantech.fingerscan.FingerScanActivity;
import com.pantech.fingerscan.OnVerifyListener;

public class SampleDeviceDefaultActivity extends FingerScanActivity implements OnVerifyListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestVerifyActivity(this);
		} catch (IllegalAccessException e) {
			// this exception occur if this device does not support finger scan
			e.printStackTrace();
		} catch (SecurityException e) {
			// this exception occur if this device declare permission of finger scan
			e.printStackTrace();
		}
	}

	@Override
	public void onVerified(boolean isSuccess) {
		if(isSuccess){
			SuccessDiaiogFragment diaiogFragment = new SuccessDiaiogFragment();
			diaiogFragment.show(getFragmentManager(), "tag");
			finish();
		}
	}
}
