package com.example.rekhahindwar.applinksaver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.rekhahindwar.applinksaver.R.drawable.myrect;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout wall;
    EditText content;
    ImageButton post;
    ScrollView scroll;
    Drawable d;
    TextView textView;
    String baseurl = null;
    String title = null;
    String description = null;
    byte[] imgsrc = null;
    String info = null;
    public static final String PREFS_NAME = "LinkSaverApp";
    public static final String FAVORITES = "Favorite";
    static Activity activity;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wall);
        initialize();
        setActivity(this);
        activity = getActivity();
        list = new SharedPreference().createTextViewList(activity);
        if(list != null){
            int count = 0;
            while(count < list.size()){
                String contnt = list.get(count);
                System.out.println("Content : " + contnt);
                wall.addView(createNewTextView(contnt));
                count++;
            }
        }

        Bundle gotBasket = getIntent().getExtras();
        baseurl = gotBasket.getString("url");
        title = gotBasket.getString("title");
        description = gotBasket.getString("des");
        //imgsrc = gotBasket.getByteArray("img");
        info = baseurl +"\n" + "Title : "+title;
        if(description != null)
            info = info + "\nDescription : " + description;
        content.setText(info);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View v){
        String cntnt = content.getText().toString();
        wall.addView(createNewTextView(cntnt));
        content.setText("");
        scroll.fullScroll(ScrollView.FOCUS_DOWN);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView = new TextView(this);
        int id = View.generateViewId();
        textView.setId(id);
        lparams.gravity = Gravity.RIGHT;
        lparams.setMargins(200,10,10,0);
        textView.setLayoutParams(lparams);
        textView.setTextSize(15);

        textView.setBackgroundColor(Color.WHITE);
        textView.setText(text);
        Linkify.addLinks(textView, Linkify.WEB_URLS);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.WHITE);
        gd.setStroke(2, Color.BLACK);
        gd.setCornerRadius(20.0f);
        textView.setBackground(gd);
        textView.setPadding(10,10,10,10);
        textView.setGravity(Gravity.LEFT);
        activity = getActivity();
        new SharedPreference().addTextView(activity, text);
        //new FetchImageAsyncTask().execute();
        return textView;
    }

    private void initialize() {
        wall = (LinearLayout) findViewById(R.id.wall);
        content = (EditText) findViewById(R.id.content);
        post = (ImageButton) findViewById(R.id.post);
        scroll = (ScrollView) findViewById(R.id.scroll);
        post.setOnClickListener(this);
    }

    public Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity mContext) {
        activity = mContext;
    }
}
