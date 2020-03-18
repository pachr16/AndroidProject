package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import sdu.shoppinglistapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyTag", "onCreate: Main found");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
