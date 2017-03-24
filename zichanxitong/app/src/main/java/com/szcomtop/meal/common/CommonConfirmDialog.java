package com.szcomtop.meal.common;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szcomtop.meal.R;


public class CommonConfirmDialog extends Dialog {

	private final TextView titleTv;
	private TextView contentTv;
	private Button btnCancle;
	private Button btnOk;

	public CommonConfirmDialog(Context context) {
		super(context, R.style.normal_dialog);
		this.setContentView(R.layout.common_confirm_dialog);
		contentTv = (TextView) findViewById(R.id.common_confirm_dialog_content);
		titleTv = (TextView) findViewById(R.id.common_confirm_dialog_title);
		btnCancle = (Button) findViewById(R.id.common_confirm_dialog_btn_cancle);
		btnOk = (Button) findViewById(R.id.common_confirm_dialog_btn_ok);
		
		btnCancle.setOnClickListener(new View.OnClickListener() {
			
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
	
	
	public CommonConfirmDialog(Context context,String content){
		this(context);
		setContent(content);
		
	}

	public CommonConfirmDialog(Context context,String tilte,String content ,String leftBtn ,String rightBtn){
		this(context);
		setContent(content);
		titleTv.setText(TextUtils.isEmpty(tilte) ? "" :tilte);
		btnCancle.setText(TextUtils.isEmpty(leftBtn) ? "" : leftBtn);
		btnOk.setText(TextUtils.isEmpty(rightBtn) ? "" : rightBtn);

	}

	

	public CommonConfirmDialog setContent(String content) {
		contentTv.setText(content);
		return this;
	}

	

	
	
	public CommonConfirmDialog setPositiveButtonListener(View.OnClickListener l){
		btnOk.setOnClickListener(l);
		return this;
	}
	
	
	
}
