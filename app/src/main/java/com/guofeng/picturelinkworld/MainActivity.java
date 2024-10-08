package com.guofeng.picturelinkworld;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.picturelinkworld.R;
import com.guofeng.AllFragment;
import com.guofeng.BookFragment;
import com.guofeng.ShareFragment;

public class    MainActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    private MyGestureListener myGestureListener;
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
        myGestureListener = new MyGestureListener();
        gestureDetector = new GestureDetector(this, myGestureListener);
        getWindow().setStatusBarColor(Color.WHITE);
        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        TextView textViewAll = findViewById(R.id.all_text);
        TextView textViewBook = findViewById(R.id.book_text);
        TextView textViewShare = findViewById(R.id.share_text);
        replaceFragment(new AllFragment());
        textViewAll.setOnClickListener(v -> {
            textViewAll.setTextColor(Color.BLUE);
            textViewBook.setTextColor(Color.BLACK);
            textViewShare.setTextColor(Color.BLACK);
            replaceFragment(new AllFragment());

        });
        textViewBook.setOnClickListener(v -> {
            textViewAll.setTextColor(Color.BLACK);
            textViewBook.setTextColor(Color.BLUE);
            textViewShare.setTextColor(Color.BLACK);
            replaceFragment(new BookFragment());
        });
        textViewShare.setOnClickListener(v -> {
            textViewAll.setTextColor(Color.BLACK);
            textViewBook.setTextColor(Color.BLACK);
            textViewShare.setTextColor(Color.BLUE);
            replaceFragment(new ShareFragment());
        });


    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.all_fragment,fragment);
        fragmentTransaction.commit();
    }
    public boolean onTouchEvent(MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                System.out.println(e1+" "+e2);
                return true;
            }
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