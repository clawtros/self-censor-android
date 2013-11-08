package com.clawtros.selfcensor;

import java.util.ArrayList;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

public class CensorSpeechListener implements RecognitionListener {

	private CensorListenerActivity activity;

	public CensorSpeechListener(CensorListenerActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onBeginningOfSpeech() {
		Log.d("listener", "on beginning of speech");
	}

	@Override
	public void onBufferReceived(byte[] arg0) {
		Log.d("listener", "on buffer received");

	}

	@Override
	public void onEndOfSpeech() {
		Log.d("listener", "on end of speech");
		this.activity.getButton().setText("End");
		this.activity.startListening();
	}

	@Override
	public void onError(int errorCode) {
		Log.d("listener", String.format("on error: %d", errorCode));
        TextView textView = this.activity.getTextView();
		StringBuffer buffer = new StringBuffer(textView.getText());
		buffer.append(String.format("error: %d ", errorCode));
		this.activity.getTextView().setText(buffer.toString());
		
		if (errorCode == SpeechRecognizer.ERROR_AUDIO || errorCode == SpeechRecognizer.ERROR_NO_MATCH) {
			this.activity.startListening();
		}
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) {
		Log.d("listener", "on event");
	}

	@Override
	public void onPartialResults(Bundle results) {
	}

	@Override
	public void onReadyForSpeech(Bundle results) {
		Log.d("listener", "on ready for speech");
		this.activity.getButton().setText("Ready");
	}

	@Override
	public void onResults(Bundle results) {
		Log.d("Speech", "onResults");
        TextView textView = this.activity.getTextView();
        ArrayList<String> strlist = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        StringBuffer buffer = new StringBuffer(textView.getText());
        for (int i = 0; i < strlist.size();i++ ) {
            Log.d("Speech", "result=" + strlist.get(i));
            if (strlist.get(i).contains("***")) {
            	this.activity.onCussDetected();
            }
            buffer.append(strlist.get(i));
        }
        this.activity.getTextView().setText(buffer.toString());
        this.activity.getButton().setText("Results Received");
        this.activity.startListening();
	}

	@Override
	public void onRmsChanged(float arg0) {
	}

}
