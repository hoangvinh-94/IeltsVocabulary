package com.example.healer.ieltsvocabulary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.healer.ieltsvocabulary.fragment.HomeFragment;
import com.example.healer.ieltsvocabulary.fragment.LessonFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeFragment listUnits = new HomeFragment();
        LessonFragment ls = new LessonFragment();
        addFragment(listUnits);
    }
    public void addFragment(HomeFragment listUnits)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(!listUnits.isAdded()){
            try{
                ft.add(R.id.fragment_container, listUnits);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
            catch(Exception e){

            }
        }
        else{
            ft.show(listUnits);
        }
    }
}
