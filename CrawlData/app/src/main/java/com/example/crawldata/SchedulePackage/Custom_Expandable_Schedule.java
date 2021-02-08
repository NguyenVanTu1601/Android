package com.example.crawldata.SchedulePackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.crawldata.R;
import com.example.crawldata.SchedulePackage.Schedule;

import java.util.HashMap;
import java.util.List;

public class Custom_Expandable_Schedule extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeader;
    private HashMap<String, List<Schedule>> listChild;

    public Custom_Expandable_Schedule(Context context, List<String> listHeader, HashMap<String, List<Schedule>> listChild) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChild = listChild;
    }


    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listHeader.get(groupPosition)).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.group_schedule,null);

        TextView txtheader = convertView.findViewById(R.id.textViewHeader);
        txtheader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Schedule sc = (Schedule) getChild(groupPosition,childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.child_schedule,null);

        TextView textKipChild = convertView.findViewById(R.id.textKipChild);
        TextView textSubjectChild = convertView.findViewById(R.id.textSubjectChild);
        TextView textRoomChild = convertView.findViewById(R.id.textRoomChild);

        textKipChild.setText("Kíp " + sc.getTimeOfDay());
        textRoomChild.setText("Phòng " + sc.getRoom());
        textSubjectChild.setText(sc.getNameClass());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
