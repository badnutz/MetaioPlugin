// Copyright 2007-2013 metaio GmbH. All rights reserved.
package org.apache.cordova.MetaioPlugin;

import android.view.View;
import com.metaio.sdk.ARELActivity;


public class ARELViewActivity extends ARELActivity 
{

	@Override
	protected int getGUILayout() 
	{
		// Attaching layout to the activity
		return R.layout.template; 
	}
	
	public void onButtonClick(View v)
	{
		finish();
	}
	
}
