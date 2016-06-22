package com.dreamworks.au_counselling;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by admin on 22/06/2016.
 */
public class Schedule_Activity extends AppCompatActivity {

    Button btn1,btn2,btn3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        setTitle("Counselling Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn1=(Button) findViewById(R.id.btn_1);
        btn2=(Button) findViewById(R.id.btn_2);
        btn3=(Button) findViewById(R.id.btn_3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request dr = new DownloadManager.Request(Uri.parse("https://www.annauniv.edu/tnea2016/SCH_2016_ACA_DAP.pdf"));
                dr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SCH_2016_ACA_DAP.pdf");
                dr.allowScanningByMediaScanner();
                dr.setNotificationVisibility(1);
                ((DownloadManager) Schedule_Activity.this.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(dr);
                Toast.makeText(Schedule_Activity.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request dr = new DownloadManager.Request(Uri.parse("https://www.annauniv.edu/tnea2016/SCH_2016_ACA.pdf"));
                dr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SCH_2016_ACA.pdf");
                dr.allowScanningByMediaScanner();
                dr.setNotificationVisibility(1);
                ((DownloadManager) Schedule_Activity.this.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(dr);
                Toast.makeText(Schedule_Activity.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request dr = new DownloadManager.Request(Uri.parse("https://www.annauniv.edu/tnea2016/SCH_2016_VOC.pdf"));
                dr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SCH_2016_VOC.pdf");
                dr.allowScanningByMediaScanner();
                dr.setNotificationVisibility(1);
                ((DownloadManager) Schedule_Activity.this.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(dr);
                Toast.makeText(Schedule_Activity.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });
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
