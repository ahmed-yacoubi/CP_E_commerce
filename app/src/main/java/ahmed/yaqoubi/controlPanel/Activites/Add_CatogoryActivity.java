package ahmed.yaqoubi.controlPanel.Activites;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import ahmed.yaqoubi.controlPanel.R;
import ahmed.yaqoubi.controlPanel.firebase.FirebaseAdminShopping;
import ahmed.yaqoubi.controlPanel.model.Category;


public class Add_CatogoryActivity extends AppCompatActivity {
    int Image_Request_Code = 7;

    private ImageView img_CatogoryImg;
    private EditText et_CatogoryName;
    private EditText et_CatogoryDescription;
    private CheckBox check_CatogorySmall;
    private Button btn_productAdd;
    private String userId;
    private FirebaseAdminShopping firebaseAdminShopping;
    Uri uri;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__catogory);
        initView();
        userId = getSharedPreferences("main", MODE_PRIVATE).getString("id", null);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAdminShopping = FirebaseAdminShopping.getInstance(this, userId);

        btn_productAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_CatogoryName.getText().toString();
                String desc = et_CatogoryDescription.getText().toString();
                boolean isSmall = check_CatogorySmall.isChecked();
                //    public Category(String  id, String name, String desc, String  image, boolean isSmall) {
                Category category = new Category(name, name, desc, name, isSmall);
                firebaseAdminShopping.addCategory(category);
                firebaseStorage.getReference("categoryImages").child(category.getId()+".png").putFile(uri);

            }
        });
        img_CatogoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, Image_Request_Code);
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                img_CatogoryImg.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    private void initView() {
        img_CatogoryImg = (ImageView) findViewById(R.id.add_catogory_img);
        et_CatogoryName = (EditText) findViewById(R.id.add_catogory_name);
        et_CatogoryDescription = (EditText) findViewById(R.id.add_catogory_Description);
        check_CatogorySmall = (CheckBox) findViewById(R.id.add_catogory_Small);
        btn_productAdd = (Button) findViewById(R.id.add_product_btnAddCat);
    }
}