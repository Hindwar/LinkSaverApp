package com.example.rekhahindwar.applinksaver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rekha Hindwar on 7/13/2017.
 */

public class UrlFetcher extends AppCompatActivity implements View.OnClickListener {

    EditText url;
    Button fetch;
    String baseurl = null;
    String title = null;
    String description = null;
    String imgsrc = null;
    Bitmap bitmap;
    byte[] byteArray;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        url = (EditText) findViewById(R.id.url);
        fetch = (Button) findViewById(R.id.fetch);
        fetch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        baseurl = url.getText().toString();
        if(!baseurl.isEmpty() )
            new FetchURLAsyncTask().execute();
        else if(baseurl.trim().length() == 0 || baseurl == null || baseurl.isEmpty()){
            Intent i = new Intent(UrlFetcher.this, MainActivity.class);
            startActivity(i);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class FetchURLAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            StringBuffer buffer = new StringBuffer();

            try {
                Document doc = Jsoup.connect(baseurl).get();
                title = doc.title();
                description = doc.select("meta[name=description]").get(0).attr("content");
                if(description.length() > 100)
                    description = description.substring(0, 99);
                description = description+"....";

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            System.out.println("Url 1!!!! " + title + "Description : " + description);
            Bundle busket = new Bundle();
            busket.putString("url",baseurl);
            busket.putString("title",title);
            busket.putString("des",description);
            //busket.putByteArray("img", byteArray);
            Intent i = new Intent(UrlFetcher.this, MainActivity.class);
            i.putExtras(busket);
            System.out.println("Url 2!!!! " + title + "Description : " + description);
            startActivity(i);
            return null;

        }
    }
}
