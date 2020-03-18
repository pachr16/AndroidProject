package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.persistence.DbHandler;
import sdu.shoppinglistapp.business.User;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("MyTag", "onCreate: login found");

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

    }

    private void loginUser() {
        DbHandler dbHandler = DbHandler.getInstance();

        User tmpUser = dbHandler.checkCredentials((email+""), (password+""));

        if(tmpUser.geteMail().equals(email) && tmpUser.getPasword().equals(password)) {
            // TODO set user to logged in user (maybe save to "cache")

            Intent intent = new Intent(this, ShoppingActivity.class);
            startActivity(intent);
        }

    }
}
