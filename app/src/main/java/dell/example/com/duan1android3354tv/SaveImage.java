package dell.example.com.duan1android3354tv;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.artjimlop.altex.AltexImageDownloader;

public class SaveImage {
   private Context context;
   private String url;
   private String nameFolder;

    public SaveImage(Context context, String url, String nameFolder) {
        this.context = context;
        this.url = url;
        this.nameFolder = nameFolder;
    }
    public void downloadImage(){
        final AltexImageDownloader downloader = new AltexImageDownloader(new AltexImageDownloader.OnImageLoaderListener() {
            @Override
            public void onError(AltexImageDownloader.ImageError error) {
                Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgressChange(int percent) {
                // Here you can show the percentage of progress and stuff
            }

            @Override
            public void onComplete(Bitmap result) {
                // Do whatever you want, mate
                Toast.makeText(context, "Download sucessful !", Toast.LENGTH_SHORT).show();
            }});
        downloader.writeToDisk(context, url, nameFolder);

}
    }
