package com.example.leadcrm.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.leadcrm.R;

import java.util.ArrayList;
import java.util.List;

public class TopicSpinnerAdapter extends ArrayAdapter<Topic> {

    private static final int RESOURCE = R.layout.layout_spinner_general_item;

    private boolean errorState = false;

    private final List<Topic> mTopicList;
    private final LayoutInflater mLayoutInflater;
    private Context mContext;

    public TopicSpinnerAdapter(@NonNull Context context) {
        super(context, RESOURCE);
        mContext = context;
        mTopicList = new ArrayList();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent, true);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent, false);
    }

    public Topic getItemAt(int index) {
        return mTopicList.get(index);
    }

    public void updateList(List<Topic> topicList) {
        mTopicList.clear();
        mTopicList.addAll(topicList);
        notifyDataSetChanged();
    }

    private View createItemView(int position, View convertView, ViewGroup parent, boolean isDropDown) {
        final View view = mLayoutInflater.inflate(RESOURCE, parent, false);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        ImageView ivArrow = view.findViewById(R.id.ivArrow);

        ivArrow.setVisibility(isDropDown ? View.GONE : View.VISIBLE);

        Topic spinnerItem = mTopicList.get(position);

        if (errorState) {
            if (!isDropDown) {
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.error_color));
            }
        }

        tvTitle.setText(spinnerItem.getName());

        return view;
    }

    public void setErrorState(Boolean state){
        errorState = state;
    }

    @Override
    public int getCount() {
        return mTopicList.size();
    }
}
