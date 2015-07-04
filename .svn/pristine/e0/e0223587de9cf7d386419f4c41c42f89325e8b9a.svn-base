package com.kentec.milkbox.grocery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.GroceryVoiceASRCreater;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.GroceryVoiceState.GroceryVoiceStateEnum;
import com.kentec.milkbox.grocery.adapter.ShoppingCartAdapter;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.grocery.task.DeleteItemTask;
import com.kentec.milkbox.grocery.task.GroceryInitialTask;
import com.kentec.milkbox.grocery.task.GrocerySearchTask;
import com.kentec.milkbox.utils.Format;


/**
* @author andywu
* @date 2014.03.24
* @category 語音模式
*
*/
public class GroceryVoiceMainActivity extends ASRActivity
{
	private RelativeLayout search_layout ;
	private RelativeLayout bill_layout ;
	private RelativeLayout setting_layout ;
	private TextView testTV ; // 測試用
	private ListView mMainLv;
	
	private GroceryVoiceState.GroceryVoiceStateEnum nowstate  = GroceryVoiceStateEnum.NONE;
	private ArrayList<String> keyword = new ArrayList<String>() ;
	private ArrayList<Product> data = new ArrayList<Product>() ;
	private int chooseDataId = 0 ;
	protected String mCartId;
	protected String mStoreId;
	private ProgressDialog mProgressDialog;
	private ArrayList<Product> mList;
	private ShoppingCartAdapter mAdapter;
	private double mTotal;
	private int billNowPage = 1 ;
	private int billSelectItem = 1 ;
	private final int billPageNumber = 8 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grocery_voice_main);
		//
		
		search_layout = (RelativeLayout) findViewById(R.id.search_layout) ;
		bill_layout = (RelativeLayout) findViewById(R.id.bill_layout) ;
		setting_layout = (RelativeLayout) findViewById(R.id.setting_layout) ;
		testTV = (TextView) findViewById(R.id.testTV);
		mMainLv = (ListView) findViewById(R.id.mainLv);
		mList = new ArrayList<Product>();
		mAdapter = new ShoppingCartAdapter(mActivity, mList, 100, mCartId);
		mMainLv.setAdapter(mAdapter);
//		mMainLv.setOnItemClickListener(itemClickListener);
		
		new GroceryInitialTask(mActivity, initSuccessListener, CFG.IMEI);
		mProgressDialog = new ProgressDialog(this);
		new GroceryVoiceASRCreater().init(this);
		nowstate = GroceryVoiceStateEnum.START ;
		startSearchState();
	}
	
	private void startSearchState()
	{
		//animation
		Log.d("andy","startSearchState");
		search_layout.setVisibility(View.VISIBLE) ;
		bill_layout.setVisibility(View.INVISIBLE) ;
		setting_layout.setVisibility(View.INVISIBLE) ;
		nowstate = GroceryVoiceStateEnum.SEARCH_NONE ;
		testTV.setText("Hi " + CFG.getNickName() + "" + getResources().getString(R.string.grocery_voice_whatdoyouwant));
		//MilkBoxTTS.speak("Hi " + CFG.getNickName() + "" + getResources().getString(R.string.grocery_voice_whatdoyouwant),"whatdoyouwant");
		
		moderatorInteractiveDialog("Hi " + CFG.getNickName() + "" + getResources().getString(R.string.grocery_voice_whatdoyouwant)
									, 2
									,"Hi " + CFG.getNickName() + "" + getResources().getString(R.string.grocery_voice_whatdoyouwant),
									"whatdoyouwant", 9);	
	}
	private void endSearchState()
	{
		//animation
		Log.d("andy","endSearchState");
		bill_layout.setVisibility(View.VISIBLE) ;
		setting_layout.setVisibility(View.INVISIBLE) ;
		startBillState() ;
	}
	private void startBillState()
	{
		//animation
		Log.d("andy","startBillState");
		search_layout.setVisibility(View.INVISIBLE) ;
		setting_layout.setVisibility(View.INVISIBLE) ;
		nowstate = GroceryVoiceStateEnum.BILL_NONE ;
		new LoadCartTask(mCartId).execute();
	}
	private void endBillState(GroceryVoiceStateEnum toState)
	{
		//animation
		Log.d("andy","endBillState");
		if (toState.compareTo(GroceryVoiceStateEnum.SEARCH_START) >0 
				&& toState.compareTo(GroceryVoiceStateEnum.SEARCH_END) <0)
		{
			search_layout.setVisibility(View.VISIBLE) ;
			startSearchState() ;
		}
		else if (toState.compareTo(GroceryVoiceStateEnum.SETTING_START) >0 
				&& toState.compareTo(GroceryVoiceStateEnum.SETTING_END) <0)
		{
			setting_layout.setVisibility(View.VISIBLE) ;
			startSettingState() ;
		}	
	}
	private void startSettingState()
	{
		//animation
		Log.d("andy","startSettingState");
		bill_layout.setVisibility(View.INVISIBLE) ;
		nowstate = GroceryVoiceStateEnum.SETTING_NONE ;
	}
	private void endSettingState(GroceryVoiceStateEnum toState)
	{
		//animation
		Log.d("andy","endSettingState");
		if (toState.compareTo(GroceryVoiceStateEnum.BILL_START) >0 
				&& toState.compareTo(GroceryVoiceStateEnum.BILL_END) <0)
		{
			bill_layout.setVisibility(View.VISIBLE) ;
			startBillState() ;
		}
		else if (toState == GroceryVoiceStateEnum.FINISH)
		{
			setResult(RESULT_OK);
			this.finish();
		}			
	}
	private void searchByKeyword()
	{
		testTV.setText(getResources().getString(R.string.grocery_voice_thinking));
		MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_thinking),"thinking");
		String s = "" ;
		for (int i=0; i< keyword.size();i++)
		{
			s += keyword.get(i) + " , " ;
		}
		Log.d("andy","all keyword:" + s );
		// search by keyword
		openProductToCardWithNumber( s ) ;
	}
	private void checkCartReturn()
	{
		// cacluate total price
		
		// total price
//		testTV.setText(getResources().getString(R.string.grocery_voice_howmany));
//		MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_howmany),"howmany");
	
	}
	public void addCartReturn()
	{
		testTV.setText(getResources().getString(R.string.grocery_voice_anythingelse));
		MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_anythingelse),"anythingelse");
		
		// clean view 
		keyword.clear() ;
		chooseDataId = 0 ;
		nowstate = GroceryVoiceStateEnum.SEARCH_NONE ;		
	}
	@SuppressWarnings("unchecked")
	public void searchReturn(ArrayList<Product> plist)
	{
		data = (ArrayList<Product>) plist.clone() ;
		//data change ?
		//test_show
		for (int i=0 ; i< data.size() ; i++)
		{
			Log.d("andy", "    data"+ i +":"+ data.get(i).getSku() ) ;
		}
		
		if (data.size() ==0 )
		{
			testTV.setText(getResources().getString(R.string.grocery_voice_notfound));
			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_notfound),"notfound");
//			resetSearch();
		}
		else if (data.size() == 1)
		{
			testTV.setText(getResources().getString(R.string.grocery_voice_howmany));
//			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_howmany),"howmany");
			moderatorInteractiveDialog(getResources().getString(R.string.grocery_voice_howmany)
					, 4
					,getResources().getString(R.string.grocery_voice_howmany)
					,"howmany", 9);	
			nowstate = GroceryVoiceStateEnum.SEARCH_NEEDNUMBER ;
		}
		else if (data.size() <10 )
		{
			testTV.setText(getResources().getString(R.string.grocery_voice_lookingfor));
			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_lookingfor),"lookingfor");
			nowstate = GroceryVoiceStateEnum.SEARCH_HASRESULT ;
		}
		else
		{
			testTV.setText(getResources().getString(R.string.grocery_voice_moredetail));
			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_moredetail),"moredetail");
			nowstate = GroceryVoiceStateEnum.SEARCH_NONE ;
		}
		
	}
	public void chooseNumber(String strValue)
	{
		Log.d("andy","choosenumber:"+ strValue );
		int itemNumber ;
		if (strValue == null || ("").equals(strValue))
		{
			//milky speak : ???
			return ;			
		}
		itemNumber = parseNumber(strValue) ;
		if (itemNumber < 0)
		{
			//milky speak : ???
			return ;
		}
		if (nowstate == GroceryVoiceStateEnum.SEARCH_HASRESULT
				&& data.get(itemNumber-1) != null)
		{
			chooseDataId = itemNumber-1 ;
			Log.d("andy","choose:"+ data.get(chooseDataId).getSku() );
			testTV.setText(getResources().getString(R.string.grocery_voice_howmany));
			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_howmany),"howmany");
			nowstate = GroceryVoiceStateEnum.SEARCH_NEEDNUMBER ;
		}
		else
		{
			//milky speak : ???
		}
	}
	public void setKeyword(String strValue)
	{
		Log.d("andy","setkeyword:"+ strValue );
		strValue = strValue.replace(" ", "") ;
		if (nowstate == GroceryVoiceStateEnum.SEARCH_NONE || nowstate == GroceryVoiceStateEnum.SEARCH_MUTLIKEYWORD)
		{
			keyword.add(strValue);
			searchByKeyword();
		}
		else if (nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_START) >0 
				&& nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_END) <0)
		{
			nowstate = GroceryVoiceStateEnum.SEARCH_NONE ;
			keyword.clear();
			keyword.add(strValue);
			searchByKeyword();
		}
		else
		{
			//milky speak ???
		}
	}
	public void chooseQuantity(String strValue)
	{
		Log.d("andy","choosequantity:"+ strValue );
		int itemNumber ;
		if (strValue == null || ("").equals(strValue))
		{
			//milky speak : ???
			return ;			
		}
		itemNumber = parseNumber(strValue) ;
		if (itemNumber < 0)
		{
			//milky speak : ???
			return ;
		}
		if (nowstate == GroceryVoiceStateEnum.SEARCH_NEEDNUMBER)
		{
			// add to cart animation
			
			new AddCartTask(mCartId, data.get(chooseDataId).getId(), itemNumber).execute();
			Log.d("andy","addcart:"+ data.get(chooseDataId).getSku() + ",Qty:" + itemNumber );
			
//			testTV.setText(getResources().getString(R.string.grocery_voice_anythingelse));
//			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_anythingelse),"anythingelse");
//			
//			// clean view 
//			keyword.clear() ;
//			chooseDataId = 0 ;
//			nowstate = GroceryVoiceStateEnum.SEARCH_NONE ;
		}
		else
		{
			//milky speak : ???
		}
	}
	public void lastSearch()
	{
		Log.d("andy","lastsearch");
		if (nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_START) >0 
				&& nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_END) <0)
		{
			if (keyword.size() > 1)
			{
				keyword.remove( keyword.size()-1 );
				
			}
			searchByKeyword();
		}
		else
		{
			resetSearch();
		}			
	}
	public void resetSearch()
	{
		Log.d("andy","resetsearch");
		if (nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_START) >0 
				&& nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_END) <0)
		{
			// clean view
			keyword.clear() ;
			nowstate = GroceryVoiceStateEnum.SEARCH_NONE ;
			testTV.setText("Hi " + CFG.getNickName() + getResources().getString(R.string.grocery_voice_whatdoyouwant));
			//MilkBoxTTS.speak("Hi " + CFG.getNickName() + getResources().getString(R.string.grocery_voice_whatdoyouwant),"whatdoyouwant");
			
			moderatorInteractiveDialog("Hi " + CFG.getNickName() + getResources().getString(R.string.grocery_voice_whatdoyouwant)
										, 4
										,"Hi " + CFG.getNickName() + getResources().getString(R.string.grocery_voice_whatdoyouwant),
										"whatdoyouwant", 9);	
		}
		
		else
		{
			//milky speak ???
		}		
	}
	public void backToShop()
	{
		Log.d("andy","backtoshop");
		if (nowstate.compareTo(GroceryVoiceStateEnum.BILL_START) >0 
				&& nowstate.compareTo(GroceryVoiceStateEnum.BILL_END) <0)
		{
			endBillState(GroceryVoiceStateEnum.SEARCH_NONE) ;
		}
		else
		{
			//milky speak ???
		}
	}
	public void myCart()
	{
		Log.d("andy","mycart");
		if (nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_START) >0 
				&& nowstate.compareTo(GroceryVoiceStateEnum.SEARCH_END) <0)
		{
			endSearchState();
		}
		
		else
		{
			//milky speak ???
		}			
		
	}
	/*public void dropNumber()
	{
		Log.d("andy","dropnumber");
		
	}*/
	public void dropNumber(String strValue)
	{
		Log.d("andy","dropnumber"+strValue );
		if ( nowstate == GroceryVoiceStateEnum.BILL_NONE )
		{
			int itemNumber ;
			if (strValue == null || ("").equals(strValue))
			{
				//milky speak : ???
				return ;			
			}
			itemNumber = parseNumber(strValue) ;
			if (itemNumber < 0 || itemNumber > billPageNumber)
			{
				//milky speak : ???
				return ;
			}
			billSelectItem = (billNowPage-1)*billPageNumber + itemNumber ;
			Product product = mList.get( billSelectItem ) ;
			new DeleteItemTask(mActivity, deleteSuccessListener, mCartId, product);
		}
		
		else
		{
			//milky speak ???
		}			
	}
	public void nextBill()
	{
		Log.d("andy","nextBill");
				
	}
	public void prevBill()
	{
		Log.d("andy","prevBill");
				
	}
	public void creditcard()
	{
		Log.d("andy","creditcard");
		if ( nowstate == GroceryVoiceStateEnum.BILL_NONE )
		{
			endBillState(GroceryVoiceStateEnum.SETTING_NONE) ;
		}
		else
		{
			//milky speak ???
		}
	}
	public void modifyFinish()
	{
		Log.d("andy","modifyfinish");
		if ( nowstate == GroceryVoiceStateEnum.SETTING_NONE )
		{
			endSettingState(GroceryVoiceStateEnum.FINISH) ;
		}
		else
		{
			//milky speak ???
		}		
	}
	private int parseNumber(String strValue)
	{
		if (strValue == null || ("").equals(strValue))
		{
			//milky speak : ???
			return -1;			
		}
		int itemNumber ;
		
		if ("one".equals(strValue))
			itemNumber = 1 ;
		else if ("two".equals(strValue))
			itemNumber = 2 ;
		else if ("too".equals(strValue))
			itemNumber = 2 ;
		else if ("three".equals(strValue))
			itemNumber = 3 ;
		else if ("four".equals(strValue))
			itemNumber = 4 ;
		else if ("five".equals(strValue))
			itemNumber = 5 ;
		else if ("six".equals(strValue))
			itemNumber = 6 ;
		else if ("seven".equals(strValue))
			itemNumber = 7 ;
		else if ("eight".equals(strValue))
			itemNumber = 8 ;
		else if ("nine".equals(strValue))
			itemNumber = 9 ;
		else if ("zero".equals(strValue))
			itemNumber = 0 ;
		else
			itemNumber = Integer.parseInt(strValue);

		return itemNumber;
	}
	private void openProductToCardWithNumber(String strValue)
	{
		// search.php product
		new GrocerySearchTask(mActivity, productSearchSuccessListener, strValue);
	}
	GrocerySearchTask.OnSucessListener productSearchSuccessListener = new GrocerySearchTask.OnSucessListener()
	{
		@Override
		public void success(ProductResult mProductResult)
		{
			ArrayList<Product> pList = mProductResult.getList();
			searchReturn(pList) ;
		}
		@Override
		public void fail(int failCode)
		{
			// TODO WRONG
			
			ArrayList<Product> pList = new ArrayList<Product>();
			searchReturn(pList) ;
		}
	};
	
	class AddCartTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode
	{

		String cartId;
		String productId;
		int quantity;

		AddCartTask(String cartId, String productId, int quantity) {
			this.cartId = cartId;
			this.productId = productId;
			this.quantity = quantity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... params) {
			
			try {
				String response = RestClient.get(CFG.API_GROCERY_PATH + "productInfo.php?id=" + productId);
				RestResult rr = GroceryApiParser.parserRestResult(response);
				if (rr.getCode() != OK) {
					return ERROR;
				}
				
				Product product = GroceryApiParser.parserProduct(new JSONObject(rr.getData()));				
				if(product.getId()==null) {
					return PRODUCT_IS_NOT_EXIST;
				}
				
			}catch(Exception e) {
				return PRODUCT_IS_NOT_EXIST;
			}
			
			try {
				
				RpcClient client = CFG.getRpcClient();
				Map<String, String> products = new HashMap<String, String>();
				products.put("product_id", productId);
				products.put("qty", Integer.toString(quantity));
				Boolean success = client.cartProductAdd(cartId, products);
				if (false == success) {
					return ERROR;
				}

				return OK;

			} catch (Exception e) {
//				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();
			if (result == OK)
			{
//				mActivity.showOkMsg(R.string.msg_add_cart_success);
				addCartReturn();
			}
			if (result == ERROR)
			{
//				mActivity.showMsg(R.string.fail);
				//milky speak ???
			}
			if(result==PRODUCT_IS_NOT_EXIST)
			{
//				mActivity.showMsg(R.string.msg_product_is_not_exist);
				//milky speak ???
			}
		}
	}
	GroceryInitialTask.OnSucessListener initSuccessListener = new GroceryInitialTask.OnSucessListener()
	{
		@Override
		public void success(String cartId, String storeId)
		{
			if (cartId == null)
			{
				mActivity.showMsg(R.string.msg_no_shopping_cart_id);
				return;
			}
			if (storeId == null)
			{
				mActivity.showMsg(R.string.msg_illegal_store);
				return;
			}
			mCartId = cartId;
			mStoreId = storeId;
			Log.d("andy","LOAD_SUCESS");
		}
	};
	
	class LoadCartTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String cartId;

		LoadCartTask(String id) {
			cartId = id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {

				RpcClient client = CFG.getRpcClient();
				HashMap<?,?> hm = (HashMap<?,?>) client.cartInfo(cartId);
				Object[] list = (Object[]) hm.get("items");
				
				if(list.length==0) {
					return NO_DATA;
				}
				
				HashMap<?,?> row;
				Product product;
				mList.clear();
				mTotal = 0;
				double subtotal;
				for (int i = 0; i < list.length; i++) {
					row = (HashMap<?,?>) list[i];
					product = new Product();
					product.setId(row.get("product_id").toString());
					product.setName(row.get("name").toString());
					product.setPrice(Double.parseDouble(row.get("price").toString()));
					product.setQty(Double.parseDouble(row.get("qty").toString()));
					subtotal = Double.parseDouble(row.get("row_total").toString());
					mTotal += subtotal;
					product.setSubtotal(subtotal);
					mList.add(product);
				}

				return OK;

			} catch (Exception e) {
//				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();

			if (isCancelled()) {
				return;
			}
			
			if (result == OK) {
				mAdapter.setData(mList);
				mAdapter.notifyDataSetChanged();
//				mTotalTv.setText(Format.price(mTotal));
				Log.d("andy","load cart SUCESS");
				checkCartReturn() ;
			}
			
			if(result==NO_DATA) {
//				mActivity.showMsg(R.string.no_data);
				//milky speak : ???
			}
			
		}
	}
	DeleteItemTask.OnSucessListener deleteSuccessListener = new DeleteItemTask.OnSucessListener() {
		@Override
		public void success(Product product) {		
			mList.remove(billSelectItem);
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
			mTotal = mTotal - product.getSubtotal();
//			mTotalTv.setText(Format.price(mTotal));
//			mActivity.showOkMsg(R.string.msg_the_item_was_deleted);
			
			// total price
//			testTV.setText(getResources().getString(R.string.grocery_voice_howmany));
//			MilkBoxTTS.speak(getResources().getString(R.string.grocery_voice_howmany),"howmany");
			
		}
	};
}
