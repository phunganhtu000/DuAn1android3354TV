package dell.example.com.duan1android3354tv.activity;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.ApiService;
import dell.example.com.duan1android3354tv.HitAdapter;
import dell.example.com.duan1android3354tv.R;
import dell.example.com.duan1android3354tv.RetroClient;
import dell.example.com.duan1android3354tv.adapter.Adapter_commuity;
import dell.example.com.duan1android3354tv.frament.FramentCommunity;
import dell.example.com.duan1android3354tv.frament.FramentFavorites;
import dell.example.com.duan1android3354tv.model.HDWALLPAPER;
import dell.example.com.duan1android3354tv.model.Hit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayoutcomnunity;
    private ViewPager viewpagercommunity;
    private NavigationView navigationView;
    private ProgressDialog pDialog;
    private SearchView searchView;
    private List<HDWALLPAPER> list=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        pDialog = new ProgressDialog(HomeActivity.this);
        pDialog.setMessage("Please wait a moment");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
//        Intent intent1=getIntent();
//        Bundle bundle1=intent1.getBundleExtra("b");
//        String ten=bundle1.getString("tenus");

        searchView = findViewById(R.id.mnuSeach);
        toolbar = (Toolbar) findViewById(R.id.toolbarcommunity);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        tabLayoutcomnunity = findViewById(R.id.tabLayoutcomnunity);
        viewpagercommunity = findViewById(R.id.viewpagercommunity);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        View view=navigationView.getHeaderView(0);
//        txtTenUS=view.findViewById(R.id.tenngdung);

        drawerLayout = findViewById(R.id.drawer_Layout);
//        txtTenUS.setText(ten);
//        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        FragmentManager manager = getSupportFragmentManager();
        Adapter_commuity adapter = new Adapter_commuity(manager);
        viewpagercommunity.setAdapter(adapter);
        tabLayoutcomnunity.setupWithViewPager(viewpagercommunity);
        viewpagercommunity.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutcomnunity));
        tabLayoutcomnunity.setTabsFromPagerAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                pDialog.show();
                ApiService api = RetroClient.getApiService();

                /**
                 * Calling JSON
                 */
                Call<Hit> call = api.getMyJSON();

                /**
                 * Enqueue Callback will be call when get response...
                 */
                call.enqueue(new Callback<Hit>() {
                    @Override
                    public void onResponse(Call<Hit> call, Response<Hit> response) {
                        //Dismiss Dialog
                        boolean a=true;

                        if (response.isSuccessful()) {
                            /**
                             * Got Successfully
                             */
                            list =  response.body().getHDWALLPAPER();
                            HDWALLPAPER hdwallpaper=new HDWALLPAPER();
                            for (int i = 0; i <list.size() ; i++) {
                                if (query.equalsIgnoreCase(list.get(i).getCategoryName())){
                                    hdwallpaper=list.get(i);
                                    a=true;
                                    Intent intent=new Intent(HomeActivity.this,SetWallActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("link",hdwallpaper.getWallpaperImage());
                                    bundle.putString("name",hdwallpaper.getCategoryName());
                                    intent.putExtra("bun",bundle);
                                    startActivity(intent);
                                    pDialog.dismiss();
                                    break;
                                }else {
                                    a=false;
                                }

                            }
                            if(a==false){
                                pDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "No photos you need to find", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Hit> call, Throwable t) {

                    }
                });

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case R.id.nav_help:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "tupaph06266@fpt.edu.vn", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Content");
                startActivity(Intent.createChooser(emailIntent, "Send"));
                break;
            case R.id.nav_signout:
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "Log out successfully", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.seach, menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }
}

