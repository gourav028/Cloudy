package cc.atspace.cloudy.cloudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;

public class MacLearn extends AppCompatActivity implements AIListener {
    AIService aiService ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_learn);
        final AIConfiguration config = new AIConfiguration("01cb1fb0acf74391b502db43cce00ce0",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }
    public void buttonClicked(View view){
        aiService.startListening();

    }
    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
