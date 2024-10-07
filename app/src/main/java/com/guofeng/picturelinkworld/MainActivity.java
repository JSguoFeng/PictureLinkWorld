package com.guofeng.picturelinkworld;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;

import com.guofeng.appcore.CreateView;
import com.guofeng.appcore.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.picturelinkworld.R;
import com.guofeng.appcore.Thumbnails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPermission(this);
        }
        GridLayout gridLayout = findViewById(R.id.image_grid);
        ContentResolver contentResolver = this.getContentResolver();
        List<Image> images = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DURATION,
                MediaStore.Images.Media.SIZE
        };
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        assert cursor != null;
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        int idColumn =  cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DURATION);
        int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
        while(cursor.moveToNext()){
            String name = cursor.getString(nameColumn);
            long id = cursor.getLong(idColumn);
            String path = cursor.getString(pathColumn);
            int duration = cursor.getInt(durationColumn);
            int size = cursor.getInt(sizeColumn);
            Uri contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            images.add(new Image(name,path,contentUri,duration,size));
        }
        FrameLayout frameLayout = findViewById(R.id.display_image);
        ImageView imageView = findViewById(R.id.this_image);
        CreateView createView = new CreateView(this, frameLayout, imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static void getPermission(Activity activity) {
//      检查权限
        int readPermissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readImagesPermissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES);
//        检查是否已经有该权限，没有才去申请
//        PackageManager.PERMISSION_GRANTED--->有
//        PackageManager.PERMISSION_DENIED---->无
        if (readPermissionCheck != PackageManager.PERMISSION_GRANTED || writePermissionCheck != PackageManager.PERMISSION_GRANTED||readImagesPermissionCheck!=PackageManager.PERMISSION_GRANTED) {
//            将这些权限添加到数组中
            String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            通过ActivityCompat.requestPermissions()方法申请权限
            ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    0);
        }
    }
}