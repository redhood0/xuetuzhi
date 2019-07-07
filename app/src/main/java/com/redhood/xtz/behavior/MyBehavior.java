package com.redhood.xtz.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.NestedScrollingChildHelper;

public class MyBehavior extends CoordinatorLayout.Behavior<ImageView> {

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        Log.d("onDependentViewChanged", "onDependentViewChanged: >>>>>>>>>>>>>>>>");
        child.setX(dependency.getX());
        child.setY(dependency.getY() + 300);
        com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior scrollingViewBehavior;

        return true;
    }
}
