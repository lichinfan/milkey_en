package com.kentec.milkbox.layout;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
* @author andywu
* @date 2014.03.13
* @category 排版用class<br>
* 使用前要確保有使用建構子初始化<br>
* Relative- 開頭的，只能用於RelativeLayout<br>
* -WithReset 結尾的，會把view之前的排版都清掉，譬如在XML裡面大概排版的設定也可以清除
*/
public class ReLayoutManager
{
	// 機器output
	private int OriWidth;
	private int OriHeight;
	// X Y軸縮放比例
	private double ScaleWidth;
	private double ScaleHeight;
	// 輸出目標解析度
	private int srcDisW = 1920;
	private int srcDisH = 1080;
	
	/**
	 * 建構子，傳入Activity作為抓取長寬
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param mActivity 傳入頁面
	 */
	public ReLayoutManager(Activity mActivity)
	{
		ResetActivity(mActivity);
	}
	/**
	 * 原則上不使用。用於重新設定頁面大小
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param mActivity 傳入頁面
	 */
	public void ResetActivity(Activity mActivity)
	{
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		OriWidth = dm.widthPixels;
		OriHeight = dm.heightPixels;
		ScaleWidth = (double) OriWidth / (double) srcDisW;
		ScaleHeight = (double) OriHeight / (double) srcDisH;		
	}
	/**
	 * 原則上不使用。用於調整輸出目標螢幕解析度(預設是1920*1080)
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param w 新的長
	 * @param h 新的高
	 */
	public void SetOutputScreenSize(int w , int h)
	{
		srcDisW = w ;
		srcDisH = h ;
	}
	/**
	 * 拿到螢幕真實的寬
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @return int 螢幕的寬
	 */
	public int GetScreenWidth()
	{
		return OriWidth ;
	}
	/**
	 * 拿到螢幕真實的高
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @return int 螢幕的高
	 */
	public int GetScreenHeight()
	{
		return OriHeight ;
	}
	/**
	 * 轉換X座標
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param x 要轉換的X座標
	 * @return int 轉換後的X座標
	 */
	public int TransformX(int x)
	{
		return (int) ((double) x * ScaleWidth) ;
	}
	/**
	 * 轉換Y座標
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param y 要轉換的Y座標
	 * @return int 轉換後的Y座標
	 */
	public int TransformY(int y)
	{
		return (int) ((double) y * ScaleHeight) ;
	}
	/**
	 * 重置後、重新調整size
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 * @param h 調整後的高度
	 */
	public void RelativeSetSizeWithReset(View view, int w, int h)
	{
		int newW = (int) ((double) w * ScaleWidth);
		int newH = (int) ((double) h * ScaleHeight);

		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.width = newW;
		lp.height = newH;
		view.setLayoutParams(lp);		
	}
	/**
	 * 重置後、重新調整位置
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param x 調整後的x座標
	 * @param y 調整後的y座標
	 */
	public void RelativeSetPostionWithReset(View view,int x, int y)
	{
		int newX = (int) ((double) x * ScaleWidth);
		int newY = (int) ((double) y * ScaleHeight);

		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(newX, newY, 0, 0);
		view.setLayoutParams(lp);		
	}
	/**
	 * 重置後、重新調整size&位置
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 * @param h 調整後的高度
	 * @param x 調整後的x座標
	 * @param y 調整後的y座標
	 */
	public void RelativeSetPostionAndSizeWithReset(View view, int w, int h, int x, int y)
	{
		int newW = (int) ((double) w * ScaleWidth);
		int newH = (int) ((double) h * ScaleHeight);
		int newX = (int) ((double) x * ScaleWidth);
		int newY = (int) ((double) y * ScaleHeight);

		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.width = newW;
		lp.height = newH;
		lp.setMargins(newX, newY, 0, 0);
		view.setLayoutParams(lp);
	}
	/**
	 * 重新調整size
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 * @param h 調整後的高度
	 */
	public void RelativeSetSize(View view, int w, int h)
	{
		int newW = (int) ((double) w * ScaleWidth);
		int newH = (int) ((double) h * ScaleHeight);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		view.setLayoutParams(params);	
	}
	/**
	 * 重新調整位置
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param x 調整後的x座標
	 * @param y 調整後的y座標
	 */
	public void RelativeSetPostion(View view,int x, int y)
	{
		int newX = (int) ((double) x * ScaleWidth);
		int newY = (int) ((double) y * ScaleHeight);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(newX, newY, 0, 0);
		view.setLayoutParams(params);
	}
	/**
	 * 重新調整size&位置
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 * @param h 調整後的高度
	 * @param x 調整後的x座標
	 * @param y 調整後的y座標
	 */
	public void RelativeSetPostionAndSize(View view, int w, int h, int x, int y)
	{
		int newW = (int) ((double) w * ScaleWidth);
		int newH = (int) ((double) h * ScaleHeight);
		int newX = (int) ((double) x * ScaleWidth);
		int newY = (int) ((double) y * ScaleHeight);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		params.setMargins(newX, newY, 0, 0);
		view.setLayoutParams(params);
	}
	/**
	 * 重新調整X座標
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param x 調整後的x座標
	 */
	public void RelativeSetX(View view, int x)
	{
		int newX = (int) ((double) x * ScaleWidth);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(newX, params.topMargin, params.rightMargin, params.bottomMargin);
		view.setLayoutParams(params);		
	}
	/**
	 * 重新調整Y座標
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param y 調整後的y座標
	 */
	public void RelativeSetY(View view, int y)
	{
		int newY = (int) ((double) y * ScaleHeight);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(params.leftMargin, newY, params.rightMargin, params.bottomMargin);
		view.setLayoutParams(params);		
	}
	/**
	 * 重新調整寬
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 */
	public void RelativeSetWidth(View view, int w)
	{
		int newW = (int) ((double) w * ScaleWidth);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		view.setLayoutParams(params);
	}
	/**
	 * 重新調整高
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param h 調整後的高度
	 */
	public void RelativeSetHeight(View view, int h)
	{
		int newH = (int) ((double) h * ScaleHeight);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.height = newH;
		view.setLayoutParams(params);
	}
	/**
	 * 重新調整size
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 * @param h 調整後的高度
	 */
	public void LinearSetSize(View view, int w, int h)
	{
		int newW = (int) ((double) w * ScaleWidth);
		int newH = (int) ((double) h * ScaleHeight);

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		view.setLayoutParams(params);	
	}
	/**
	 * 重新調整寬
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param w 調整後的寬度
	 */
	public void LinearSetWidth(View view, int w)
	{
		int newW = (int) ((double) w * ScaleWidth);

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		view.setLayoutParams(params);
	}
	/**
	 * 重新調整高
	 * @author andywu
	 * @date 2014.03.13
	 * 
	 * @param view 要調整的元件
	 * @param h 調整後的高度
	 */
	public void LinearSetHeight(View view, int h)
	{
		int newH = (int) ((double) h * ScaleHeight);

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.height = newH;
		view.setLayoutParams(params);
	}
}
