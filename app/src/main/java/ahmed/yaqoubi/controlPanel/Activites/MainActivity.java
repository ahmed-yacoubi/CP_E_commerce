package ahmed.yaqoubi.controlPanel.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

import ahmed.yaqoubi.controlPanel.R;
import ahmed.yaqoubi.controlPanel.callback.CallBackProduct;
import ahmed.yaqoubi.controlPanel.firebase.FirebaseAdminShopping;
import ahmed.yaqoubi.controlPanel.model.Product;


public class MainActivity extends AppCompatActivity {


     CardView Card_addpro, Card_addcat, Card_showall, Card_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Card_addpro = findViewById(R.id.main_Show_addprodect);
        Card_addcat = findViewById(R.id.main_Show_addcat);
        Card_exit = findViewById(R.id.main_Show_exit);
        Card_showall = findViewById(R.id.main_Show_all);


        Card_addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_ProdectActivity.class);
                startActivity(intent);

            }
        });
        Card_addcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_CatogoryActivity.class);
                startActivity(intent);

            }
        });
        Card_showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);

            }
        });
        Card_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setTitle("Close the application");
                builder.setMessage("Are you sure to exit the application ?");
                builder.setPositiveButton("YES", // الزر الاول
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(MainActivity.this, "EXIT", Toast.LENGTH_SHORT).show();
                                getSharedPreferences("name", MODE_PRIVATE).edit().remove("id").apply();
                                startActivity(new Intent(getBaseContext(), LogInActivity.class));
                                finish();

                            }

                        });
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
                builder.show();
            }


        });


    }
}


