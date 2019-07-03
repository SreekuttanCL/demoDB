package com.example.demodb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreList extends ArrayAdapter<TotalScore> {

    private Activity context;
    List<TotalScore> scrs;

    public ScoreList(Activity context, List<TotalScore> scrs) {

        super(context, R.layout.layout_score_list, scrs);
        this.context = context;
        this.scrs = scrs;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_score_list, null , true);

        TextView textScore = listViewItem.findViewById(R.id.textScore);

        TotalScore ts = scrs.get(position);
        textScore.setText(ts.getScore());

        return listViewItem;
    }
}
