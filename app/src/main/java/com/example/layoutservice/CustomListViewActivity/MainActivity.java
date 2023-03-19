package com.example.layoutservice.CustomListViewActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.layoutservice.Adapter.ViewPagerAdapter;
import com.example.layoutservice.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnStop;
    private TabLayout mtabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);


        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new ViewPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("Trang chủ");
                        tab.setIcon(R.drawable.ic_home);
                        break;
                    }
                    case 1:{
                        tab.setText("Khám phá");
                        tab.setIcon(R.drawable.ic_discover);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);
            }
        });
//        btnStart = findViewById(R.id.btn_start);
//        btnStop = findViewById(R.id.btn_stop);
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickStartService();
//            }
//        });

//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickStopService();
//            }
//        });
    }
//    private void clickStopService() {
//        Intent intent = new Intent(this,MyService.class);
//        stopService(intent);
//    }
//
//    private void clickStartService() {
//        Song song = new Song("Show Me Love","MCK",R.drawable.img_music, R.raw.lethergo);
//        Intent intent = new Intent(this, MyService.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("object_song",song);
//        intent.putExtras(bundle);
//        startService(intent);
//    }
}