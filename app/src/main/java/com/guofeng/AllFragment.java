package com.guofeng;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.picturelinkworld.R;
import com.guofeng.appcore.CreateView;
import com.guofeng.appcore.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_all, container, false);
        // Inflate the layout for this fragment
        GridLayout gridLayout = view.findViewById(R.id.image_grid);
        ContentResolver contentResolver = this.requireActivity().getContentResolver();
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
            Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            images.add(new Image(name,path,contentUri,duration,size));
        }
        FrameLayout frameLayout = this.requireActivity().findViewById(R.id.display_image);
        ImageView imageView =this.requireActivity().findViewById(R.id.this_image);
        //frameLayout.setVisibility(View.VISIBLE);
        CreateView createView = new CreateView(this.getContext(), frameLayout, imageView);
        for(int i =0;i<80;i++){
            try {
                ImageView imageViewNow = createView.createImageView(images.get(i).getPath());
                Glide.with(this).load(images.get(i).getUri()).thumbnail(Glide.with(this).load(images.get(i).getPath())).into(imageViewNow);
                gridLayout.addView(imageViewNow);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return view ;
    }
}