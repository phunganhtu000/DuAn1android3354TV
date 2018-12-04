package dell.example.com.duan1android3354tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import dell.example.com.duan1android3354tv.activity.SetWallActivity;
import dell.example.com.duan1android3354tv.model.Favorite;
import dell.example.com.duan1android3354tv.sqlite.DatabaseHelper;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CustomViewHolder> {
    Context context;
    private DatabaseHelper databaseHelper;
    private List<Favorite> hitList;
    public FavoriteAdapter(Context context, List<Favorite> hitList) {
        this.context = context;
        this.hitList = hitList;
    }

    @Override
    public FavoriteAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_list, parent, false);

        return new FavoriteAdapter.CustomViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.CustomViewHolder holder, int position) {
        databaseHelper=new DatabaseHelper(context);
        final Favorite hit = hitList.get(position);

        holder.tvTitle.setText(hit.getName());
        Glide.with(context).load(hit.getLink()).apply(RequestOptions.centerCropTransform()).into(holder.imgAnh);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SetWallActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("link",hit.getLink());
                bundle.putString("name",hit.getName());
                intent.putExtra("bun",bundle);
                context.startActivity(intent);

            }
        });
        holder.line.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                databaseHelper.deleteUFavorite(holder.tvTitle.getText().toString());
                hitList.remove(hit);
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                return false;
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
