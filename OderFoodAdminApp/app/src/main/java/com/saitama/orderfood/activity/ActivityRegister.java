package com.saitama.orderfood.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.saitama.orderfood.R;
import com.saitama.orderfood.api.UserAPI;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.task.RegisterTask;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.PicassoCircleTransformation;
import com.saitama.orderfood.utils.RealPathUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ActivityRegister extends AppCompatActivity {
    public static final int PICK_IMAGE = 1000;
    private TextInputEditText txtRegUsername, txtRegPassword, txtRegRePassword, txtRegName;
    private RadioGroup radioRegSex;
    private RadioButton btnRadioMale, btnRadioFemale;
    private Button btnRegSubmit;
    private ImageView imgRegAvatar;
    private Uri uri;

    private Long id;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        initView();
        chooseAvatar();
        btnRegSubmit.setOnClickListener(btnRegSubmitOnClick);
    }

    private void findView() {
        imgRegAvatar = findViewById(R.id.img_reg_avatar);
        txtRegUsername = findViewById(R.id.txt_reg_username);
        txtRegPassword = findViewById(R.id.txt_reg_password);
        txtRegRePassword = findViewById(R.id.txt_reg_repassword);
        txtRegName = findViewById(R.id.txt_reg_name);
        radioRegSex = findViewById(R.id.radio_reg_sex);
        btnRadioMale = findViewById(R.id.btn_radio_male);
        btnRadioFemale = findViewById(R.id.btn_radio_female);
        btnRegSubmit = findViewById(R.id.btn_reg_submit);
    }

    /**
     * Khởi tạo màn hình
     */
    private void initView() {
        String token = SharedPrefsUtil.getInstance().get(ContainsUtil.TOKEN_LOGIN, String.class);
        if (!token.equals("")) {
            JsonResult<UserModel> result = UserAPI.getInfo();
            if (result.getCode() == 0) {
                id = result.getData().getId();
                avatar = result.getData().getAvatar();

                Picasso.get()
                        .load(ContainsUtil.SERVICE_URL + result.getData().getAvatar())
                        .transform(new PicassoCircleTransformation())
                        .resize(100, 100)
                        .centerCrop()
                        .into(imgRegAvatar);
                txtRegUsername.setEnabled(false);
                txtRegUsername.setText(result.getData().getUsername());
                txtRegName.setText(result.getData().getName());
                radioRegSex.clearCheck();
                if (result.getData().getSex())
                    btnRadioMale.setChecked(true);
                else
                    btnRadioFemale.setChecked(true);
                btnRegSubmit.setText("Cập nhật");
            } else {
                Toast.makeText(getApplicationContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Set OnClickListener cho "btnRegSubmit"
     */
    View.OnClickListener btnRegSubmitOnClick = v -> {
        if (uri == null && avatar == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isValid()) {
            String username = txtRegUsername.getText().toString();
            String password = txtRegPassword.getText().toString();
            String name = txtRegName.getText().toString();
            String sex = ((RadioButton) findViewById(radioRegSex.getCheckedRadioButtonId())).getText().toString();

            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", id == null ? "" : String.valueOf(id))
                    .addFormDataPart("username", username)
                    .addFormDataPart("password", password)
                    .addFormDataPart("avatar", avatar == null ? "" : avatar)
                    .addFormDataPart("name", name)
                    .addFormDataPart("reg", "0")
                    .addFormDataPart("sex", sex.contains("Nam") ? "true" : "false");
            if (uri != null) {
                File file = new File(RealPathUtil.getRealPath(this, uri));
                RequestBody requestBodyFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                builder.addFormDataPart("file", file.getName(), requestBodyFile);
            }
            RequestBody body = builder.build();

            RegisterTask registerTask = new RegisterTask(ActivityRegister.this);
            registerTask.execute(body);
        }
    };

    /**
     * Chọn ảnh đại diện món ăn
     */
    private void chooseAvatar() {
        imgRegAvatar.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh món ăn"), PICK_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                Picasso.get()
                        .load(uri)
                        .resize(100, 100)
                        .centerCrop()
                        .into(imgRegAvatar);
            }
        }
    }

    private boolean isValid() {
        boolean result = true;
        if (txtRegUsername.getText().toString().length() != 10) {
            txtRegUsername.setError("SĐT không hợp lệ");
            result = false;
        }
        if (txtRegPassword.getText().toString().length() < 8) {
            txtRegPassword.setError("Mật khẩu phải từ 8 kí tự");
            result = false;
        } else if (!txtRegPassword.getText().toString().equals(txtRegRePassword.getText().toString())) {
            txtRegRePassword.setError("Mật khẩu không trùng khớp");
            result = false;
        }
        if (txtRegName.getText().toString().length() == 0) {
            txtRegName.setError("Vui lòng điền họ và tên");
            result = false;
        }

        return result;
    }
}