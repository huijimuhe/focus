package com.huijimuhe.focus.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.huijimuhe.focus.R;

import java.util.ArrayList;

/**
 * Created by Huijimuhe on 2016/4/18.
 * This is a part of focus
 * enjoy
 */
public class MainAdapter  extends FragmentPagerAdapter {

    private String titles[] = new String[]{"tab1","tab2"};
    private int titleResId[] = new int[]{R.drawable.ic_index,R.drawable.ic_action_list,R.drawable.ic_lock};
    private ArrayList<Fragment> fragments=new ArrayList<>();
    private Context context;

    public MainAdapter(FragmentManager fm,Context c,ArrayList<Fragment> lists) {
        super(fm);
        fragments=lists;
        context=c;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return titles[position];
        Drawable image = context.getResources().getDrawable(titleResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}