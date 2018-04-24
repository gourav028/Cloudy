package cc.atspace.cloudy.cloudy.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.fragment.Chat;
import cc.atspace.cloudy.cloudy.fragment.Story;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

public class QueryActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ImageButton btnSpeak;
    TextView txtSpeechInput, outputText;
    LinearLayout responseLL;
    String responseTask;
    Button responseYES, responseNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        outputText = (TextView) findViewById(R.id.outputText);
        responseLL = (LinearLayout) findViewById(R.id.response_LL_AQ);
        responseYES = (Button) findViewById(R.id.yes_response_TV_AQ);
        responseNO = (Button) findViewById(R.id.no_response_TV_AQ);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });


        outputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("ResourceType")
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase(getString(R.string.response_profile_ask))) {
                    responseLL.setVisibility(View.VISIBLE);
                    responseTask = getString(R.string.response_profile_change);
                } else if (editable.toString().equalsIgnoreCase(getString(R.string.response_profile_ask_now))) {
                    responseLL.setVisibility(View.INVISIBLE);
                    AppPreference.getInstance(QueryActivity.this).setCurrentTask("profile");
                    startActivity(new Intent(QueryActivity.this, Profile.class));
                    finish();
                } else if (editable.toString().equalsIgnoreCase(getString(R.string.response_story_ask_now))) {
                    responseLL.setVisibility(View.INVISIBLE);
                    AppPreference.getInstance(QueryActivity.this).setCurrentTask("story");
                    Intent it = new Intent(QueryActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                } else if (editable.toString().equalsIgnoreCase(getString(R.string.response_show_profile_ask_now))) {

                    responseLL.setVisibility(View.INVISIBLE);
                    AppPreference.getInstance(QueryActivity.this).setCurrentTask("profile");
                    Intent it = new Intent(QueryActivity.this, ImageDisplayFullScreen.class);
                    if (txtSpeechInput.getText().toString().contains("gaurav") || txtSpeechInput.getText().toString().contains("Gaurav")) {
                        it.putExtra("currentStoryLink", "https://firebasestorage.googleapis.com/v0/b/cloudy-e0811.appspot.com/o/profile_images%2FSVE0W6wsCGdK6xBVjwurY9vpaVJ3.jpg?alt=media&token=88757676-0074-425e-b34c-388d12e163ef");
                        Log.d("gaurav", it.toString());
                    } else if (txtSpeechInput.getText().toString().contains("prachi savdekar")) {
                        it.putExtra("currentStoryLink", "https://firebasestorage.googleapis.com/v0/b/cloudy-e0811.appspot.com/o/profile_images%2FN7yHoIDaqafts42vACFDQLdNfmw1.jpg?alt=media&token=5041d6ac-f486-4931-81e0-823cd9356c98");
                    } else if (txtSpeechInput.getText().toString().contains("pranjal") || txtSpeechInput.getText().toString().contains("pranjall")) {
                        it.putExtra("currentStoryLink", "https://firebasestorage.googleapis.com/v0/b/cloudy-e0811.appspot.com/o/profile_images%2Fod4XftloLqQV9lnk9tyxNf0lhIG3.jpg?alt=media&token=32562f55-bf3e-41a4-82e0-6499d444f791");
                    } else if (txtSpeechInput.getText().toString().contains("harsh")) {
                        it.putExtra("currentStoryLink", "https://firebasestorage.googleapis.com/v0/b/cloudy-e0811.appspot.com/o/profile_images%2Fprofile.png?alt=media&token=65c5801f-1dcd-49ea-b95b-e09f8a256986");
                    }

                    startActivity(it);
                    finish();
                }

            }
        });

        responseYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (responseTask.equalsIgnoreCase(getString(R.string.response_profile_change))) {
                    AppPreference.getInstance(QueryActivity.this).setCurrentTask("profile");
                    startActivity(new Intent(QueryActivity.this, Profile.class));
                    finish();
                }
            }
        });

    }

    /**
     * Showing google speech input dialog
     */
    public void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String userQuery = result.get(0);
                    txtSpeechInput.setText(userQuery);
                    RetrieveFeedTask task = new RetrieveFeedTask();
                    task.execute(userQuery);


                }
                break;
            }

        }
    }


    // Create GetText Metod
    public String GetText(String query) throws UnsupportedEncodingException {

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://api.api.ai/v1/query?v=20150910");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", "Bearer 934357dd8cdf42ce8827883feb3f1dac");
            conn.setRequestProperty("Content-Type", "application/json");

            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query);
            jsonParam.put("query", queryArray);
//            jsonParam.put("name", "order a medium pizza");
            jsonParam.put("lang", "en");
            jsonParam.put("sessionId", "1234567890");


            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d("karma", "after conversion is " + jsonParam.toString());
            wr.write(jsonParam.toString());
            wr.flush();
            Log.d("karma", "json is " + jsonParam);

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();


            JSONObject object1 = new JSONObject(text);
            JSONObject object = object1.getJSONObject("result");
            JSONObject fulfillment = null;
            String speech = null;
//            if (object.has("fulfillment")) {
            fulfillment = object.getJSONObject("fulfillment");
//                if (fulfillment.has("speech")) {
            speech = fulfillment.optString("speech");
//                }
//            }


            Log.d("karma ", "response is " + text);
            return speech;

        } catch (Exception ex) {
            Log.d("karma", "exception at last " + ex);
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        return null;
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            String s = null;
            try {

                s = GetText(voids[0]);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("karma", "Exception occurred " + e);
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            outputText.setText(s.toString());
            Log.d("responce", s.toString());

        }
    }


}