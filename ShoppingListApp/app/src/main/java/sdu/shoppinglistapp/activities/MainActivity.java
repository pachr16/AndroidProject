package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.User;

public class MainActivity extends AppCompatActivity {
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyTag", "onCreate: Main found");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user.equals(null)) {
            // send to login activity
        } else {
            // send to shoppingList activity
        }
    }
}
