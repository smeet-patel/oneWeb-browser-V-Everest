package com.example.smeet.oneweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.widget.TextView;

import java.util.List;

public class ListViewCustomAdapter extends BaseAdapter {

    //The list of messages to be displayed
    private List<History> itemList;

    //An object that can create/inflate views using the context/activity provided
    private LayoutInflater inflater;

    public ListViewCustomAdapter(Activity context, List<History> itemList) {
        super();

        this.itemList = itemList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Abstract methods used by parent
    public int getCount() { return itemList.size(); }
    public Object getItem(int position) {
        return itemList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    //Convenient store to be used with tagging
    public static class ViewHolder {
        TextView TimeList;
        TextView UrlList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null) {
            //No previous view, instantiate holder, inflate view and setup views of holder
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem, null);

            holder.TimeList = (TextView) convertView.findViewById(R.id.txtViewTitle);
            holder.UrlList = (TextView) convertView.findViewById(R.id.txtViewSubtitle);

            //Remember the holder inside the view
            convertView.setTag(holder);
        }
        else
            //Get holder to prepare for update with new data
            holder=(ViewHolder)convertView.getTag();

        //Update views
        History hist = (History) itemList.get(position);
        holder.TimeList.setText(hist.getTimeList());
        holder.UrlList.setText(hist.getUrlList());

        return convertView;
    }
}

