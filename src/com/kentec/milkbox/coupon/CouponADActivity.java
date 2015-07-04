package com.kentec.milkbox.coupon;

import com.kentec.milkbox.R;
import com.kentec.milkbox.animation.AnimationManager;
import com.kentec.milkbox.animation.InterpolatorManager;
import com.kentec.milkbox.data.ActivityResultCode;
import com.kentec.milkbox.layout.ReLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
/**
* @author andywu
* @date 2014.03.31
* @category 加入coupon前的廣告頁面，若沒看完則不會加入
*
*/
public class CouponADActivity extends CouponBaseActivity
{
	private ProgressDialog mProgressDialog;
	private ReLayoutManager mReLayoutManager ;
	private Handler countHandler ;
	private Runnable mCountRunnable ;
	private Handler mHandler ; 
	private Runnable mRunnable ;
	private long nowTime ;
	private long receiveTime = 15000 ;
	
	private VideoView mplayerMedia ;
	private LinearLayout mplayerAll ;
	private Button mplayerCannel ;
	private LinearLayout mplayerCenter ;
	private ProgressBar mplayerSeek ;
	private TextView mplayerTime ;
	private Button mplayerAdd ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_ad);
		mProgressDialog = new ProgressDialog(this);
		mReLayoutManager = new ReLayoutManager(this) ;
		
		mplayerMedia = (VideoView)findViewById(R.id.playerMedia) ;
		mplayerAll = (LinearLayout) findViewById(R.id.playerAll) ;
		mplayerCannel = (Button) findViewById(R.id.playerCannel) ;		
		mplayerCenter = (LinearLayout) findViewById(R.id.playerCenter) ;
		mplayerSeek = (ProgressBar) findViewById(R.id.playerSeek) ;
		mplayerTime = (TextView) findViewById(R.id.playerTime) ;
		mplayerAdd = (Button) findViewById(R.id.playerAdd) ;
		
		mReLayoutManager.LinearSetSize(mplayerCannel, 100, 100) ;
		mReLayoutManager.LinearSetSize(mplayerAdd, 100, 100) ;
//		mReLayoutManager.LinearSetHeight(mplayerSeek, 5);
		
		mplayerMedia.setOnPreparedListener(new OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer arg0)
			{
				setTime(receiveTime) ;
			}
		});
		
		mHandler = new Handler(); 
		countHandler = new Handler();
		mRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				nowTime -= 1000 ;
//				Log.d("andy","nowTime:" + nowTime/1000);
				mplayerAdd.setText("(" + nowTime/1000 + ")" + getResources().getString(R.string.add));
				
				String str = "" ;
				String str2 = "" ;
				if ( ((receiveTime-nowTime)/1000) <10 )
					str = "0" ;
				else
					str = "" ;
				str += (receiveTime-nowTime)/1000 ;
				
				if ( (receiveTime/1000) <10 )
					str2 = "0" ;
				else
					str2 = "" ;
				str2 += receiveTime/1000 ;
				
				mplayerTime.setText("00:" + str + "  /  00:" + str2);
				mplayerSeek.setProgress((int)(1.0*(receiveTime-nowTime)/receiveTime*100));
				
				mHandler.postDelayed(mRunnable,1000);

			}
		};
		mCountRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				timeFinish();
			}
		};
		
		playChannel() ; // test
//		setTime(15000) ;
	}

	/*@Override
	public void onBackPressed()
	{
		//Log.d("andy","onBackPressed" );
		setResult(ActivityResultCode.OK);
		this.finish();
		
		//super.onBackPressed();
	}*/
	private void setTime(long time)
	{
		showMenu();
		if ( time > 0 )
		{
			nowTime = time ;
			startcount( time ) ;
		}
		else
		{
			nowTime = 15000 ;
			startcount(15000) ;
		}
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		showMenu();
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK :
			setResult(ActivityResultCode.CANCEL);
			stopRunnable();
			break;
//		case KeyEvent. :
//			break;		
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		showMenu();
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void stopRunnable()
	{
		mHandler.removeCallbacks(mRunnable) ;
		countHandler.removeCallbacks(mCountRunnable) ;
	}
	private void timeFinish()
	{
		stopRunnable();
		setResult(ActivityResultCode.OK);
		this.finish();
	}
	
	private void startcount(long time)
	{
		countHandler.postDelayed(mCountRunnable,time+500);
		mHandler.postDelayed(mRunnable,1000);
	}
	
	private void showMenu()
	{
//		Log.d("andy","showMenu");
		AnimationManager.SetAlpha(mplayerAll, AnimationManager.AlphaDirection.NOW_TO_LIGHT, 500, 0, 0, AnimationManager.EndingState.SET_FILL, InterpolatorManager.OriginalBalance(), null);
		hideMenu();
	}
	private void hideMenu()
	{
//		Log.d("andy","hideMenu");
		AnimationManager.SetAlpha(mplayerAll, AnimationManager.AlphaDirection.NOW_TO_DARK, 2000, 3000, 0, AnimationManager.EndingState.SET_FILL, InterpolatorManager.OriginalBalance(), null);
	}
	
	private void playChannel()
	//private void playChannel(TvChannel channel)
	{
		mplayerMedia.stopPlayback();
		mplayerMedia.setVideoURI(Uri.parse("http://emilk.kenmec.com/mfiles/013-Grocery Delivery The next big thing.mp4"));
		mplayerMedia.start();
	}
}
