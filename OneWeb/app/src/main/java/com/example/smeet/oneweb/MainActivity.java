package com.example.smeet.oneweb;


import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.smeet.oneweb.R.*;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private WebView webView;
    private EditText e;
    private TextView serachurl;
    private String urlto;
    private String defaulturl ="https://www.google.com";
    private SearchView searchView;
    private List<History> urlchange = new ArrayList<History>();
    private List<History> favs = new ArrayList<History>();
    private List<History> acttabs = new ArrayList<History>();
    private String WebTitle;
    private String curURL;
    private boolean curfav=false;
    private String currentLayout;
    private String urlnow;

    static final int HISOPEN = 0;
    static final int HISCLOSE = 1;
    static final int RETURNHIS = 2;
    static final int BOOKOP = 3;
    static final int BOOKDEL = 4;
    static final int TABOP = 5;
    static final int TABDEL = 6;
    static final int SETDE = 7;
    static final int CLEARHIS = 8;
    private ListViewCustomAdapter listAdapter;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
        loadDefault();
        changestateurl();
    }


    private void changestateurl() {
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                curfav=false;
                e.setText("");//clear text
                e.setHint(url);//set hint as current url and it does not interfer when new url is entered
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
                String valnow = dateFormat.format(new Date());
                urlchange.add(new History(valnow,url));
                curURL =url;
                WebTitle = view.getTitle();
                Bitmap bitmapOne =view.getFavicon();
                ImageButton imageView = (ImageButton)findViewById(id.fav_m);
//              imageView.setImageIcon(Icon.createWithBitmap(bitmapOne));
                Toast.makeText(MainActivity.this, WebTitle, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadDefault() {
        webView.loadUrl(defaulturl);
    }

    private void loadurlnow() {
        webView.loadUrl(urlnow);
    }

    private void main() {
        setContentView(layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        e = (EditText) findViewById(id.inputurl);
        serachurl = (TextView) findViewById(id.inputurl);
        webView = (WebView) findViewById(id.webview);
        webView.setWebViewClient (new WebViewClient());

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ButtonHandler bh = new ButtonHandler();
        findViewById(id.serachbtn).setOnClickListener(bh);
//        findViewById(R.id.returnHis).setOnClickListener(bh);
    }
    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                //number is guessed
                case id.serachbtn:
                    urlto= serachurl.getText().toString().trim();
                    if(urlto.startsWith("http://")||urlto.startsWith("https://")){
                        webView.loadUrl(urlto);}
                    else {
                        webView.loadUrl("https://"+urlto);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater menuInflator = getMenuInflater();

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.app_bar_search));
        searchView.clearFocus();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onQueryTextSubmit(String query) {
        urlto= query;
        if(urlto.startsWith("http://")||urlto.startsWith("https://")){
            webView.loadUrl(urlto);}
        else {
            webView.loadUrl("https://"+urlto);
        }
        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Do not use! otherwise updates every changed char
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.hist_list) {
            setContentView(layout.historypage);
            setupScreen(HISOPEN);
            return true;
        }
        if (id == R.id.tab_bnt) {
            acttabs.add(new History(WebTitle,curURL));
            setContentView(layout.tagpage);
            setupScreen(TABOP);
            return true;
        }
        if (id == R.id.fav_list){
            setContentView(layout.favspage);
            setupScreen(BOOKOP);
        }
        if (id == R.id.Back_m) {
            webView.goBack();
            return true;
        }
        if (id == R.id.forward_m) {
            webView.goForward();
            return true;
        }
        if (id == R.id.action_settings) {
            setContentView(layout.setting);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            return true;
        }
        if (id == R.id.tab_m) {
            curfav=false;
            acttabs.add(new History(WebTitle,curURL));
            Toast.makeText(MainActivity.this, acttabs.toString(), Toast.LENGTH_SHORT).show();
            webView.setWebViewClient (new WebViewClient());
            webView.loadUrl(defaulturl);
            e.setText("");e.setHint("Enter website name or url...");
            return true;
        }
        if (id == R.id.fav_m) {
            ImageButton imagefav = (ImageButton)findViewById(R.id.fav_m);
            if(!curfav){
                favs.add(new History(WebTitle,curURL));
                Toast.makeText(MainActivity.this, favs.toString(), Toast.LENGTH_SHORT).show();
            curfav=true;}
            else{favs.remove(favs.size() - 1);
                Toast.makeText(MainActivity.this, favs.toString(), Toast.LENGTH_SHORT).show();
                curfav=false;}
        return true;
        };
        return super.onOptionsItemSelected(item);
    }
    //implymenting the back menu button
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    private void setupScreen(int state) {
        ListView list = (ListView)findViewById(id.listView);

        switch (state) {
            case HISOPEN:
                setTitle("History list");
                currentLayout = "openlinkhistory";
                listAdapter = new ListViewCustomAdapter(this, urlchange);
                break;
            case HISCLOSE:
                currentLayout = "removelinkhistory";
                listAdapter = new ListViewCustomAdapter(this, urlchange);
                break;
            case RETURNHIS:
                main();
                loadprev();
                changestateurl();
                break;
            case BOOKOP:
                currentLayout = "openlinkbook";
                listAdapter = new ListViewCustomAdapter(this, favs);
                break;
            case BOOKDEL:
                currentLayout = "openlinkbookdel";
                listAdapter = new ListViewCustomAdapter(this, favs);
                break;
            case TABOP:
                currentLayout = "openlinktab";
                listAdapter = new ListViewCustomAdapter(this, acttabs);
                break;
            case TABDEL:
                currentLayout = "openlinktabdel";
                listAdapter = new ListViewCustomAdapter(this, acttabs);
                break;
            case SETDE:
                currentLayout = "set-web";
                break;
            case CLEARHIS:
                currentLayout = "clear-all";
                break;

        }

        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLayout=="openlinkhistory"){
                    urlnow = urlchange.get(position).getUrlList();
                    main();
                    loadurlnow();
                    e.setText("");e.setHint("Enter website name or url...");
                    Toast.makeText(MainActivity.this, "Opening "+urlnow , Toast.LENGTH_LONG).show();
                    changestateurl();
                }
                else if (currentLayout=="removelinkhistory"){
                    urlnow = urlchange.get(position).getUrlList();
                    urlchange.remove(position);
                    setupScreen(HISCLOSE);
                    Toast.makeText(MainActivity.this, "Deleting "+urlnow , Toast.LENGTH_LONG).show();
                }
                else if(currentLayout=="openlinkbook"){
                    urlnow = favs.get(position).getUrlList();
                    main();
                    loadurlnow();
                    e.setText("");e.setHint("Enter website name or url...");
                    Toast.makeText(MainActivity.this, "Opening "+urlnow , Toast.LENGTH_LONG).show();
                    changestateurl();
                }
                else if (currentLayout=="openlinkbookdel"){
                    urlnow = favs.get(position).getUrlList();
                    favs.remove(position);
                    setupScreen(BOOKDEL);
                    Toast.makeText(MainActivity.this, "Deleting "+urlnow , Toast.LENGTH_LONG).show();
                }
                else if(currentLayout=="openlinktab"){
                    urlnow = acttabs.get(position).getUrlList();
                    main();
                    loadurlnow();
                    e.setText("");e.setHint("Enter website name or url...");
                    Toast.makeText(MainActivity.this, "Opening "+urlnow , Toast.LENGTH_LONG).show();
                    changestateurl();
                }
                else if (currentLayout=="openlinktabdel"){
                    urlnow = acttabs.get(position).getUrlList();
                    acttabs.remove(position);
                    setupScreen(TABDEL);
                    Toast.makeText(MainActivity.this, "Deleting "+urlnow , Toast.LENGTH_LONG).show();
                }
//                else if (currentLayout=="set-web"){
//                }
//                else if (currentLayout=="clear-all"){
//                    acttabs.clear();
//                    favs.clear();
//                    urlchange.clear();
//                    Toast.makeText(MainActivity.this, "History Deleted" , Toast.LENGTH_LONG).show();
//                }
                }
            })
        ;
    }

    private void loadprev() {
        webView.loadUrl(curURL);
    }

    public void hisopen(View v) {
        setupScreen(HISOPEN);
    }

    public void hisclose(View v) {
        setupScreen(HISCLOSE);
    }

    public void returnfromhis(View v) {
        setupScreen(RETURNHIS);
    }

    public void bookopen(View v) {
        setupScreen(BOOKOP);
    }

    public void bookdel(View v) {
        setupScreen(BOOKDEL);
    }

    public void tabopen(View v) {
        setupScreen(TABOP);
    }

    public void tabdel(View v) {
        setupScreen(TABDEL);
    }

//    public void setdefault(View v) {
//        setupScreen(SETDE);
//    }
//
//    public void clearhistory(View v) {
//        setupScreen(CLEARHIS);
//    }


    //saving the state of the game
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("urlto", urlto);
        outState.putString("defaulturl", defaulturl);
        outState.putString("curURL", curURL);
        outState.putString("WebTitle", WebTitle);
        outState.putBoolean("curfav", curfav);
//        outState.putStringArrayList("urlchange", urlchange );
//        outState.putStringArrayList("favs", favs );
//        outState.putStringArrayList("acttabs", acttabs );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        urlto = savedInstanceState.getString("urlto");
        defaulturl = savedInstanceState.getString("defaulturl");
        curURL = savedInstanceState.getString("curURL");
        WebTitle = savedInstanceState.getString("WebTitle");
        curfav = savedInstanceState.getBoolean("curfav");
//        acttabs = savedInstanceState.getStringArrayList("acttabs");
//        favs = savedInstanceState.getStringArrayList("favs");
//        urlchange = savedInstanceState.getStringArrayList("urlchange ");
    }
}