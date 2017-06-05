package com.example.healer.ieltsvocabulary.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Button;

import com.example.healer.ieltsvocabulary.fragment.MainFragment;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

/**
 * Created by Healer on 23-May-17.
 */

public class MainAdapter extends FragmentStatePagerAdapter {
    ArrayList<Vocabulary> list = null;
    public MainAdapter(FragmentManager fm, ArrayList<Vocabulary> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("vocabulary",list.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
