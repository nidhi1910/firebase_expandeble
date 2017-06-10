package com.example.skylix.firebase_auth;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by He on 3/12/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private HashMap<String, List<String>> _Category;
    private List<String> _List;
    TextView parent_textview;
    public ExpandableListAdapter(Context ctx, HashMap<String, List<String>> _Category, List<String> _List) {
        this.ctx = ctx;
        this._Category = _Category;
        this._List = _List;
    }

    @Override
    public int getGroupCount() {
        return _List.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _Category.get(_List.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _List.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _Category.get(_List.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentview) {
        String group_title = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, parentview, false);
        }
        parent_textview  = (TextView)convertView.findViewById(R.id.parent_text);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        Log.d("result","group_title"+group_title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentview) {
        String child_title = (String)getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, parentview, false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
