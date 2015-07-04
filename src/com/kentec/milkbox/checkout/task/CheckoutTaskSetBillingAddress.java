package com.kentec.milkbox.checkout.task;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIMake;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskSetBillingAddress extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;
	private Address mAddress;

	public CheckoutTaskSetBillingAddress(CheckoutActivity activity, Address address) {
		this.mActivity = activity;
		this.mAddress = address;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.getViewGroup().getProgressDialog().show();
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		try {
			String cartID = mActivity.getData().getCartId();

			RpcClient client = CFG.getRpcClient();
			Boolean result = client.cartCustomerAddress(cartID, APIMake.billingAddress(mAddress));

			if (result) {
				mActivity.getOrder().setBillingAddress(mAddress);
				return OK;
			}

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == OK) {
			mActivity.getMethod().nextPage();
		} else if (result == ERROR) {
			mActivity.showMsg(R.string.error);
		}
	}
}