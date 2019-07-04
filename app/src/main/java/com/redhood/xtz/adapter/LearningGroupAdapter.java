package com.redhood.xtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redhood.xtz.R;
import com.redhood.xtz.bean.LearningGroupBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearningGroupAdapter extends RecyclerView.Adapter<LearningGroupAdapter.MyHolder> {
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;


    Context context;
    List<LearningGroupBean> datas;


    public LearningGroupAdapter(Context context, List<LearningGroupBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_group,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        LearningGroupBean bean = datas.get(position);
        holder.item_learning_group_name.setText(bean.getName());
        holder.item_learning_group_date.setText(bean.getDate());
        holder.item_learning_group_msg.setText(bean.getMsg());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView item_learning_group_name;
        TextView item_learning_group_date;
        TextView item_learning_group_msg;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            item_learning_group_name = itemView.findViewById(R.id.item_learning_group_name);
            item_learning_group_date = itemView.findViewById(R.id.item_learning_group_date);
            item_learning_group_msg = itemView.findViewById(R.id.item_learning_group_msg);
        }
    }

    public void update(List<LearningGroupBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }
}
