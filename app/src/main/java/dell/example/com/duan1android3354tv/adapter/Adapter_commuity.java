package dell.example.com.duan1android3354tv.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dell.example.com.duan1android3354tv.frament.FramentCommunity;
import dell.example.com.duan1android3354tv.frament.FramentFavorites;

public class Adapter_commuity extends FragmentStatePagerAdapter {
    public Adapter_commuity(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FramentCommunity();
                break;
            case 1:
                fragment = new FramentFavorites();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Image";
                break;
            case 1:
                title = "Favorites";
                break;
        }
        return title;
    }
}
