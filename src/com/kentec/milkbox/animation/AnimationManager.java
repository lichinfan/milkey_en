package com.kentec.milkbox.animation;

import com.kentec.milkbox.layout.ReLayoutManager;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

/**
* Animation總管<br>
* 統整動畫，並帶入一些預設參數
* @author andywu
* @date 2014.03.13
*
*/
public class AnimationManager
{
//	public AnimationManager()
//	{
//		
//	}
	/**
	 * 設定傳入參數是相對還是絕對
	 * 
	 * @author andywu
	 * @date 2014.03.13
	 */
	public enum ValueType
	{
		ABSOLUTE,
		RELATIVE		
	}
	/**
	 * 設定移動的方向與來源預設值
	 * 
	 * @author andywu
	 * @date 2014.03.13
	 */
	public enum TranslateDirection
	{
		LEFT_ENTER,
		LEFT_LEAVE,
		TOP_ENTER,
		TOP_LEAVE,
		RIGHT_ENTER,
		RIGHT_LEAVE,
		BOTTOM_ENTER,
		BOTTOM_LEAVE
	}
	/**
	 * 設定透明度的預設值<br>
	 * DARK:透明<br>
	 * LIGHT:完全不透明<br>
	 * NOW:現有透明度
	 * 
	 * @author andywu
	 * @date 2014.03.13
	 */
	public enum AlphaDirection
	{
		DARK_TO_LIGHT,
		LIGHT_TO_DARK,
		NOW_TO_LIGHT,
		NOW_TO_DARK
	}
	/**
	 * 設定執行完動畫時，物件的狀態。<br>
	 * (因為動畫是用矩陣做的，感應區並不會跟著變化)<br>
	 * SET_FILL為 Animation.setFillAfter(true);<br>
	 * SET_POSTION為直接改變物件屬性<br>
	 * SET_POSTION必須在沒有設定Listener時才有效
	 * 
	 * @author andywu
	 * @date 2014.03.13
	 */
	public enum EndingState
	{
		NONE,
		SET_FILL,
		SET_POSTION
	}
	/**
	 * 設定透明度變化動畫
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要變化的物件
	 * @param StartAlpha 起始的透明度，值域:[0-1]，0:透明、1:不透明
	 * @param EndAlpha 結束時的透明度，值域:[0-1]，0:透明、1:不透明
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */
	public static void SetAlpha(final View view , int StartAlpha,final int EndAlpha ,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,AnimationListener animationlistener)
	{
		AlphaAnimation mAnimation = new AlphaAnimation(StartAlpha, EndAlpha);
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							view.setAlpha(EndAlpha);
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
	/**
	 * 快速設定透明度變化動畫
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要變化的物件
	 * @param alphadirection 設定透明動畫種類，使用 AnimationManager.AlphaDirection
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */
	public static void SetAlpha(final View view , final AlphaDirection alphadirection ,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,AnimationListener animationlistener)
	{
		AlphaAnimation mAnimation ;
		switch(alphadirection)
		{
		default:
		case DARK_TO_LIGHT :
			mAnimation = new AlphaAnimation(0, 1);
			break;
		case LIGHT_TO_DARK :
			mAnimation = new AlphaAnimation(1, 0);
			break;
		case NOW_TO_LIGHT :
			mAnimation = new AlphaAnimation(view.getAlpha(), 1);
		case NOW_TO_DARK :
			mAnimation = new AlphaAnimation(view.getAlpha(), 0);
		}
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							switch(alphadirection)
							{
								default:
								case DARK_TO_LIGHT :
								case NOW_TO_LIGHT :
									view.setAlpha(1);
									break;
								case LIGHT_TO_DARK :
								case NOW_TO_DARK :
									view.setAlpha(0);
									break;
							}
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
	/**
	 * 設定旋轉變化動畫
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要變化的物件
	 * @param StartAngle 起始的角度，值域:[0-360]
	 * @param EndAngle 結束時的角，值域:[0-360]
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */	
	public static void SetRotate(final View view , int StartAngle,final int EndAngle ,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,AnimationListener animationlistener)
	{
		RotateAnimation mAnimation = new RotateAnimation(StartAngle, EndAngle,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							//TODO maybe have problem ?
							view.setRotation(EndAngle);
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
	/**
	 * 設定透明度變化動畫
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要變化的物件
	 * @param StartXScale 起始的橫向縮放倍率，1:原始大小
	 * @param StartYScale 起始時的縱向縮放倍率，1:原始大小
	 * @param EndXScale 結束時的橫向縮放倍率，1:原始大小
	 * @param EndYScale 結束時的縱向縮放倍率，1:原始大小
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */	
	public static void SetScale(final View view , int StartXScale, int StartYScale ,final int EndXScale,final int EndYScale ,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,AnimationListener animationlistener)
	{
		ScaleAnimation mAnimation = new ScaleAnimation(StartXScale, EndXScale ,  StartYScale, EndYScale);
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							//TODO maybe have problem ?
							view.setScaleX(EndXScale);
							view.setScaleY(EndYScale);
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
	/**
	 * 設定移動變化動畫
	 * @author andywu
	 * @date 2014.03.13 
	 * 
	 * @param view 要變化的物件
	 * @param StartX 起始的x座標
	 * @param StartY 起始時的y座標
	 * @param EndX 結束時的x座標
	 * @param EndY 結束時的y座標
	 * @param valuetype 傳入的座標模式，使用AnimationManager.ValueType
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param reLayoutmanager 重新定位使用，可傳入null，但會使SET_POSTION失效
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */	
	public static void SetTranslate(final View view , float StartX, float StartY ,final float EndX ,final float EndY ,final ValueType valuetype,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,final ReLayoutManager reLayoutmanager,AnimationListener animationlistener)
	{
		final float NowX = view.getX() ;
		final float NowY = view.getY();
		TranslateAnimation mAnimation ;
		switch(valuetype)
		{
			case ABSOLUTE :
				mAnimation = new TranslateAnimation(StartX-NowX, EndX-NowX , StartY-NowY , EndY-NowY);
				break;
			default :
			case RELATIVE :
				mAnimation = new TranslateAnimation(StartX, EndX , StartY , EndY);
				break ;
		}
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							if (reLayoutmanager != null)
							{
								switch(valuetype)
								{
								case ABSOLUTE :
									reLayoutmanager.RelativeSetPostion(view, (int)EndX, (int)EndY);
									break;
								default :
								case RELATIVE :
									reLayoutmanager.RelativeSetPostion(view, (int)(NowX+EndX), (int)(NowY+EndY));
									break ;
								}
							}
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
	/**
	 * 設定移動變化動畫
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要變化的物件
	 * @param translatedirection 設定移動動畫種類，使用AnimationManager.TranslateDirection
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param reLayoutmanager 重新定位使用，此函式一定要傳入，不可null
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */	
	public static void SetTranslate(final View view ,final TranslateDirection translatedirection,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,final ReLayoutManager reLayoutmanager,AnimationListener animationlistener)
	{
		if (reLayoutmanager == null)
		{
			Log.w("AnimationManager","SetTranslate - Parameter ERROR . SetTranslate傳入參數有誤");
			return ;
		}
		TranslateAnimation mAnimation ;
		switch(translatedirection)
		{
			case BOTTOM_ENTER :
				mAnimation = new TranslateAnimation(0, 0 , reLayoutmanager.GetScreenHeight() , 0);
				break;
			case BOTTOM_LEAVE :
				mAnimation = new TranslateAnimation(0, 0 , 0 , reLayoutmanager.GetScreenHeight());
				break;
			default :
			case LEFT_ENTER :
				mAnimation = new TranslateAnimation(-reLayoutmanager.GetScreenWidth(), 0 , 0 , 0);
				break;
			case LEFT_LEAVE :
				mAnimation = new TranslateAnimation(0, -reLayoutmanager.GetScreenWidth() , 0 , 0);
				break;
			case RIGHT_ENTER :
				mAnimation = new TranslateAnimation(reLayoutmanager.GetScreenWidth(), 0 , 0 , 0);
				break;
			case RIGHT_LEAVE :
				mAnimation = new TranslateAnimation(0, reLayoutmanager.GetScreenWidth() , 0 , 0);
				break;
			case TOP_ENTER :
				mAnimation = new TranslateAnimation(0, 0 , -reLayoutmanager.GetScreenHeight() , 0);
				break;
			case TOP_LEAVE :
				mAnimation = new TranslateAnimation(0, 0 , 0 , -reLayoutmanager.GetScreenHeight());
				break ;
		}
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							switch(translatedirection)
							{
								default :
								case BOTTOM_ENTER :
								case LEFT_ENTER :
								case RIGHT_ENTER :
								case TOP_ENTER :
									break ;
								case BOTTOM_LEAVE :
									reLayoutmanager.RelativeSetY(view, (int)(view.getY()+reLayoutmanager.GetScreenHeight()) );
									break;
								case LEFT_LEAVE :
									reLayoutmanager.RelativeSetX(view, (int)(view.getX()-reLayoutmanager.GetScreenWidth()) );
									break;
								case RIGHT_LEAVE :
									reLayoutmanager.RelativeSetX(view, (int)(view.getX()+reLayoutmanager.GetScreenWidth()) );
									break;
								case TOP_LEAVE :
									reLayoutmanager.RelativeSetY(view, (int)(view.getY()-reLayoutmanager.GetScreenHeight()) );
									break ;
							}
	
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
	/**
	 * 設定使用貝茲曲線移動變化動畫<br>
	 * 起始點就是物件原本的位置
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要變化的物件
	 * @param bezierXDelta 貝茲曲線中輔助點第一點的x座標
	 * @param bezierYDelta 貝茲曲線中輔助點第一點的y座標
	 * @param bezier2XDelta 貝茲曲線中輔助點第二點的x座標
	 * @param bezier2YDelta 貝茲曲線中輔助點第二點的y座標
	 * @param EndX 結束時的x座標
	 * @param EndY 結束時的y座標
	 * @param Time 變化的時間，單位為毫秒
	 * @param DelayTime 要延遲多久才啟動動畫，單位為毫秒
	 * @param Count 重複次數，-1為無限次，0不重複只做一次，1重複一次
	 * @param endingstate 做完動畫時，是否要對物件設定，使用 AnimationManager.EndingState
	 * @param interpolator 動畫插入器，參考InterpolatorManager
	 * @param reLayoutmanager 重新定位使用，可傳入null，但會使SET_POSTION失效
	 * @param animationlistener 設定AnimationListener，可以傳入null代表不設定，當有傳入時，EndingState中的SET_POSTION將會失效
	 */	
	public static void SetBezier(final View view , float bezierXDelta, float bezierYDelta, float bezier2XDelta, float bezier2YDelta ,final float EndX ,final float EndY ,int Time ,int DelayTime,int Count,EndingState endingstate,Interpolator interpolator,final ReLayoutManager reLayoutmanager,AnimationListener animationlistener)
	{
		final float NowX = view.getX() ;
		final float NowY = view.getY();
		BezierTranslateAnimation mAnimation= new BezierTranslateAnimation(NowX, EndX, NowY, EndY, bezierXDelta, bezierYDelta, bezier2XDelta, bezier2YDelta) ;
		mAnimation.setDuration(Time);
		mAnimation.setStartOffset(DelayTime);
		mAnimation.setRepeatCount(Count);
		mAnimation.setInterpolator(interpolator);
		if  (animationlistener != null)
			mAnimation.setAnimationListener(animationlistener);
		switch (endingstate)
		{
			case SET_POSTION :
				if (animationlistener == null)
				{
					mAnimation.setAnimationListener(new AnimationListener()
					{
						@Override
						public void onAnimationEnd(Animation animation)
						{
							if (reLayoutmanager != null)
							{
								reLayoutmanager.RelativeSetPostion(view, (int)EndX, (int)EndY);
							}
						}
						@Override
						public void onAnimationRepeat(Animation animation){}
						@Override
						public void onAnimationStart(Animation animation){}
					});					
				}
				break;
			case SET_FILL :
				mAnimation.setFillAfter(true);
				break;
			case NONE :
				break;
			default :
				break;
		}
		view.startAnimation(mAnimation);
	}
}
