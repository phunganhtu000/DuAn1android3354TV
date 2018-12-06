package dell.example.com.duan1android3354tv.activity;


import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.R;
import dell.example.com.duan1android3354tv.SaveImage;
import dell.example.com.duan1android3354tv.model.Favorite;
import dell.example.com.duan1android3354tv.sqlite.DatabaseHelper;

public class SetWallActivity extends AppCompatActivity {
    private ImageView imgSet;
    private TextView set;
    private TextView nameImg;
    private TextView save;
    private ProgressDialog pDialog;
    private TextView favorite;
    private List<Favorite> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setimg);
        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        pDialog = new ProgressDialog(SetWallActivity.this);
        pDialog.setMessage("Please wait a moment");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        final String link = bundle.getString("link");
        final String name = bundle.getString("name");
        save = findViewById(R.id.txtSave);
        set = findViewById(R.id.txtSet);
        imgSet = findViewById(R.id.imgSet);
        nameImg = findViewById(R.id.nameImg);
        favorite = findViewById(R.id.txtFavorite);
        Glide.with(this).load(link).apply(RequestOptions.centerCropTransform()).into(imgSet);
        nameImg.setText(name);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.show();
                final Intent intent2 = new Intent(Intent.ACTION_MAIN);
                intent2.addCategory(Intent.CATEGORY_HOME);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Glide.
                        with(SetWallActivity.this)
                        .asBitmap()
                        .load(link)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                try {
                                    WallpaperManager.getInstance(getApplicationContext()).setBitmap(resource);
                                    pDialog.dismiss();
                                    startActivity(intent2);
                                } catch (IOException e) {
                                    e.printStackTrace();

                                }
                            }

                        });

            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite();
                favorite.setName(name);
                favorite.setLink(link);
                String a = null;
                String b = "daco";
                String name1 = nameImg.getText().toString().trim();
                list2=databaseHelper.getFavoriteAll();
                for (int i = 0; i < list2.size(); i++) {
                    Favorite favorite1 = list2.get(i);
                    if (favorite1.getName().equals(name1)) {
                        a = "daco";
                    } else {
                        a = "chuaco";
                    }
                }
                if (b.equals(a)) {
                    Toast.makeText(SetWallActivity.this, "This picture is in favorites ", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.insertFavorite(favorite);
                    Toast.makeText(SetWallActivity.this, "Added " + name + " to favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage saveImage=new SaveImage(getApplicationContext(),link,getResources().getString(R.string.app_name));
                saveImage.downloadImage();
                Toast.makeText(SetWallActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
            }

            public ContentValues getImageContent(File parent) {
                ContentValues image = new ContentValues();
                image.put(MediaStore.Images.Media.TITLE, "");
                image.put(MediaStore.Images.Media.DISPLAY_NAME, name);
                image.put(MediaStore.Images.Media.DESCRIPTION, "App Image");
                image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
                image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                image.put(MediaStore.Images.Media.ORIENTATION, 0);
                image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                        .toLowerCase().hashCode());
                image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                        .toLowerCase());
                image.put(MediaStore.Images.Media.SIZE, parent.length());
                image.put(MediaStore.Images.Media.DATA, parent.getAbsolutePath());
                return image;
            }
        });
    }

    public void backimg(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}










































