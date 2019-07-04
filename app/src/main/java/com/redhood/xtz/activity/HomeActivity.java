package com.redhood.xtz.activity;

import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.redhood.xtz.R;
import com.redhood.xtz.fragment.HomePageFragment;
import com.redhood.xtz.fragment.LearningGroupFragment;
import com.redhood.xtz.util.StatusUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView buttom_navigation_bar;
    private HomePageFragment homePageFragment;
    private LearningGroupFragment learningGroupFragment;
    private List<Fragment> fragments = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        StatusUtil.makeStatusBarTransparentAndFullContent(this);

        buttom_navigation_bar = findViewById(R.id.buttom_navigation_bar);

        showFragmentPage(homePageFragment, HomePageFragment.class);
        buttom_navigation_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                hideAllFragment(getSupportFragmentManager().beginTransaction());
                switch (menuItem.getItemId()) {
                    case R.id.home_item_homepage:
                        showFragmentPage(homePageFragment, HomePageFragment.class);
                        break;
                    case R.id.home_item_learning_group:
                        showFragmentPage(learningGroupFragment, LearningGroupFragment.class);
                        break;
                }
                return true;
            }
        });
    }

    private void showFragmentPage(Fragment fragment, Class<? extends Fragment> fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transition = fragmentManager.beginTransaction();
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                switch (fragmentClass.getSimpleName()) {
                    case "HomePageFragment":
                        homePageFragment = (HomePageFragment) fragment;
                        break;
                    case "LearningGroupFragment":
                        learningGroupFragment = (LearningGroupFragment) fragment;
                        break;
                }
                transition.add(R.id.flayout_4homechange, fragment);
                fragments.add(fragment);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            transition.show(fragment);
        }
        transition.commit();
    }

    private void hideAllFragment(FragmentTransaction transition) {
        for (Fragment fragment : fragments) {
            transition.hide(fragment);
        }
        transition.commit();
    }
}
