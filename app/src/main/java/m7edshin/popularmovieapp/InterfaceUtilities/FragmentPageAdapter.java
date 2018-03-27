package m7edshin.popularmovieapp.InterfaceUtilities;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import m7edshin.popularmovieapp.Fragments.FavoriteMoviesFragment;
import m7edshin.popularmovieapp.Fragments.NowPlayingMoviesFragment;
import m7edshin.popularmovieapp.Fragments.PopularMoviesFragment;
import m7edshin.popularmovieapp.Fragments.TopRatedMoviesFragment;
import m7edshin.popularmovieapp.Fragments.UpcomingMoviesFragment;

/**
 * Created by Mohamed Shahin on 08/03/2018.
 * Pager adapter for the fragments
 */

public class FragmentPageAdapter extends FragmentPagerAdapter {

    private Context context;
    private final String[] TITLES = {"Upcoming", "Now Playing ", "Popular", "Top Rated", "Favorite"};

    public FragmentPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new UpcomingMoviesFragment();
        }else if (position == 1){
            return new NowPlayingMoviesFragment();
        }else if (position == 2){
            return new PopularMoviesFragment();
        }else if(position == 3){
            return new TopRatedMoviesFragment();
        }else if (position == 4){
            return new FavoriteMoviesFragment();
        }
       return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
