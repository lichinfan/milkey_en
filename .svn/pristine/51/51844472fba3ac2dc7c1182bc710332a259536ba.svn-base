package com.kentec.milkbox.grocery.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.OrderHistory;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.task.BaseTask;
import com.kentec.milkbox.utils.DateUtil;

public class GroceryLoadOrderHistoryTask extends BaseTask {
	
	private static final String TAG = "OrderHistoryLoad";
	
	private ArrayList<OrderHistory> mList = new ArrayList<OrderHistory>();;
	private String mCustomerId;
	private String mStoreId;

	public GroceryLoadOrderHistoryTask(BaseActivity activity,OnSucessListener listener, String customerId, String storeId) {
		super(activity);
		mListener = listener;
		mCustomerId = customerId;
		mStoreId = storeId;
	}

	
	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ArrayList<OrderHistory> mList);
		public void fail(int failCode);
	}
	
	@Override
	protected int doInBackground() throws Exception {
		try {
			
			RpcClient client = CFG.getRpcClient();
			Object[] list = client.salesOrderList(mCustomerId,mStoreId);
			mList.clear();
			OrderHistory oh;
			HashMap<?,?> row;
			for(int i=list.length-1; i>=0; i--) {
				row = (HashMap<?,?>) list[i];
				oh = GroceryApiParser.parserOrderHistory(row);
				oh.setDate(DateUtil.toLocalTime(oh.getDate()));					
				mList.add(oh);					
			}
											
			return OK;
			
		} catch(Exception e) {
			Log.e(TAG,e.toString());
		}
		return ERROR;			

	}

	@Override
	protected void success() {
		if (mListener!=null)
			mListener.success(mList);
	}

	@Override
	protected void fail(int failCode) {
		if (mListener!=null)
			mListener.fail(failCode);
			
//		if(failCode==NO_DATA) {
//			mActivity.showMsg(R.string.no_data);
//		}		
	}
	
}

