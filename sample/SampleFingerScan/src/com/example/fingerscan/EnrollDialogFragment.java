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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.pantech.fingerscan.FingerScanUtils;

class EnrollDialogFragment extends DialogFragment{
	
	protected static EnrollDialogFragment newInstnace(){
		EnrollDialogFragment dialog = new EnrollDialogFragment();
		return dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		
		builder.setTitle(R.string.title_dialog_enroll);
		builder.setMessage(R.string.message_dialog_enroll);
		
		builder.setPositiveButton(getString(android.R.string.ok), new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				FingerScanUtils.callRegisterSettings(getActivity());
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(getString(android.R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		return builder.create();
	}
}
