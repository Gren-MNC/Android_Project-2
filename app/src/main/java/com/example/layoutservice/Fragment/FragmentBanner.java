package com.example.layoutservice.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.layoutservice.Adapter.BannerAdapter;
import com.example.layoutservice.Models.Banner;
import com.example.layoutservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBanner extends Fragment {
    View view;
    private ViewPager viewPager;
    private BannerAdapter bannerAdapter;
    private Timer timer;
    private List<Banner> listBanner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        listBanner = getListBanner();
        bannerAdapter = new BannerAdapter(this, listBanner);
        viewPager.setAdapter(bannerAdapter);
        autoSlideBanner();

        return view;
    }

    private List<Banner> getListBanner(){
        List<Banner> list = new ArrayList<>();
        list.add(new Banner(R.drawable.banner_1));
        list.add(new Banner(R.drawable.banner_2));
        list.add(new Banner(R.drawable.banner_3));
        list.add(new Banner(R.drawable.banner_4));
        list.add(new Banner(R.drawable.banner_5));
        return list;
    }

    private void autoSlideBanner(){
        if(listBanner == null || listBanner.isEmpty()||viewPager == null){
            return;
        }

        if (timer ==null){
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = listBanner.size() -1;
                        if(currentItem <totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 10000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
            timer=null;
        }
    }
}
