package dell.example.com.duan1android3354tv.activity;


import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.ImageCacher;
import dell.example.com.duan1android3354tv.R;
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
        pDialog.setMessage("Bạn đợi chút nha");
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
                    Toast.makeText(SetWallActivity.this, "Đã có ", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.insertFavorite(favorite);
                    Toast.makeText(SetWallActivity.this, "Đã thêm " + name + " vào favorite", Toast.LENGTH_SHORT).show();
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ImageView imgLogo = (ImageView) findViewById(R.id.imgSet);
                ImageCacher imageCacher = new ImageCacher(getApplicationContext(), -1);
                Bitmap bmImg = imageCacher.getBitmap("https://i.imgur.com/3HTto9K.jpg");
                File filename;
                try {
                    String path1 = android.os.Environment.getExternalStorageDirectory()
                            .toString();
                    Log.e("in save()", "after mkdir");
                    File file = new File(path1 + "/" + "");
                    if (!file.exists())
                        file.mkdirs();
                    filename = new File(file.getAbsolutePath() + "/" + name + ".jpg");
                    Log.e("in save()", "after file");
                    try (FileOutputStream out = new FileOutputStream(filename)) {
                        bmImg.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("in save()", "after outputstream closed");
                    //File parent = filename.getParentFile();
                    ContentValues image = getImageContent(filename);
                    Uri result = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
                    Toast.makeText(getApplicationContext(),
                            "File is Saved in  " + filename, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("gfjgj", e.getMessage());

                }
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

}
