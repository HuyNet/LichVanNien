package com.example.lichvannien;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    public ViewPageAdapter(@NonNull FragmentManager fm,int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NgayFragment();
            case 1:
                return new ThangFragment();
            case 2:
                return new MoRongFragment();
            default:
                return new NgayFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
