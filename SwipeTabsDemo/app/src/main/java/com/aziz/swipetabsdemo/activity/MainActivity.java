package com.aziz.swipetabsdemo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aziz.swipetabsdemo.R;
import com.aziz.swipetabsdemo.activity.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Tab1").setIcon(R.drawable.ic_tab_black_24dp);
        tabLayout.getTabAt(1).setText("Tab2").setIcon(R.drawable.ic_tab_black_24dp);
        tabLayout.getTabAt(2).setText("Tab3").setIcon(R.drawable.ic_tab_black_24dp);
    }
}
