package com.example.layoutservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.layoutservice.Fragment.FragmentBanner;
import com.example.layoutservice.Models.Banner;
import com.example.layoutservice.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter{

    private FragmentBanner mContext;
    private List<Banner> mListBanner;

    public BannerAdapter(FragmentBanner mContext, List<Banner> mListBanner) {
        this.mContext = mContext;
        this.mListBanner = mListBanner;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.items_banner,container,false);
        ImageView imageView = view.findViewById(R.id.img_banner);

        Banner banner = mListBanner.get(position);
        if(banner != null){
            Glide.with(mContext).load(banner.getResourceId()).into(imageView);
        }

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(mListBanner != null) {
            return mListBanner.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
