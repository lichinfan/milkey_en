package com.kentec.milkbox.animation;

import android.util.Log;
import android.view.animation.Interpolator;
/**
* @author andywu
* @date 2014.02.12
* 自定義的Interpolator
* @date 2014.03.13 
* 加入full mode
*/
/*
 * easy mode is Bezier
 *  
 * full mode please watch
 * http://st-on-it.blogspot.tw/2011/05/calculating-cubic-bezier-function.html
 * 
 */
public class CustomizeInterpolator implements Interpolator
{
	float Bezier1 = 0 ;
	float Bezier2 = 0 ;
	boolean full_mode  = false ;
	float ax ;
	float bx ;
	float cx ;
	float ay ;
	float by ;
	float cy ;
	
	
	public CustomizeInterpolator(float Bezier1, float Bezier2)
	{
		super();
		this.Bezier1 = Bezier1;
		this.Bezier2 = Bezier2;
		full_mode  = false ;
	}
	public CustomizeInterpolator(float x1, float y1,float x2, float y2)
	{
		super();
		full_mode  = true ;
		cx = 3 * x1 ;
		bx = 3 * (x2-x1) - cx ;
		ax = 1 - cx - bx ;
		 
		cy = 3 * y1 ;
		by = 3 * (y2-y1) - cy ;
		ay = 1 - cy - by ;
//		test_bezier();
	}	
	@Override
	public float getInterpolation(float time)
	{
		if (!full_mode)
		{			
			return (float) (3.0*Bezier1*time*(1.0-time)*(1.0-time)
					+3.0*Bezier2*time*time*(1.0-time)
					+time*time*time);
		}
		else
		{
			return bezier_y(find_x_for(time)) ;
//			return get_x(time);
		}
	}

	private float bezier_x(float t)
	{
		return t * (cx + t * (bx + t * ax));
	} 
	private float bezier_y(float t)
	{
		return t * (cy + t * (by + t * ay));
	}
	private float  bezier_x_der(float t)
	{
		return cx + t * (2*bx + t * 3*ax);
	}
	private float find_x_for(float t)
	{
		float x = t, z;
		int i = 0 ;
		while (i < 5)
		{
			z = bezier_x(x) - t;
			if (Math.abs(z) < 1e-3)
				break;
		    x = x - z / bezier_x_der(x);
		    i++;
		 }
		 return x;
	}
	
	/*private float get_x(float x) {
		float t0; 
		float t1;
		float t2;
		float x2;
		float d2;
		float i;

	    // First try a few iterations of Newton's method -- normally very fast.
	    for (t2 = x, i = 0; i < 8; i++) {
	        x2 = bezier_x(t2) - x;
	        if (Math.abs (x2) < 10)
	            return t2;
	        d2 = find_x_for(t2);
	        if (Math.abs(d2) < 10)
	            break;
	        t2 = t2 - x2 / d2;
	    }

	    // No solution found - use bi-section
	    t0 = 0.0f;
	    t1 = 1.0f;
	    t2 = x;

	    if (t2 < t0) return t0;
	    if (t2 > t1) return t1;

	    while (t0 < t1) {
	        x2 = bezier_x(t2);
	        if (Math.abs(x2 - x) < 10)
	            return t2;
	        if (x > x2) t0 = t2;
	        else t1 = t2;

	        t2 = (float) ((t1 - t0) * .5 + t0);
	    }

	    // Give up
	    return t2;
	}*/

	private void test_bezier()
	{
		for (float i=0; i < 1.001; i+=0.01)
		{
			Log.d("andy",i + " " + bezier_y(find_x_for(i))); 
//			Log.d("andy2",i + " " + get_x(i) ); 
		}

	}

}
