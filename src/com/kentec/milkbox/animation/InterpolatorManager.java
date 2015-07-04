package com.kentec.milkbox.animation;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
/**
* @author andywu
* @date 2014.03.13
* @category Interpolator總管<br>
* Fast採用的是一維貝茲曲線，而Full才是把貝茲曲線完全轉換<br>
* 原則上用Full跑動畫，若跑不動就降級為Fast，<br>
* 再不行，就要用原生的，或是使用自定義曲線函數<br>
*/
public class InterpolatorManager
{
//	public InterpolatorManager()
//	{
//	}
	
	/**
	 * 一維貝茲曲線，可自訂義曲線。(原則上不用這隻)
	 */
	public static Interpolator FaseCurve(float Bezier1, float Bezier2)
	{
		return new CustomizeInterpolator(Bezier1,Bezier2);
	}
	/**
	 * 完整二維貝茲曲線，可自訂義曲線。(原則上不用這隻)
	 */
	public static Interpolator FullCurve(float x1, float y1,float x2, float y2)
	{
		return new CustomizeInterpolator(x1,y1,x2,y2) ;
	}
	/**
	 * 一般物件移動
	 */	
	public static Interpolator FastMove()
	{
		return new CustomizeInterpolator(0.039f, 1f);
	}
	/**
	 * 一般物件移動
	 */	
	public static Interpolator FullMove()
	{
		return new CustomizeInterpolator(0.525f,0.07f,0.35f,-0.07f);
	}	
	/**
	 * pop出來
	 */	
	public static Interpolator FastPop()
	{
		return new CustomizeInterpolator(0.8f, 1.3f);
	}
	/**
	 * pop出來
	 */	
	public static Interpolator FullPop()
	{
		return new CustomizeInterpolator(0.5f,0f,0.57f,1.57f);
	}	
	/**
	 * 一般物件移動+衝過頭
	 */	
	public static Interpolator FastMoveEndWithOver()
	{
		return new CustomizeInterpolator(0.0f, 1.57f);
	}
	/**
	 * 一般物件移動+衝過頭
	 */	
	public static Interpolator FullMoveEndWithOver()
	{
		return new CustomizeInterpolator(0.5f,0f,0.57f,1.57f);
	}
	
	//*****************************************************
	
	//*****************************************************
	/**
	 * 標準等速變化
	 */	
	public static Interpolator OriginalBalance()
	{
		return new LinearInterpolator();
	}	
	/**
	 * 速度越來越快
	 */	
	public static Interpolator OriginalFaster()
	{
		return new AccelerateInterpolator();
	}	
	/**
	 * 速度越來越快
	 * @param factor 越大，效果越明顯。 (預設值是1)
	 */	
	public static Interpolator OriginalFaster(float factor)
	{
		return new AccelerateInterpolator(factor);
	}	
	/**
	 * 速度越來越慢
	 */	
	public static Interpolator OriginalSlower()
	{
		return new DecelerateInterpolator();
	}	
	/**
	 * 速度越來越慢
	 * @param factor 越大，效果越明顯。 (預設值是1)
	 */	
	public static Interpolator OriginalSlower(float factor)
	{
		return new DecelerateInterpolator(factor);
	}	
	/**
	 * 一開始很快，後面很慢
	 */	
	public static Interpolator OriginalFastToSlow()
	{
		return new AccelerateDecelerateInterpolator();
	}	
	/**
	 * 先往回退後再前進
	 */	
	public static Interpolator OriginalStartWithBack()
	{
		return new AnticipateInterpolator();
	}	
	/**
	 * 先往回退後再前進
	 * @param factor 越大，退越多、後面加速越快。 (預設值是1)
	 */	
	public static Interpolator OriginalStartWithBack(float factor)
	{
		return new AnticipateInterpolator(factor);
	}
	/**
	 * 前進過頭後再回到終點
	 */	
	public static Interpolator OriginalEndWithOver()
	{
		return new OvershootInterpolator();
	}	
	/**
	 * 前進過頭後再回到終點
	 * @param factor 越大，超出終點越多、前面加速越快。 (預設值是1)
	 */	
	public static Interpolator OriginalEndWithOver(float factor)
	{
		return new OvershootInterpolator(factor);
	}
	/**
	 * 先往回退後、前進，前進過頭後再回到終點
	 */	
	public static Interpolator OriginalStartWithBackEndWithOver()
	{
		return new AnticipateOvershootInterpolator();
	}	
	/**
	 * 先往回退後、前進，前進過頭後再回到終點
	 * @param factor 越大，超出越多、中間加速越快。 (預設值是1)
	 */	
	public static Interpolator OriginalStartWithBackEndWithOver(float factor)
	{
		return new AnticipateOvershootInterpolator(factor);
	}	
	/**
	 * 到終點時物理撞擊效果、會彈
	 */	
	public static Interpolator OriginalBounce()
	{
		return new BounceInterpolator();
	}	
	/**
	 * sin的周期搖擺
	 * @param factor 越大，越頻繁。也代表一次裡面要包含幾個sin周期 (預設值是1)
	 */	
	public static Interpolator OriginalSinCycle(float factor)
	{
		return new CycleInterpolator(factor);
	}	
}
