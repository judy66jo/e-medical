package com.example.ncku.e_medical;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ncku on 2017/6/2.
 */

public class HospitalAdapter extends ArrayAdapter<HospitalAdapter.Item> {
    public static class Item implements java.io.Serializable {

        // 編號、醫院名稱、數量、花費、等級、距離、地址

        private String iddevice;
        private String hospital_name;
        private String quantity;
        private String cost;
        private String level;
        private String distance;
        private String address;

        private long id;

        public Item(String iddevice, String hospital_name,String quantity,String cost,String level, String distance, String address) {
            this.iddevice = iddevice;
            this.hospital_name = hospital_name;
            this.quantity = quantity;
            this.cost = cost;
            this.level = level;
            this.distance = distance;
            this.address = address;
        }

        public long getId() { return id; }
        public void setId(long id) {
            this.id = id;
        }

        public String get_hospital_name() { return hospital_name; }
        public String get_quantity() { return quantity; }
        public String get_cost() { return cost; }
        public String get_level() { return level; }
        public String get_distance() { return distance; }
        public String get_address() { return address; }

    }

    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<Item> items;

    public HospitalAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final Item item = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        TextView tv_hospital_name = (TextView) itemView.findViewById(R.id.tv_hospital_name);
        TextView tv_quantity = (TextView) itemView.findViewById(R.id.tv_quantity);
        TextView tv_cost = (TextView) itemView.findViewById(R.id.tv_cost);
        TextView tv_level = (TextView) itemView.findViewById(R.id.tv_level);
        TextView tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
        TextView tv_address = (TextView) itemView.findViewById(R.id.tv_address);

        tv_hospital_name.setText(item.get_hospital_name());
        tv_quantity.setText(item.get_quantity());
        tv_cost.setText(item.get_cost());
        tv_level.setText(item.get_level());
        tv_distance.setText(item.get_distance());
        tv_address.setText(item.get_address());

        return itemView;
    }

    // 設定指定編號的記事資料
    public void set(int index, Item item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public Item get(int index) {
        return items.get(index);
    }
}
