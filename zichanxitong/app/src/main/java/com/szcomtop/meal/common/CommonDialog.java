package com.szcomtop.meal.common;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.szcomtop.meal.R;


public class CommonDialog extends Dialog {

	private TextView tv;

	public CommonDialog(Context context) {
		super(context, R.style.dialog);
		this.setContentView(R.layout.common_dialog);
		this.setCancelable(false);
		tv = (TextView) findViewById(R.id.dialog_tv);
		
		// TODO Auto-generated constructor stub
	}
	
	public CommonDialog setText(String text){
		tv.setText(text);
		return this;
	}

	
}
