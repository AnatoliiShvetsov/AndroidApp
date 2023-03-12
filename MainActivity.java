 package com.example.httpclient;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


//import java.net.MalformedURLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static com.example.httpclient.utils.NetworkUtils.GenerateURL;
import static com.example.httpclient.utils.NetworkUtils.generateURL;
import static com.example.httpclient.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private EditText login;
    private EditText password;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private Document doc;

    private void showResultTextView() {
        textView.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }
    private void showErrorTextView(){
        textView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    class QueryTask extends AsyncTask<URL,Void, String>{
        @Override
        protected void onPreExecute(){
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                doc = Jsoup.connect(urls[0].toString()).get();
                //response = getResponseFromURL(urls[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String response){
            Elements element;
           if(doc!=null){
                element = doc.getElementsByTag("h1");
                textView.setText(element.text());

            showResultTextView();}else{
                showErrorTextView();
            }
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        textView = findViewById(R.id.textView);
        errorMessage = findViewById(R.id.tv_error_message);
        Button button = findViewById(R.id.button);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        Button button3 = findViewById(R.id.button3);


        button.setOnClickListener(v -> {
            URL generatedURL;
            generatedURL = generateURL(login.getText().toString(),password.getText().toString());
            new QueryTask().execute(generatedURL);

        });

        button3.setOnClickListener(v -> {
            Context context = MainActivity.this;
            Class destinationActivity = SecondActivity.class;
            Intent SecondActivityIntent = new Intent(context,destinationActivity);
            startActivity(SecondActivityIntent);
        });
    }
}