package com.clawtros.selfcensor;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class CensorListenerActivity extends Activity {

    private SpeechRecognizer sr;
	private CensorSpeechListener listener;
	private MediaPlayer bleep;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_censor_listener);
        bleep = MediaPlayer.create(this, R.raw.beep);
        this.sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        this.listener = new CensorSpeechListener(this);
        Button button = (Button)findViewById(R.id.listen_button);
        button.setOnClickListener(new ListenButtonClickListener(this));
        sr.setRecognitionListener(listener);
    }

	@Override
	protected void onPause() {
		super.onPause();
		sr.stopListening();
	}
	
	public void restartListening() {
		Log.d("activity", "restart listening");
		sr.destroy();
		sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
		sr.setRecognitionListener(listener);
	}
	
	public void startListening() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
	}
	
	public Button getButton() {
		return (Button)findViewById(R.id.listen_button);
	}

	public void onCussDetected() {
		bleep.start();
	}
	
    public TextView getTextView() {
    	return (TextView)findViewById(R.id.words);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.censor_listener, menu);
        return true;
    }
    
}
