package com.dreamworks.au_counselling;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by admin on 21/06/2016.
 */
public class RandomRank_Activity extends AppCompatActivity {
    ProgressDialog dialog;
    EditText edtAppNo;
    Button btnSubmit;
    WebView webView;
    String url = "https://www.annauniv.edu/cgi-bin/ran/rawdata16captcha.pl?regno=", appNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_rank_activity);
        setTitle("Random Number Enquiry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtAppNo = (EditText) findViewById(R.id.edt_appno);
        btnSubmit = (Button) findViewById(R.id.btnSub);
        webView = (WebView) findViewById(R.id.webView);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAppNo.getText().toString().trim().length() > 5) {
                    appNo = edtAppNo.getText().toString();
                    new RandomAsync().execute();
                }
            }
        });

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("CC5F2C72DF2B356BBF0DA198")
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);
    }


    class RandomAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(RandomRank_Activity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Document document = (Document) Jsoup.connect(url.concat(appNo)).timeout(90000).get();
                Log.i("Response", document.toString());
                Elements div = document.select("table[bordercolor=#841f27]");
                Log.i("Response", div.toString());
                if(div.toString().isEmpty()){

                    return "<center><h2>Invalid Application Number</h2></center>";
                }
                return div.toString();
            } catch (Exception e) {

            }
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equalsIgnoreCase("1")) {
                Toast.makeText(RandomRank_Activity.this, "Loading Failed Try Again", Toast.LENGTH_SHORT).show();
                return;
            }
            webView.loadUrl("about:blank");
            webView.loadData(result, "text/html", "UTF-8");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
