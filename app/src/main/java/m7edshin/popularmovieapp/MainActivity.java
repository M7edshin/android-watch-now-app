package m7edshin.popularmovieapp;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;
import m7edshin.popularmovieapp.InterfaceUtilities.FragmentPageAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.pager_sliding_tabs)
    PagerSlidingTabStrip pager_sliding_tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPageAdapter(this, getSupportFragmentManager());
        view_pager.setAdapter(fragmentPagerAdapter);
        pager_sliding_tabs.setViewPager(view_pager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.watch_now_image);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}
