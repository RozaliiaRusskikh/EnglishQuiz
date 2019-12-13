package com.e.englishquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<PhrasalVerb> itemList;
    private Context context;

    public ItemAdapter(ArrayList<PhrasalVerb> itemList, Context context) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return itemList.get(i).getId();
    }

    @Override
    public View getView(final int i, View itemView, ViewGroup viewGroup) {

        View view = itemView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_verb, null);
        }

        PhrasalVerb item = (PhrasalVerb) getItem(i);
        TextView textView = view.findViewById(R.id.verb_title);
        textView.setText(item.getVerb());

        ImageView checkedImageView;
        checkedImageView = view.findViewById(R.id.imageChecked);

        checkedImageView.setVisibility(item.isKnown() ? View.VISIBLE : View.GONE);

        return view;
    }
}
