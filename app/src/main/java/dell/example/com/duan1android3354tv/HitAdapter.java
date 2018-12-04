package dell.example.com.duan1android3354tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import dell.example.com.duan1android3354tv.activity.SetWallActivity;
import dell.example.com.duan1android3354tv.model.HDWALLPAPER;


/**
 * Created by Abhi on 23 Sep 2017 023.
 */

public class HitAdapter extends RecyclerView.Adapter<HitAdapter.CustomViewHolder> {
    Context context;
    private List<HDWALLPAPER> hitList;

    public HitAdapter(Context context, List<HDWALLPAPER> hitList) {
        this.context = context;
        this.hitList = hitList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_list, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final HDWALLPAPER hit = hitList.get(position);
        holder.tvTitle.setText(hit.getCategoryName());
        Glide.with(context).load(hit.getWallpaperImageThumb()).apply(RequestOptions.centerCropTransform()).into(holder.imgAnh);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(context, SetWallActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("link",hit.getWallpaperImage());
                bundle.putString("name",hit.getCategoryName());
                intent.putExtra("bun",bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return hitList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        private ImageView imgAnh;
        private RelativeLayout line;

        public CustomViewHolder(View view) {
            super(view);
            line=view.findViewById(R.id.line);
        tvTitle=view.findViewById(R.id.tvTitle);
        imgAnh=view.findViewById(R.id.imgAnh);
        }
    }
}