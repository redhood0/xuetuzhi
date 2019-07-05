package com.redhood.xtz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redhood.xtz.R;
import com.redhood.xtz.bean.LearningGroupBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearningGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 普通布局
    private final int TYPE_ITEM = 0;
    // 脚布局
    private final int TYPE_FOOTER = 1;

    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    Context context;
    List<LearningGroupBean> datas;


    public LearningGroupAdapter(Context context, List<LearningGroupBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_learning_group, parent, false);
            return new MyHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            LearningGroupBean bean = datas.get(position);
            myHolder.item_learning_group_name.setText(bean.getName());
            myHolder.item_learning_group_date.setText(bean.getDate());
            myHolder.item_learning_group_msg.setText(bean.getMsg());
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING:
                    footViewHolder.ll_loading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.ll_loading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    footViewHolder.ll_loading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
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

    private class FootViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llEnd;
        LinearLayout ll_loading;

        FootViewHolder(View itemView) {
            super(itemView);
            ll_loading = itemView.findViewById(R.id.ll_loading);
            llEnd = itemView.findViewById(R.id.ll_end);
        }
    }

    public void update(List<LearningGroupBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    //下拉更新状态
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据x个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


}
