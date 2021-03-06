package com.saitama.orderfood.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.saitama.orderfood.R;
import com.saitama.orderfood.api.FoodAPI;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.task.FoodEditTask;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.RealPathUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ActivityEditFood extends AppCompatActivity {

    public static final int PICK_IMAGE = 1000;
    private ImageView imgFoodEditAvatar, imgFoodEditDelete, imgFoodEditBack;
    private TextInputEditText txtFoodEditName, txtFoodEditPrice, txtFoodEditDescription;
    private Button btnFoodAddSubmit;
    private SwitchCompat switchClose;

    private Long id;
    private Double price;
    private Integer status;
    private String name, description, avatar;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        findView();
        initView();
        chooseAvatar();

        btnFoodAddSubmit.setOnClickListener(edit);
        imgFoodEditDelete.setOnClickListener(delete);
        imgFoodEditBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                Picasso.get()
                        .load(uri)
                        .resize(150, 150)
                        .centerCrop()
                        .into(imgFoodEditAvatar);
            }
        }
    }

    /**
     * T??m ki???m view - findViewById()
     */
    private void findView() {
        imgFoodEditBack = findViewById(R.id.img_food_edit_back);
        imgFoodEditDelete = findViewById(R.id.img_food_edit_delete);
        imgFoodEditAvatar = findViewById(R.id.img_food_edit_avatar);
        txtFoodEditName = findViewById(R.id.txt_food_edit_name);
        txtFoodEditPrice = findViewById(R.id.txt_food_edit_price);
        txtFoodEditDescription = findViewById(R.id.txt_food_edit_description);
        btnFoodAddSubmit = findViewById(R.id.btn_food_edit_submit);
        switchClose = findViewById(R.id.switch_close);
    }

    /**
     * Kh???i t???o m??n h??nh
     */
    private void initView() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            this.id = bundle.getLong("id");
            name = bundle.getString("name");
            avatar = bundle.getString("avatar");
            price = bundle.getDouble("price");
            description = bundle.getString("description");
            status = bundle.getInt("status");
            Picasso.get().load(ContainsUtil.SERVICE_URL + avatar)
                    .resize(150, 150).centerCrop()
                    .into(imgFoodEditAvatar);
            txtFoodEditName.setText(name);
            txtFoodEditPrice.setText(String.valueOf(price));
            txtFoodEditDescription.setText(description);
            switchClose.setChecked(status == 0);
            btnFoodAddSubmit.setText("C???p nh???t");
        } else {
            imgFoodEditDelete.setVisibility(View.GONE); //???n n??t x??a
        }
    }

    /**
     * Ch???n ???nh ?????i di???n m??n ??n
     */
    private void chooseAvatar() {
        imgFoodEditAvatar.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Ch???n ???nh m??n ??n"), PICK_IMAGE);
        });
    }

    /**
     * C???p nh???t m??n ??n
     */
    View.OnClickListener edit = v -> {
        if (uri == null && avatar == null) {
            Toast.makeText(this, "Vui l??ng ch???n ???nh ?????i di???n m??n ??n", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isValid()) {
            name = txtFoodEditName.getText().toString();
            price = Double.parseDouble(txtFoodEditPrice.getText().toString());
            description = txtFoodEditDescription.getText().toString();
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", id == null ? "" : String.valueOf(id))
                    .addFormDataPart("name", name)
                    .addFormDataPart("avatar", avatar == null ? "" : avatar)
                    .addFormDataPart("price", String.valueOf(price))
                    .addFormDataPart("description", description)
                    .addFormDataPart("status", switchClose.isChecked() ? "0" : "1");
            if (uri != null) {
                File file = new File(RealPathUtil.getRealPath(this, uri));
                RequestBody requestBodyFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                builder.addFormDataPart("file", file.getName(), requestBodyFile);
            }
            RequestBody requestBody = builder.build();
            new FoodEditTask(this).execute(requestBody);
        }

    };

    /**
     * X??a m??n ??n
     */
    View.OnClickListener delete = v -> {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", id == null ? "" : String.valueOf(id))
                .build();
        new DeleteFoodAsyncTask(this).execute(requestBody);
    };

    /**
     * Validate form ????ng k?? qu??n ??n
     */
    private boolean isValid() {
        boolean result = true;
        if (txtFoodEditName.getText().length() == 0) {
            txtFoodEditName.setError("Vui l??ng nh???p t??n m??n ??n");
            result = false;
        }
        if (txtFoodEditPrice.getText().length() == 0) {
            txtFoodEditPrice.setError("Vui l??ng nh???p gi?? m??n ??n");
            result = false;
        }
        if (txtFoodEditDescription.getText().length() == 0) {
            txtFoodEditDescription.setError("Vui l??ng ??i???n  qu??n ??n");
            result = false;
        }
        return result;
    }


    private class DeleteFoodAsyncTask extends AsyncTask<RequestBody, Void, JsonResult<String>> {
        private final ProgressDialog dialog;
        private final Activity activity;

        public DeleteFoodAsyncTask(Activity activity) {
            this.dialog = new ProgressDialog(activity);
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Vui l??ng ?????i...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JsonResult<String> doInBackground(RequestBody... requestBodies) {
            return FoodAPI.delete(requestBodies[0]);
        }

        @Override
        protected void onPostExecute(JsonResult<String> stringJsonResult) {
            super.onPostExecute(stringJsonResult);
            if (dialog.isShowing()) {
                SystemClock.sleep(1000);
                dialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(stringJsonResult.getMsg());
            if (stringJsonResult.getCode() == 0) {
                builder.setPositiveButton("OK", (dialog, which) -> {
                    activity.finish();
                });
            } else
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}