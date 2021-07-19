package ahmed.yaqoubi.controlPanel.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ahmed.yaqoubi.controlPanel.R;
import ahmed.yaqoubi.controlPanel.callback.CallBack;
import ahmed.yaqoubi.controlPanel.firebase.FirebaseAuthentication;

public class LogInActivity extends AppCompatActivity {

    private ImageView logInImgBack;
    private EditText logInEtEmail;
    private EditText logInEtPassword;
    private Button logInBtnLogIn;
    private TextView logInTvLogin;
    private FirebaseAuthentication firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();
        if (getSharedPreferences("main", MODE_PRIVATE).getString("id", null) != null) {
            finish();
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }
        firebaseAuthentication = FirebaseAuthentication.getInstance(LogInActivity.this, null);
        initView();
        logInBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logInEtPassword != null && logInEtEmail != null) {
                    if (!logInEtEmail.getText().toString().isEmpty() && !logInEtPassword.getText().toString().isEmpty()) {

                        firebaseAuthentication.logIn(logInEtEmail.getText().toString(), logInEtPassword.getText().toString(), new CallBack() {
                            @Override
                            public void getResult(String result) {
                                if (!result.equals("fail") && result.length() > 5) {


                                    getSharedPreferences("main", MODE_PRIVATE).edit().putString("id", result).apply();
                                    finish();
                                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                                } else {
                                    Toast.makeText(LogInActivity.this, "Email or password invalid", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    } else {

                        Toast.makeText(LogInActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(LogInActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logInTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));
            }
        });


    }

    private void initView() {
        logInImgBack = findViewById(R.id.logIn_img_back);
        logInEtEmail = findViewById(R.id.logIn_et_email);
        logInEtPassword = findViewById(R.id.logIn_et_password);
        logInBtnLogIn = findViewById(R.id.logIn_btn_logIn);
        logInTvLogin = findViewById(R.id.logIn_tv_login);
    }
}