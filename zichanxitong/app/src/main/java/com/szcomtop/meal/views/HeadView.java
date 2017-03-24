package com.szcomtop.meal.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szcomtop.meal.R;


/**
 * 公共头部Titel布局
 * 
 * @author yangzhao
 * 
 */
public class HeadView extends LinearLayout implements OnClickListener {
	
	/**
	 * 只显示中间TextView
	 */
	public static final int TYPE_CENTERONLY = 0;
	/**
	 * 只显示左边按钮
	 */
	public static final int TYPE_LEFTONLY = 1;
	/**
	 * 只显示右边Button
	 */
	public static final int TYPE_RIGHTBTN_ONLY = 2;
	/**
	 * 只右边ImageButton
	 */
	public static final int TYPE_RIGHTIBTN_ONLY = 3;
	/**
	 * 显示左边Button和右边Button
	 */
	public static final int TYPE_LRIGHTBTN = 4;
	/**
	 * 显示左边Button和右边ImageButton
	 */
	public static final int TYPE_LRIGHTIBTN = 5;
	
	private OnHeadClickListener onClickListener = null;
	private OnHeadDoubleClickListener onDoubleClickListener = null;
	private ImageButton btnBack;
	private TextView tvHeadCenter;
	private Button btnHeadRight;
	private ImageButton ibtnHeadRight;
	private View includeHead;
	private ImageView btnHeadAddedRight;
	
	private View lytConnecting;
	private ImageView ivConnectLoading;
	private TextView tvUnavaliable;
	
	private Button tvGroupLeft;
	private Button tvGroupRight;
	
	public HeadView(Context context) {
		super(context);
		initView(context);
	}
	
	public HeadView(Context context, int TYPE,
					OnHeadInitListener onHeadInitListener) {
		super(context);
		initView(context);
	}
	
	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 设置控件初始化背景，文字资源
	 * @param TYPE
	 * @param onHeadInitListener
	 */
	public void setHeadParams(int TYPE, OnHeadInitListener onHeadInitListener) {
		switch (TYPE) {
		case TYPE_CENTERONLY:
			btnBack.setVisibility(View.GONE);
			btnHeadRight.setVisibility(View.GONE);
			ibtnHeadRight.setVisibility(View.GONE);
			tvHeadCenter.setVisibility(View.VISIBLE);
			if (onHeadInitListener != null) {
				onHeadInitListener.initCenterTv(tvHeadCenter);
			}
			break;
		case TYPE_LEFTONLY:
			btnBack.setVisibility(View.VISIBLE);
			btnHeadRight.setVisibility(View.GONE);
			ibtnHeadRight.setVisibility(View.GONE);
			tvHeadCenter.setVisibility(View.VISIBLE);
			if (onHeadInitListener != null) {
				onHeadInitListener.initCenterTv(tvHeadCenter);
				onHeadInitListener.initLeftBtn(btnBack);
			}
			break;
		case TYPE_RIGHTBTN_ONLY:
			btnBack.setVisibility(View.GONE);
			btnHeadRight.setVisibility(View.VISIBLE);
			ibtnHeadRight.setVisibility(View.GONE);
			tvHeadCenter.setVisibility(View.VISIBLE);
			if (onHeadInitListener != null) {
				onHeadInitListener.initCenterTv(tvHeadCenter);
				onHeadInitListener.initRightBtn(btnHeadRight);
			}
			break;
		case TYPE_RIGHTIBTN_ONLY:
			btnBack.setVisibility(View.GONE);
			btnHeadRight.setVisibility(View.GONE);
			ibtnHeadRight.setVisibility(View.VISIBLE);
			tvHeadCenter.setVisibility(View.VISIBLE);
			if (onHeadInitListener != null) {
				onHeadInitListener.initCenterTv(tvHeadCenter);
				onHeadInitListener.initRightIBtn(ibtnHeadRight);
			}
			break;
		case TYPE_LRIGHTBTN:
			btnBack.setVisibility(View.VISIBLE);
			btnHeadRight.setVisibility(View.VISIBLE);
			ibtnHeadRight.setVisibility(View.GONE);
			tvHeadCenter.setVisibility(View.VISIBLE);
			if (onHeadInitListener != null) {
				onHeadInitListener.initLeftBtn(btnBack);
				onHeadInitListener.initCenterTv(tvHeadCenter);
				onHeadInitListener.initRightBtn(btnHeadRight);
			}
			break;
		case TYPE_LRIGHTIBTN:
			btnBack.setVisibility(View.VISIBLE);
			btnHeadRight.setVisibility(View.GONE);
			ibtnHeadRight.setVisibility(View.VISIBLE);
			tvHeadCenter.setVisibility(View.VISIBLE);
			if (onHeadInitListener != null) {
				onHeadInitListener.initLeftBtn(btnBack);
				onHeadInitListener.initCenterTv(tvHeadCenter);
				onHeadInitListener.initRightIBtn(ibtnHeadRight);
			}
			break;
		default:
			break;
		}
	}
	
	public void showLoading(Context context) {
		lytConnecting.setVisibility(View.VISIBLE);
		tvHeadCenter.setVisibility(View.GONE);
		RotateAnimation anim=new RotateAnimation(0.0f, +350.0f,    
	               Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setRepeatCount(-1);
		anim.setRepeatMode(Animation.RESTART);    
		anim.setDuration(500);
		ivConnectLoading.setImageResource(R.drawable.loading_progress);
		ivConnectLoading.setVisibility(View.VISIBLE);
		ivConnectLoading.setAnimation(anim);
		tvUnavaliable.setText("连接中...");
		anim.start();
	}
	
	public void hideLoading() {
		if(ivConnectLoading != null && ivConnectLoading.getAnimation()!=null)
			ivConnectLoading.getAnimation().cancel();
		ivConnectLoading.setVisibility(View.GONE);
		ivConnectLoading.setImageBitmap(null);
		lytConnecting.setVisibility(View.GONE);
		tvHeadCenter.setVisibility(View.VISIBLE);
	}
	
	public void showUnavailable() {
		if(ivConnectLoading != null && ivConnectLoading.getAnimation() != null)
			ivConnectLoading.getAnimation().cancel();
		tvUnavaliable.setText("未连接");
		ivConnectLoading.setVisibility(View.GONE);
		ivConnectLoading.setImageBitmap(null);
		lytConnecting.setVisibility(View.VISIBLE);
		tvHeadCenter.setVisibility(View.GONE);
	}

	private void initView(Context context) {

		includeHead = View.inflate(context, R.layout.layout_headview, null);
		addView(includeHead,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		btnBack = (ImageButton) includeHead.findViewById(R.id.btn_back);
		tvHeadCenter = (TextView) includeHead.findViewById(R.id.tv_head_center);
		btnHeadAddedRight = (ImageView) includeHead.findViewById(R.id.btn_added_right);
		btnHeadRight = (Button) includeHead.findViewById(R.id.btn_head_right);
		ibtnHeadRight = (ImageButton) includeHead.findViewById(R.id.ibtn_head_right);
		
		lytConnecting = includeHead.findViewById(R.id.lyt_connecting);
		ivConnectLoading = (ImageView)includeHead.findViewById(R.id.iv_connect_loading);
		tvUnavaliable = (TextView)includeHead.findViewById(R.id.tv_connect_unavaliable);
		
		btnBack.setOnClickListener(this);
		tvHeadCenter.setOnClickListener(this);
		btnHeadRight.setOnClickListener(this);
		ibtnHeadRight.setOnClickListener(this);
		
		includeHead.setOnTouchListener(doubleClickTouch);
		tvHeadCenter.setOnTouchListener(doubleClickTouch);
		
		tvGroupLeft = (Button) includeHead.findViewById((R.id.group_left));
		tvGroupRight = (Button) includeHead.findViewById((R.id.group_right));
		
		tvGroupLeft.setOnClickListener(this);
		tvGroupRight.setOnClickListener(this);
	}
	
	/**
	 * 模拟实现双击事件
	 */
	public OnTouchListener doubleClickTouch = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			long tick = System.currentTimeMillis();
			Object tagTick = arg0.getTag();
			long lastTick = 0;
			if(tagTick != null && tagTick instanceof Long)
			{
				lastTick = (Long)tagTick;
			}
			if(tick - lastTick < 300)
			{
				//double clicked
				if(onDoubleClickListener != null)
				{
					onDoubleClickListener.OnDoubleClick(arg0);
				}
				arg0.setTag(0);
			}
			else
				arg0.setTag(tick);
			return false;
		}
	};

	/**
	 * 头部点击监听接口
	 * 
	 * @author yangzhao
	 * 
	 */
	public interface OnHeadClickListener {
		/**
		 * 中间标题点击事件
		 * @param v
		 */
		void OnCenterTVClick(View v);

		/**
		 * 左边按钮点击事件
		 * @param v
		 */
		void OnLeftBtnClick(View v);

		/**
		 * 右边按钮点击事件
		 * @param v
		 */
		void OnRightBtnClick(View v);

		/**
		 * 右边imageBtn点击事件
		 * @param v
		 */
		void OnRightIBtnClick(View v);
	}
	
	/**
	 * @author wangchong
	 * 头部被双击的事件的调用
	 */
	public interface OnHeadDoubleClickListener {
		void OnDoubleClick(View v);
	}

	public void setOnHeadClick(OnHeadClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	public void setOnDoubleClickListener(
			OnHeadDoubleClickListener onDoubleClickListener) {
		this.onDoubleClickListener = onDoubleClickListener;
	}

	@Override
	public void onClick(View v) {
		if (onClickListener == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.btn_back:
			OnLeftBtnClick(v);
			break;
		case R.id.tv_head_center:
			OnCenterTVClick(v);
			break;
		case R.id.btn_head_right:
			OnRightBtnClick(v);
			break;
		case R.id.ibtn_head_right:
			OnRightIBtnClick(v);
			break;
		case R.id.group_left:
			OnCenterTVClick(v);
			break;
		case R.id.group_right:
			OnCenterTVClick(v);
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化头部背景，文字
	 * 
	 * @author yangzhao
	 * 
	 */
	public interface OnHeadInitListener {
		/**
		 * 初始化中间按钮资源
		 * @param cTv
		 */
		void initCenterTv(TextView cTv);
		/**
		 * 初始化左边按钮资源
		 * @param lBtn
		 */
		void initLeftBtn(ImageButton lBtn);
		/**
		 * 初始化右边按钮资源
		 * @param rBtn
		 */
		void initRightBtn(Button rBtn);
		/**
		 * 初始话化右边ImageBtn资源
		 * @param ibtn
		 */
		void initRightIBtn(ImageButton ibtn);
	}
	
	public void OnLeftBtnClick(View v) {
		if (onClickListener == null) {
			return;
		}
		onClickListener.OnLeftBtnClick(v);
	}
	
	public void OnCenterTVClick(View v) {
		if (onClickListener == null) {
			return;
		}
		onClickListener.OnCenterTVClick(v);
	}
	
	public void OnRightBtnClick(View v) {
		if (onClickListener == null) {
			return;
		}
		onClickListener.OnRightBtnClick(v);
	}
	
	public void OnRightIBtnClick(View v) {
		if (onClickListener == null) {
			return;
		}
		onClickListener.OnRightIBtnClick(v);
	}
	/**
	 * 显示听筒模式
	 * @param resId
	 */
	public void showEarpieceView(int resId){
		if(resId!=0){
		btnHeadAddedRight.setVisibility(View.VISIBLE);
		btnHeadAddedRight.setImageResource(resId);
		}
	}
	/**
	 * 隐藏听筒图标
	 */
	public void hideEarpieceView(){
		btnHeadAddedRight.setVisibility(View.GONE);
	}
	
	/**
	 * 清除headview 的相关引用
	 */
	public void clearHead()
	{
		onClickListener = null;
	}

	public View getIncludeHead() {
		return includeHead;
	}
}
