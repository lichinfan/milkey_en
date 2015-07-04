package com.kentec.milkbox.asr;

import com.kentec.milkbox.R;
import com.kentec.milkbox.grocery.GroceryVoiceMainActivity;

/**
* @author andywu
* @date 2014.03.19
* Grocery中語音模式的語音 
*/

public class GroceryVoiceASRCreater
{
	private GroceryVoiceMainActivity mGroceryVoiceMainActivity ;
	
	public void init (GroceryVoiceMainActivity Activity)
	{
		mGroceryVoiceMainActivity = Activity ;
		initASRCMDAdapter();
	}
	
	private void initASRCMDAdapter()
	{
		// 2014/03/25 Alvin : 阻擋 UserGuide voice command 在非 Main Menu 下被執行
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(	ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.UserGuide))
		{
			@Override
			public void action() {}
			
			@Override
			public void action(String strValue) {}
		});
		
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.item))
		{
			@Override
			public void action()
			{}
			@Override
			public void action(String strValue)
			{
				mGroceryVoiceMainActivity.chooseNumber(strValue) ;
			}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.findsomething))
		{
			@Override
			public void action()
			{}
			@Override
			public void action(String strValue)
			{
				mGroceryVoiceMainActivity.setKeyword(strValue) ;
			}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.iwantsomething))
		{
			@Override
			public void action()
			{}
			@Override
			public void action(String strValue)
			{
				mGroceryVoiceMainActivity.chooseQuantity(strValue) ;
			}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.lastsearch))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.lastSearch() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.reset))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.resetSearch() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.backtoshop))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.backToShop() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.mycart))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.myCart() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		/*mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.drop))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.dropNumber() ;
			}
			@Override
			public void action(String strValue)
			{}
		});*/
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.dropsomething))
		{
			@Override
			public void action()
			{}
			@Override
			public void action(String strValue)
			{
				mGroceryVoiceMainActivity.dropNumber(strValue) ;
			}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.nextbill))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.nextBill() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.prevbill))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.prevBill() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.creditcard))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.creditcard() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		/*mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.lastsetting))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.lastsetting() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.modifysetting))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.modifysetting() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.modifyaddress))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.modifyaddress() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.modifycreditcard))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.modifycreditcard() ;
			}
			@Override
			public void action(String strValue)
			{}
		});*/
		mGroceryVoiceMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryVoiceMainActivity.getResources(), R.array.modifyfinish))
		{
			@Override
			public void action()
			{
				mGroceryVoiceMainActivity.modifyFinish() ;
			}
			@Override
			public void action(String strValue)
			{}
		});
	}
}
