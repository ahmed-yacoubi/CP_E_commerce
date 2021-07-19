package ahmed.yaqoubi.controlPanel.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ahmed.yaqoubi.controlPanel.R;
import ahmed.yaqoubi.controlPanel.callback.CallBackCategory;
import ahmed.yaqoubi.controlPanel.callback.CallBackProduct;
import ahmed.yaqoubi.controlPanel.firebase.FirebaseAdminShopping;
import ahmed.yaqoubi.controlPanel.model.Category;
import ahmed.yaqoubi.controlPanel.model.Product;

public class Add_ProdectActivity extends AppCompatActivity {
    ImageView add_img;
    private FirebaseAdminShopping firebaseAdminShopping;
    Spinner spinner;
    String userId;
    private EditText et_ProductName;
    private EditText et_ProductDescription;
    private EditText et_ProductPrice;
    private EditText et_ProductAmount;
    private Button addProductBtnAdd;
    private String categoryId;
    Uri uri;
    FirebaseStorage firebaseStorage;
    String tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prodect);
        userId = getSharedPreferences("main", MODE_PRIVATE).getString("id", null);
        firebaseStorage = FirebaseStorage.getInstance();

        firebaseAdminShopping = FirebaseAdminShopping.getInstance(this, userId);
        initView();
        getTokenId();

        addProductBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_ProductAmount != null && et_ProductDescription != null && et_ProductPrice != null && et_ProductName != null) {
                    String name = et_ProductName.getText().toString();
                    String price = et_ProductPrice.getText().toString();
                    String desc = et_ProductDescription.getText().toString();
                    String amount = et_ProductAmount.getText().toString();
                    if (!name.isEmpty() && !price.isEmpty() && !desc.isEmpty() && !amount.isEmpty()) {
                        Product product = new Product(name, name, desc, Integer.parseInt(amount), Double.parseDouble(price), categoryId, null, false, 3.2f, 30);
                        product.setAdminToken(tokenId);
                        firebaseAdminShopping.addProduct(product);


                        firebaseStorage.getReference("product").child(categoryId).child(product.getId()).putFile(uri);

                    }
                }
            }
        });
        firebaseAdminShopping.getCategory(new CallBackCategory() {
            @Override
            public void getCategories(List<Category> categoryList) {
                List<String> list = new ArrayList<>();
                for (Category category : categoryList) {
                    list.add(category.getName());

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_ProdectActivity.this,
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categ = (String) parent.getSelectedItem();
                categoryId = categ;
                Toast.makeText(Add_ProdectActivity.this, "" + categoryId, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });


    }

    private void getTokenId() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        tokenId = token;
                        Log.d("wwww", "getProducts: "+tokenId);

                        firebaseAdminShopping.getMyProducts(new CallBackProduct() {
                            @Override
                            public void getProducts(List<Product> productList) {
                                for (Product o :
                                        productList) {
                                    o.setAdminToken(tokenId);
                                    firebaseAdminShopping.updateProduct(o);
                                    Log.d("wwww", "getProducts: "+tokenId);
                                }

                            }
                        });
                        // Log and toast
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                add_img.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    private void initView() {
        add_img = findViewById(R.id.add_prodect_img);
        spinner = findViewById(R.id.add_product_spinner);
        et_ProductName = (EditText) findViewById(R.id.add_product_name);
        et_ProductDescription = (EditText) findViewById(R.id.add_product_Description);
        et_ProductPrice = (EditText) findViewById(R.id.add_product_price);
        et_ProductAmount = (EditText) findViewById(R.id.add_product_amount);
        addProductBtnAdd = (Button) findViewById(R.id.add_product_btnAdd);
    }
}


