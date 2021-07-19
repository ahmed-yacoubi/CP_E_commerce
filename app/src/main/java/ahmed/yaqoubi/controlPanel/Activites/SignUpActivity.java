package ahmed.yaqoubi.controlPanel.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ahmed.yaqoubi.controlPanel.R;
import ahmed.yaqoubi.controlPanel.callback.CallBack;
import ahmed.yaqoubi.controlPanel.firebase.FirebaseAuthentication;
import ahmed.yaqoubi.controlPanel.model.User;

public class SignUpActivity extends AppCompatActivity {
    private ImageView signupImgBack;
    private EditText signupEtEmail;
    private EditText signupEtFName;
     private EditText signupEtPassword;
    private Button signupBtnSignup;
    private TextView signupTvLogin;
    private FirebaseAuthentication firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();

//        String tokenId = FirebaseInstanceId.getInstance().getToken();

        firebaseAuthentication = FirebaseAuthentication.getInstance(this, null);
        signupBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signupEtEmail != null && signupEtFName != null   && signupEtPassword != null) {

                    if (!signupEtEmail.getText().toString().isEmpty() && !signupEtFName.getText().toString().isEmpty()   && !signupEtPassword.getText().toString().isEmpty()) {

                        User user = new User();
                        user.setPassword(signupEtPassword.getText().toString());
                        user.setEmail(signupEtEmail.getText().toString());
                        user.setName(signupEtFName.getText().toString());
                        firebaseAuthentication.signUp(  user, new CallBack() {
                            @Override
                            public void getResult(String result) {
                                if (result.equals("done")) {

                                    finish();
                                    startActivity(new Intent(getBaseContext(), LogInActivity.class));
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Email used or password short", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignUpActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signupTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getBaseContext(), LogInActivity.class));
            }
        });
        signupImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        signupImgBack = findViewById(R.id.signup_img_back);
        signupEtEmail = findViewById(R.id.signup_et_email);
        signupEtFName = findViewById(R.id.signup_et_fName);
         signupEtPassword = findViewById(R.id.signup_et_password);
        signupBtnSignup = findViewById(R.id.signup_btn_signup);
        signupTvLogin = findViewById(R.id.signup_tv_login);
    }
}