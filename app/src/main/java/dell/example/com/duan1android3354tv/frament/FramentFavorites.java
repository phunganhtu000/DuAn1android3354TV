package dell.example.com.duan1android3354tv.frament;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.FavoriteAdapter;
import dell.example.com.duan1android3354tv.R;
import dell.example.com.duan1android3354tv.model.Favorite;
import dell.example.com.duan1android3354tv.sqlite.DatabaseHelper;

public class FramentFavorites extends Fragment {
    private List<Favorite> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private FavoriteAdapter eAdapter;
    private DatabaseHelper databaseHelper;
    public FramentFavorites(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.fragment_favorites,container,false);
        databaseHelper=new DatabaseHelper(getContext());
        list=databaseHelper.getFavoriteAll();
        recyclerView =myView.findViewById(R.id.recycler_viewfavorite);
        eAdapter = new FavoriteAdapter(getContext(),list);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setAdapter(eAdapter);
        return myView;
    }
}
