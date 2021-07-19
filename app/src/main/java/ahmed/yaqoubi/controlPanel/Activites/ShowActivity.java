package ahmed.yaqoubi.controlPanel.Activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ahmed.yaqoubi.controlPanel.R;


public class ShowActivity extends AppCompatActivity {
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        userId = getSharedPreferences("main", MODE_PRIVATE).getString("id", null);

    }
}