package com.szcomtop.meal.common;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szcomtop.meal.R;


public class CommonNotifyDialog extends Dialog {

	private final TextView titleTv;
	private TextView contentTv;
	private Button btnCancle;
	private Button btnOk;

	public CommonNotifyDialog(Context context) {
		super(context, R
				.style.normal_dialog);
		this.setContentView(R.layout.common_notify_dialog);
		contentTv = (TextView) findViewById(R.id.common_notify_dialog_content);
		titleTv = (TextView) findViewById(R.id.common_notify_dialog_title);
		btnOk = (Button) findViewById(R.id.common_notify_dialog_btn_ok);

		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isShowing()){
					dismiss();
				}
			}
		});

		// TODO Auto-generated constructor stub
	}


	public CommonNotifyDialog(Context context, String content){
		this(context);
		setContent(content);

	}

	public CommonNotifyDialog(Context context, String tilte, String content , String btn ){
		this(context);
		setContent(content);
		titleTv.setText(TextUtils.isEmpty(tilte) ? "" :tilte);
		btnOk.setText(TextUtils.isEmpty(btn) ? "" : btn);

	}

	

	public CommonNotifyDialog setContent(String content) {
		contentTv.setText(content);
		return this;
	}

}
