package com.example.fanjie.subbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fanjie on 18-2-5.
 */

/**
 * taken from http://abhiandroid.com/ui/adapter
 */
public class listadapter extends ArrayAdapter<subscription>{
    private onClickListenertext listenertext;
    private Context context;
    private int layoutResourceId;
    private ArrayList<subscription> listData = new ArrayList<subscription>();

    public listadapter(Context context, int layoutResourceId, ArrayList<subscription> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listData = data;

    }

    /**
     * taken from https://[teamtreehouse.com/community/getview-method-of-the-adapter
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View list = convertView;
        viewHolder holder = null;


        if (list == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            list = inflater.inflate(layoutResourceId, parent, false);
            holder = new viewHolder();
            holder.text = (TextView) list.findViewById(R.id.texts);
            list.setTag("holder");
        } else {
            holder = (viewHolder) list.getTag();
        }
        subscription sub = listData.get(position);
        holder.text.setText("name:"+sub.getName()+"   Date:"+sub.getDate()+"   Charge:"+sub.getPrice()+"   Comment:"+sub.getComments());

        holder.text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listenertext!=null){
                    listenertext.Clicktext(position);

                }

            }



        });

        return list;

    }
    static class viewHolder {
        TextView text;

    }

    public void setOnClickListener(onClickListenertext click1){
        this.listenertext=click1;
    }
    public interface onClickListenertext{
        void Clicktext(int position);
 
    }


}