package com.ehx.actionbarframe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehx.actionbarframe.R;
import com.ehx.actionbarframe.model.DrawerItem;

import java.util.List;


/**
 * Created by ehanxuan on 15/4/16.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
    private LayoutInflater inflater;
    private int mSelectPosition = 0;
    private static final int DIVIDE_POSITION = 3;

    public DrawerAdapter(Context context, List<DrawerItem> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.main_navdrawer_item, null);
            holder.image = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.divide = convertView.findViewById(R.id.divide_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int iconId = getItem(position).getIconId();
        int titleId = getItem(position).getTitleId();

        if (iconId != 0) {
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setImageResource(iconId);
        } else {
            holder.image.setVisibility(View.INVISIBLE);
            if (position == DIVIDE_POSITION) {
                holder.divide.setVisibility(View.VISIBLE);
            } else {
                holder.divide.setVisibility(View.GONE);
            }
        }
        holder.title.setText(titleId);
        return convertView;
    }

//    private View makeNavDrawerItem(final int itemId, ViewGroup container) {
//        boolean selected = getSelfNavDrawerItem() == itemId;
//        int layoutToInflate = 0;
//        layoutToInflate = R.layout.main_navdrawer_item;
//        View view = activity.getLayoutInflater().inflate(layoutToInflate, container, false);
//
//        if (isSeparator(itemId)) {
//            // we are done
//            UIUtils.setAccessibilityIgnore(view);
//            return view;
//        }
//
//        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
//        TextView titleView = (TextView) view.findViewById(R.id.title);
//        int iconId = itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length ? NAVDRAWER_ICON_RES_ID[itemId] : 0;
//        int titleId = itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length ? NAVDRAWER_TITLE_RES_ID[itemId] : 0;
//
//        // set icon and text
//        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
//        if (iconId > 0) {
//            iconView.setImageResource(iconId);
//        }
//        titleView.setText(context.getString(titleId));
//
//        formatNavDrawerItem(view, itemId, selected);
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onNavDrawerItemClicked(itemId);
//            }
//        });
//
//        return view;
//    }

    private class ViewHolder {
        ImageView image;
        TextView title;
        View divide;
    }
}
