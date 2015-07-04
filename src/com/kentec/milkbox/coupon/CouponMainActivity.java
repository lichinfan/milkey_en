package com.kentec.milkbox.coupon;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.asr.CouponASRCreater;
import com.kentec.milkbox.coupon.adapter.CouponMainAdapter;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.coupon.task.CouponCollectTask;
import com.kentec.milkbox.coupon.task.CouponCreateCartTask;
import com.kentec.milkbox.coupon.task.CouponMainTask;
import com.kentec.milkbox.coupon.task.CouponNaviTask;
import com.kentec.milkbox.coupon.view.CouponDialog;
import com.kentec.milkbox.coupon.view.MyCouponDialog;
import com.kentec.milkbox.data.ActivityResultCode;
import com.kentec.milkbox.grocery.data.Category;


public class CouponMainActivity extends CouponBaseActivity {
	
//	private static final String TAG = "CouponMainActivity";
	public static final int AD_RETURN = 8;
	
	private GridView mMainGv;
	private LinearLayout mMainLl;
		
	private CouponMainAdapter mAdapter;

	private String mCartId;
	private ArrayList<Coupon> mCouponList;
	public CouponDialog dialog ;
	private Coupon coupon ;
	public ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_main);
		doCheckTimeOut();
		
		TextView couponTv = (TextView) findViewById(R.id.couponTv);
		couponTv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startASR();
			}	
		});
		
		LinearLayout myCouponLl = (LinearLayout) findViewById(R.id.myCouponLl);

		relayout(couponTv, 7);
		relayout(myCouponLl, 2);

		mMainLl = (LinearLayout) findViewById(R.id.mainLl);		
		mMainGv = (GridView) findViewById(R.id.mainGv);
				
		myCouponLl.setOnClickListener(myCouponClickListener);
				
		// initial coupon cart
		mCartId = getCouponCartId();
		if(mCartId==null) {
			new CouponCreateCartTask(this,createCartSucessListener);
		}
		
		// load navi data
		new CouponNaviTask(this,naviSucessListener);
		
		/**
		* @author andywu
		* @date 2014.03.12
		* 加入語音
		*/
		new CouponASRCreater().init(this);
		
		mProgressDialog = new ProgressDialog(this);		
	}		
	
	CouponNaviTask.OnSucessListener naviSucessListener = new CouponNaviTask.OnSucessListener() {
		@Override
		public void success(ArrayList<Category> list) {
			mMainLl.removeAllViews();
						
			int naviBtnWidth = (int) (mBaseWidth*2.2);
			int naviBtnMarginRight = (int) (naviBtnWidth*0.1);
			
			Category c;
			Button naviBtn;
			for(int i=0; i<list.size(); i++) {
				c = list.get(i);
				naviBtn = new Button(mActivity);				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(naviBtnWidth, LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 0, naviBtnMarginRight, 0);
				params.gravity = Gravity.CENTER;				
				naviBtn.setLayoutParams(params);				
				naviBtn.setTag(c.getId());
				naviBtn.setText(c.getName());				
				naviBtn.setTextSize(18);
				naviBtn.setTextColor(Color.parseColor("#1c350c"));
				naviBtn.setBackgroundResource(R.drawable.grocery_category_btn);
				naviBtn.setOnClickListener(naviClickListener);
				naviBtn.setOnFocusChangeListener( new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View view, boolean isFocused) {
						if(isFocused) {
							((Button)view).setTextColor(Color.WHITE);						
						} else {
							((Button)view).setTextColor(Color.parseColor("#1c350c"));						
						}
					}				
				});
				
				mMainLl.addView(naviBtn);
				
				if(i==0) {
					new CouponMainTask(mActivity,mainSucessListener,c.getId());					
				}
			}			

		}		
	};
	
	CouponMainTask.OnSucessListener mainSucessListener = new CouponMainTask.OnSucessListener() {
		@Override
		public void success(ArrayList<Coupon> list) {			
			mCouponList = list;			
			mAdapter = new CouponMainAdapter(CouponMainActivity.this,mCouponList);		
			mMainGv.setAdapter(mAdapter);	
			mMainGv.setOnItemClickListener(itemClickListener);
		}		
	};

	
	CouponCreateCartTask.OnSucessListener createCartSucessListener = new CouponCreateCartTask.OnSucessListener() {
		@Override
		public void success(String cartId) {
			mCartId = cartId;
			Editor editor = getSP().edit();
			editor.putString(CFG.PREF_COUPON_CART_ID, cartId);
			editor.commit();
		}		
	};

	OnClickListener naviClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			reTimeOut();
			String categoryId = (String) view.getTag(); 
			new CouponMainTask(mActivity,mainSucessListener,categoryId);
		}
	}; 

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			coupon = mCouponList.get(position);
			dialog = new CouponDialog(mActivity, mCartId, coupon);
			dialog.show();
		}
	};	
	
	OnClickListener myCouponClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			reTimeOut();
			MyCouponDialog dialog = new MyCouponDialog(CouponMainActivity.this, mCartId);
			dialog.show();
		}
	};
	
	public void openMyCoupon() {
		reTimeOut();
		MyCouponDialog dialog = new MyCouponDialog(CouponMainActivity.this, mCartId);
		dialog.show();
	}

	@Override
	protected void onResume() {
		doCheckTimeOut();
		super.onPause(); 
	}

	@Override
	protected void onRestart() {
		doCheckTimeOut();
		super.onRestart();
	}
	
	public void showAD()
	{
		Intent intent = new Intent();
		intent.setClass(CouponMainActivity.this, CouponADActivity.class);
		startActivityForResult(intent,AD_RETURN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//Log.d("andy","onActivityResult:" + requestCode + " " + resultCode );
		if (requestCode == CouponMainActivity.AD_RETURN)
		{
			//Log.d("andy","AD_RETURN" );
			switch (resultCode)
			{
			case ActivityResultCode.OK:
				//Log.d("andy","ResultCode.OK" );
				new CouponCollectTask(CouponMainActivity.this,collectSucessListener,mCartId,coupon.getId());
				showProgressDialog();
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	CouponCollectTask.OnSucessListener collectSucessListener = new CouponCollectTask.OnSucessListener() {
		@Override
		public void success() {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			mActivity.showOkMsg(R.string.msg_add_my_coupon_success);
		}		
	};
	public void showProgressDialog()
	{
		mProgressDialog.show();
	}
	public void closeProgressDialog()
	{
		mProgressDialog.dismiss();
	}
}