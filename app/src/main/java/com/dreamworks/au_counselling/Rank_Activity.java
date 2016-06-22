package com.dreamworks.au_counselling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by admin on 22/06/2016.
 */
public class Rank_Activity extends AppCompatActivity {

    ProgressDialog dialog;
    EditText edtAppNo;
    Button btnSubmit,btnLetter;
    WebView webView;
    String url = "https://www.annauniv.edu/cgi-bin/tharavarisai/varisai2016.pl?regno=", appNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_rank_activity);
        setTitle("Rank Enquiry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtAppNo = (EditText) findViewById(R.id.edt_appno);
        btnSubmit = (Button) findViewById(R.id.btnSub);
        btnLetter=(Button) findViewById(R.id.btn_letter_dwn);
        btnLetter.setVisibility(View.GONE);
        btnLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rank_Activity.this,WebViewActivity.class));
            }
        });
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(Rank_Activity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.isConnectingToInternet(Rank_Activity.this)) {
                    if (edtAppNo.getText().toString().trim().length() > 5) {
                        appNo = edtAppNo.getText().toString();
                        new RandomAsync().execute();
                    } else {
                        Toast.makeText(Rank_Activity.this,"Enter Valid Application No",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Rank_Activity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
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
            dialog = new ProgressDialog(Rank_Activity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Document document = (Document) Jsoup.connect(url.concat(appNo)).timeout(90000).get();
//                Log.i("Response", document.toString());
                Elements div = document.select("table[bordercolor=#841f27]");

                Elements div1=document.select("button[formaction=https://tnea2016online.annauniv.edu/tnea16_calletter/callletter16_be16.php]");
                Log.e("Download: ",""+div1.toString());
//                Log.i("Response", div.toString());
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
                btnLetter.setVisibility(View.GONE);
                Toast.makeText(Rank_Activity.this, "Loading Failed Try Again", Toast.LENGTH_SHORT).show();
                return;
            }
            btnLetter.setVisibility(View.VISIBLE);
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