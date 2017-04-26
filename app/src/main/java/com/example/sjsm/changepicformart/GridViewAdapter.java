package com.example.sjsm.changepicformart;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by sjsm on 2017/4/25.
 */

public class GridViewAdapter extends BaseAdapter{

    private Context mContext;
    private List<Bitmap> mList;
    private LayoutInflater inflater = null;

    public GridViewAdapter(Context context, List<Bitmap> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item_menu, null);
            holder.ItemImage = (ImageView) view.findViewById(R.id.pic);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.ItemImage.setImageBitmap(mList.get(i));


        return view;
    }

    class ViewHolder {
        ImageView ItemImage;
    }
}
