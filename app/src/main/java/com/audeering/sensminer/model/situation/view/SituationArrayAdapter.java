package com.audeering.sensminer.model.situation.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.audeering.sensminer.model.situation.Situation;

import java.util.List;

/**
 * Created by MoritzTheile on 03.01.2017.
 */

public class SituationArrayAdapter extends ArrayAdapter<Situation> {

    private List<Situation> situations;
    private int resource;

    public SituationArrayAdapter(Context context, int resource, List<Situation> situations) {
        super(context, resource, situations);
        this.situations = situations;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(getContext());
        textView.setText(situations.get(position).getName());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(situations.get(position).getName());
        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
