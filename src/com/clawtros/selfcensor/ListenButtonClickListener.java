package com.clawtros.selfcensor;

import android.view.View;
import android.view.View.OnClickListener;

public class ListenButtonClickListener implements OnClickListener {

	private final CensorListenerActivity activity;

	public ListenButtonClickListener(CensorListenerActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View button) {
		activity.startListening();
		button.setEnabled(false);
	}

}
