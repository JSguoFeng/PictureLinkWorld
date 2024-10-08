package com.guofeng.appcore;

import static com.guofeng.tools.MyCreateTool.dpToPx;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class CreateView {
    private final Context context;
    private final FrameLayout frameLayout;
    private final ImageView imageView;
    private final ViewGroup.LayoutParams layoutParams;
    public CreateView(Context context,FrameLayout frameLayou,ImageView imageView){
        this.frameLayout = frameLayou;
        this.imageView = imageView;
        this.context = context;
        this.layoutParams = new ViewGroup.LayoutParams(dpToPx(context,100),dpToPx(context,100));
    }
    public ImageView createImageView( String path) throws IOException {
        ImageView imageView1 =new ImageView(context);
        imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView1.setScaleType(ImageView.ScaleType.CENTER);
        imageView1.setLayoutParams(layoutParams);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(context).load(path).into(imageView);
               frameLayout.setVisibility(View.VISIBLE);
            }
        });
        return imageView1;
    }

    public TextView createTextView(String uri){
        TextView textView = new TextView(context);
        textView.setWidth(dpToPx(context,100));
        textView.setHeight(dpToPx(context,100));
        textView.setText(uri);
        return textView;
    }

}
