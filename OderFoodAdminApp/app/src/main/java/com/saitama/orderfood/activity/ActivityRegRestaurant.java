package com.saitama.orderfood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.saitama.orderfood.R;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.task.RestaurantCloseTask;
import com.saitama.orderfood.task.RestaurantRegisterTask;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.PicassoCircleTransformation;
import com.saitama.orderfood.utils.RealPathUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ActivityRegRestaurant extends AppCompatActivity {
    public static final int REQUEST_CODE_LOCATION = 0x9345;
    public static final int REQUEST_CODE_PICK_IMAGE = 0x9346;
    private Uri uri;
    private Long id;
    private String avatar;
    private Integer status;

    private TextInputEditText txtRegResName, txtRegResLocation;
    private ImageView imgRegResAvatar;
    private Button btnRegResSubmit, btnCloseRestaurant;
    private TextView txtHeaderRegRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_restaurant);

        findView();
        initView();
        chooseLocation();
        chooseAvatar();
        submit();
        closeRestaurant();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            Picasso
                    .get().load(uri)
                    .resize(150, 150)
                    .centerCrop()
                    .transform(new PicassoCircleTransformation())
                    .into(imgRegResAvatar);
        }

        if (requestCode == REQUEST_CODE_LOCATION) {
            if (resultCode == Activity.RESULT_CANCELED) {
                txtRegResLocation.setError("Chưa chọn vị trí");
            } else {
                final String latitude = data.getStringExtra(ActivityMap.LATITUDE);
                final String longitude = data.getStringExtra(ActivityMap.LONGITUDE);
                txtRegResLocation.setText(latitude + "-" + longitude);
            }
        }
    }

    /**
     * Tìm kiếm view - findViewById()
     */
    private void findView() {
        btnCloseRestaurant = findViewById(R.id.btn_close_restaurant);
        this.txtHeaderRegRes = findViewById(R.id.txt_header_reg_res);
        this.txtRegResName = findViewById(R.id.txt_reg_res_name);
        this.txtRegResLocation = findViewById(R.id.txt_reg_res_location);
        this.imgRegResAvatar = findViewById(R.id.img_reg_res_avatar);
        this.btnRegResSubmit = findViewById(R.id.btn_reg_res_submit);
    }

    /**
     * Khởi tạo màn  hình
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        RestaurantModel model = SharedPrefsUtil.getInstance().get(ContainsUtil.RESTAURANT_CLIENT, RestaurantModel.class);
        if (model != null) {
            txtHeaderRegRes.setText("Cập nhật thông tin");
            txtRegResName.setText(model.getName());
            txtRegResLocation.setText(model.getAddress());
            Picasso
                    .get()
                    .load(ContainsUtil.SERVICE_URL + model.getAvatar())
                    .resize(150, 150)
                    .centerCrop()
                    .transform(new PicassoCircleTransformation())
                    .error(R.drawable.ic_food_bank)
                    .into(imgRegResAvatar);
            id = model.getId();
            avatar = model.getAvatar();
            status = model.getStatus();
            btnRegResSubmit.setText("Cập nhật");
            if (model.getStatus() == 0)
                btnCloseRestaurant.setText("Đóng cửa");
            else
                btnCloseRestaurant.setText("Mở cửa");
        } else
            btnCloseRestaurant.setVisibility(View.GONE);
    }

    /**
     * Đóng cửa hàng
     */
    private void closeRestaurant() {
        btnCloseRestaurant.setOnClickListener(v -> {
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("status", btnCloseRestaurant.getText().toString().contains("Đóng") ? 1 + "" : 0 + "")
                    .build();
            RestaurantCloseTask closeTask = new RestaurantCloseTask(this);
            closeTask.execute(body);
        });
    }

    /**
     * Mở activity Google Map để chọn vị trí quán ăn
     */
    private void chooseLocation() {
        txtRegResLocation.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityMap.class);
            startActivityForResult(intent, REQUEST_CODE_LOCATION);
        });
    }

    /**
     * Mở fileChooser để chọn ảnh đại diện cho quán ăn
     */
    private void chooseAvatar() {
        imgRegResAvatar.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh đại diện"), REQUEST_CODE_PICK_IMAGE);
        });
    }

    /**
     * Submit đăng kí quán ăn
     */
    private void submit() {
        btnRegResSubmit.setOnClickListener(v -> {
            if (uri == null && avatar == null) {
                Toast.makeText(this, "Vui lòng chọn ảnh đại diện quán ăn", Toast.LENGTH_SHORT).show();
                return;
            }
            RestaurantModel restaurantModel = new RestaurantModel();
            restaurantModel.setName(txtRegResName.getText().toString());
            restaurantModel.setAddress(txtRegResLocation.getText().toString());
            if (isValid(restaurantModel)) {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("id", id == null ? "" : String.valueOf(id))
                        .addFormDataPart("avatar", avatar == null ? "" : avatar)
                        .addFormDataPart("name", restaurantModel.getName())
                        .addFormDataPart("address", restaurantModel.getAddress())
                        .addFormDataPart("status", status == null ? "0" : String.valueOf(status));
                if (uri != null) {
                    File file = new File(RealPathUtil.getRealPath(this, uri));
                    RequestBody requestBodyFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                    builder.addFormDataPart("file", file.getName(), requestBodyFile);
                }
                RequestBody requestBody = builder.build();
                RestaurantRegisterTask task = new RestaurantRegisterTask(this);
                task.execute(requestBody);
            }
        });
    }

    /**
     * Validate form đăng kí quán ăn
     */
    private boolean isValid(RestaurantModel restaurantModel) {
        boolean result = true;
        if (restaurantModel.getName().length() == 0) {
            txtRegResName.setError("Vui lòng điền tên quán ăn");
            result = false;
        }
        if (restaurantModel.getAddress().length() == 0) {
            txtRegResLocation.setError("Vui lòng chọn vị trí quán ăn");
            result = false;
        }
        return result;
    }
}