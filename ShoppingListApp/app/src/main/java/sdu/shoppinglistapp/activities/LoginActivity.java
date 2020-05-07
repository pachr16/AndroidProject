package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sdu.shoppinglistapp.R;

public class LoginActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;

    EditText email;
    EditText password;
    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().equals("") || !password.getText().equals("")) {
                    login(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Please log in for access", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password);
    }

}
