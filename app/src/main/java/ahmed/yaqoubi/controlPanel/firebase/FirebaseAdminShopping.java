package ahmed.yaqoubi.controlPanel.firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;

import ahmed.yaqoubi.controlPanel.callback.CallBack;
import ahmed.yaqoubi.controlPanel.callback.CallBackCategory;
import ahmed.yaqoubi.controlPanel.callback.CallBackProduct;
import ahmed.yaqoubi.controlPanel.model.Category;
import ahmed.yaqoubi.controlPanel.model.Product;
import ahmed.yaqoubi.controlPanel.model.User;
import ahmed.yaqoubi.controlPanel.callback.CallBackProduct;

public class FirebaseAdminShopping {
    private Activity activity;
    private static FirebaseAdminShopping instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private String userId;
    private FirebaseUser currentUser;
    private DatabaseReference shopReference;
    private DatabaseReference adminReference;

    private FirebaseAdminShopping(Activity activity, String userId) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
        this.rootNode = FirebaseDatabase.getInstance();
        this.userId = userId;
        this.currentUser = mAuth.getCurrentUser();
        shopReference = rootNode.getReference().child("main").child("shop").child("category");
        adminReference = rootNode.getReference("control").child("user").child(currentUser.getUid());

    }


    public static FirebaseAdminShopping getInstance(Activity activity, String userId) {
        if (instance == null) {
            instance = new FirebaseAdminShopping(activity, userId);

        }
        return instance;
    }


    public void addProduct(Product product) {
        adminReference.child("myProduct").child(product.getId() + "").setValue(product.getId());
        shopReference.child(product.getCategory()).child("product").child(product.getId()).setValue(product);

    }

    public void getCategory(CallBackCategory callBackCategory) {
        shopReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<Category> categories = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        categories.add(dataSnapshot.child("info").getValue(Category.class));

                    } catch (Exception e) {

                    }
                }
                callBackCategory.getCategories(categories);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void updateProduct(Product product) {
        shopReference.child(product.getCategory()).child("product").child(product.getId()).setValue(product);

     }

    public void deleteProduct(Product product) {
        adminReference.child("myProduct").child(product.getId() + "").removeValue();
        shopReference.child(product.getCategory()).child(product.getId()).removeValue();

    }

    public void addCategory(Category category) {
        shopReference.child(category.getId()).child("info").setValue(category);
    }

    public void getMyProducts(CallBackProduct callBack) {
        List<String> productId = new ArrayList<>();
        adminReference.child("myProduct").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    productId.add(d.getKey());
                }
                shopReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Product> productList = new ArrayList<>();
                        for (String s : productId) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                for (DataSnapshot snapshot1 : dataSnapshot.child("product").getChildren()) {
                                    if (s.equals(snapshot1.getKey())) {
                                        productList.add(snapshot1.getValue(Product.class));

                                    }
                                }

                            }

                        }
                        callBack.getProducts(productList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}
