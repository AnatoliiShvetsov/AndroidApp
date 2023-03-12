package com.example.httpclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

import static com.example.httpclient.utils.NetworkUtils.GenerateURL;

public class SecondActivity extends AppCompatActivity {
    private TextView textView2;
    private TextView textView;
    private EditText param1;
    private EditText param2;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private Document doc2;

    private void showResultTextView() {
        errorMessage.setVisibility(View.INVISIBLE);
    }
    private void showErrorTextView(){
        textView.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    class QueryTask2 extends AsyncTask<URL,Void, String> {
        @Override
        protected void onPreExecute(){
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            try {
                doc2 = Jsoup.connect(urls[0].toString()).get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String response){
            Elements element;
            String param1;
            String param2;
            if(doc2!=null){
                element = doc2.getElementsByTag("p");
                String text = element.text();
                param1 = text.split("_")[0];
                param2 = text.split("_")[1];
                textView.setText("Температура: " + param1 + " C\u00B0");
                textView2.setText("Напряжение: " + param2 + " V");
                showResultTextView();
            }
            else{
                showErrorTextView();
            }
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        param1 = findViewById(R.id.param1);
        param2 = findViewById(R.id.param2);
        textView = findViewById(R.id.textView);
        errorMessage = findViewById(R.id.tv_error_message);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        textView2 = findViewById(R.id.textView2);
        Button button2 = findViewById(R.id.button2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        button2.setOnClickListener(v -> {
            URL GeneratedURL;
            GeneratedURL = GenerateURL(param1.getText().toString(),param2.getText().toString());
            new QueryTask2().execute(GeneratedURL);

        });




    }
}