package dell.example.com.duan1android3354tv.frament;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.ApiService;
import dell.example.com.duan1android3354tv.HitAdapter;
import dell.example.com.duan1android3354tv.R;
import dell.example.com.duan1android3354tv.RetroClient;
import dell.example.com.duan1android3354tv.model.HDWALLPAPER;
import dell.example.com.duan1android3354tv.model.Hit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FramentCommunity extends Fragment {
    private List<HDWALLPAPER> list=new ArrayList<>();
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private HitAdapter eAdapter;
    public  FramentCommunity(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.fragment_community,container,false);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Log.e("on", "onCreate: " );

        //Creating an object of our api interface
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
                pDialog.dismiss();

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    list =  response.body().getHDWALLPAPER();
                    recyclerView =myView.findViewById(R.id.recycler_view);
                    eAdapter = new HitAdapter(getContext(),list);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setAdapter(eAdapter);
                    Log.e("SIZE",list.size()+"");
                }
            }

            @Override
            public void onFailure(Call<Hit> call, Throwable t) {
                pDialog.dismiss();
            }
        });
        return myView;
    }
}
