package com.example.asmapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asmapi.services.APISevice;
import com.example.asmapi.services.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView lvMain;
    List<CarModel> listCarModel;
    CarAdapter carAdapter;
    FloatingActionButton themXe;
    Button btnSua,btnXoa;
    CarModel car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvMain = findViewById(R.id.listviewMain);
        themXe = findViewById(R.id.themXe);
        carAdapter = new CarAdapter(MainActivity.this,listCarModel);

        themXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        fetchCarList();


    }
    private void fetchCarList(){
        // khởi tạo service request
        HttpRequest httpRequest = new HttpRequest();
        // thực thi callAPI
        httpRequest.callAPI()
                .getCars();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APISevice.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APISevice apiService = retrofit.create(APISevice.class);
        Call<List<CarModel>> call = apiService.getCars();

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                if (response.isSuccessful()){
                    listCarModel = response.body();
                    carAdapter = new CarAdapter(MainActivity.this,listCarModel);

                    lvMain.setAdapter(carAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {

            }
        });

    }

    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm xe");
        // Inflate layout dialog
        View view = getLayoutInflater().inflate(R.layout.dialog_them,null);
        // tham chiếu các Edittext từ layout
        EditText edName = view.findViewById(R.id.edName);
        EditText edNam = view.findViewById(R.id.edNam);
        EditText edHang = view.findViewById(R.id.edHang);
        EditText edGia = view.findViewById(R.id.edGia);


        builder.setPositiveButton("Thêm",(dialog, which) -> {
            String name = edName.getText().toString().trim();
            String nam = edNam.getText().toString().trim();
            String hang = edHang.getText().toString().trim();
            String gia = edGia.getText().toString().trim();
            // kiểm tra dữ liệu
            if (name.isEmpty()|| nam.isEmpty()|| hang.isEmpty()|| gia.isEmpty()){
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            int namSX = Integer.parseInt(nam);
            double giaXe = Double.parseDouble(gia);
            // thực hiện chức năng thêm xe (có thể kết nối api tại đây)
            addXe(name,namSX,hang,giaXe);

        });
        // nút hủy
        builder.setNegativeButton("Hủy",((dialog, which) -> {
            dialog.dismiss();

        }));
        builder.setView(view);
        builder.create().show();
    }
    private void addXe(String name,int namSX,String hang,double giaXe){
        // tạo instance của httpRequest();
        HttpRequest httpRequest = new HttpRequest();
        // chuẩn bị dữ liệu lên xe
        CarModel carModel = new CarModel(name,namSX,hang,giaXe);
        // gọi api qua httpRequest
        httpRequest.callAPI().addXe(carModel).enqueue(new Callback<Response<CarModel>>() {
            @Override
            public void onResponse(Call<Response<CarModel>> call, Response<Response<CarModel>> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    Toast.makeText(MainActivity.this, "Thêm xe thành công", Toast.LENGTH_SHORT).show();
                    // cập nhật danh sách xe
                    fetchCarList();
                }else {
                    Toast.makeText(MainActivity.this, "Thêm xe thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("AddCar","error"+response.code()+" "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Response<CarModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    };
    public void showEditDialog(CarModel car){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa xe");
        // Inflate layout dialog
        View view = getLayoutInflater().inflate(R.layout.dialog_them,null);
        builder.setView(view);
        EditText edName = view.findViewById(R.id.edName);
        EditText edNam = view.findViewById(R.id.edNam);
        EditText edHang = view.findViewById(R.id.edHang);
        EditText edGia = view.findViewById(R.id.edGia);
        // hiện thị thông tin hiện tại
        edName.setText(car.getTen());
        edNam.setText(String.valueOf(car.getNamSX()));
        edHang.setText(car.getHang());
        edGia.setText(String.valueOf(car.getGia()));


        builder.setPositiveButton("cập nhật",(dialog, which) ->{
            String name = edName.getText().toString().trim();
            String nam = edNam.getText().toString().trim();
            String hang = edHang.getText().toString().trim();
            String gia = edGia.getText().toString().trim();

            if (name.isEmpty()|| nam.isEmpty()|| hang.isEmpty()|| gia.isEmpty()){
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            };
            int namSX = Integer.parseInt(nam);
            double giaXe = Double.parseDouble(gia);
            // gọi api để cập nhât
            updateXebyId(car.get_id(),name,namSX,hang,giaXe);
        } );
      builder.setNegativeButton("Hủy",(dialog, which) -> {
          dialog.dismiss();
      });

      builder.create().show();

    }
    public  void updateXebyId(String id, String name, int namSX,String hang, double giaXe){
        HttpRequest httpRequest = new HttpRequest();
        CarModel updateCar = new CarModel(id,name,namSX,hang,giaXe);

        httpRequest.callAPI().updateXebyId(id,updateCar).enqueue(new Callback<Response<CarModel>>() {
            @Override
            public void onResponse(Call<Response<CarModel>> call, Response<Response<CarModel>> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    fetchCarList();
                }else {
                    Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                };
            }

            @Override
            public void onFailure(Call<Response<CarModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteXebyId(String id){
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.callAPI().deleteXebyId(id).enqueue(new Callback<Response<CarModel>>() {
            @Override
            public void onResponse(Call<Response<CarModel>> call, Response<Response<CarModel>> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    fetchCarList();
                }else {
                    Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<CarModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}