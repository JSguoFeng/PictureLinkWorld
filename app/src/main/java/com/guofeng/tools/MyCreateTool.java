package com.guofeng.tools;

import android.content.Context;

public class MyCreateTool {
    //用来将dp单位转换成px单位
    public static  int  dpToPx(Context context, int dp){
        return (int)(dp*context.getResources().getDisplayMetrics().density+0.5f);
    }
    //将图片压缩成略缩图
}
