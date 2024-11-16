package com.example.asmapi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CarAdapter extends BaseAdapter {
    List<CarModel> carModelList;
    Context context;
    public CarAdapter(Context context, List<CarModel> carModelList){
        this.context = context;
        this.carModelList = carModelList;
    }
    @Override
    public int getCount() {
        return carModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return carModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_car,parent,false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
        ImageView imgAvarta = (ImageView) rowView.findViewById(R.id.imgAvatatr);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvNamSX = (TextView) rowView.findViewById(R.id.tvNamSX);
        TextView tvHang = (TextView) rowView.findViewById(R.id.tvHang);
        TextView tvGia = (TextView) rowView.findViewById(R.id.tvGia);
        Button btnSua = (Button) rowView.findViewById(R.id.btnSua);
        Button btnXoa = (Button) rowView.findViewById(R.id.btnXoa);

        
        tvName.setText(String.valueOf(carModelList.get(position).getTen()));
        tvNamSX.setText(String.valueOf(carModelList.get(position).getNamSX()));
        tvHang.setText(String.valueOf(carModelList.get(position).getHang()));
        tvGia.setText(String.valueOf(carModelList.get(position).getGia()));

        btnSua.setOnClickListener(v -> {
            Log.d("Adapter", "Nút Sửa được bấm");
            if (context instanceof MainActivity) {
                Log.d("Adapter", "Nút Sửa được bấm trong if");
                ((MainActivity) context).showEditDialog(carModelList.get(position));
                List<CarModel> newCarList = carModelList;
                updateData(newCarList);// Truyền `car` để chỉnh sửa
            }
        });
        btnXoa.setOnClickListener(v -> {
            Log.d("Adapter", "Nút Xóa được bấm");
            if (context instanceof MainActivity) {
                ((MainActivity) context).deleteXebyId(carModelList.get(position).get_id());
                List<CarModel> newCarList = carModelList;
                updateData(newCarList);
            }
        });


        return rowView;


    }
    public void updateData(List<CarModel> newCarList){
        this.carModelList.clear();
        this.carModelList.addAll(newCarList);
        notifyDataSetChanged();
    }
}
