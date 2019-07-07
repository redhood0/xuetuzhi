package com.redhood.xtz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.redhood.xtz.R;
import com.redhood.xtz.adapter.LearningGroupAdapter;
import com.redhood.xtz.bean.LearningGroupBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MsgFragment extends Fragment {
    RecyclerView recycleview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg,container,false);

        init(view);
        return view;
    }

    private void init(View v) {
        List<LearningGroupBean> learningGroupBeans = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            learningGroupBeans.add(new LearningGroupBean("","111","111",new ArrayList<>(),"111"));
        }
        recycleview = v.findViewById(R.id.recycleview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recycleview.setLayoutManager(manager);
        recycleview.setAdapter(new LearningGroupAdapter(getContext(),learningGroupBeans));
//        v.findViewById(R.id.btn).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//                    view.setX(motionEvent.getRawX()-view.getWidth()/2);
//                    final float scale = getContext().getResources().getDisplayMetrics().density;
//                    Log.d("getRawY", "onTouch: "+ view.getHeight()+"--"+scale);
//                    view.setY(motionEvent.getRawY()-view.getHeight()/2-64*scale);
//                }
//                return true;
//            }
//        });

    }


}
