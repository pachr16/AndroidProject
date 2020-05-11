package sdu.shoppinglistapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sdu.shoppinglistapp.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    EditText screenName;
    EditText email;
    EditText password1;
    EditText password2;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Don't mind the red line.
        //It's a warning against locking screen orientation which is needed here as we do not want the user to go to landscape-mode
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();

        screenName = findViewById(R.id.register_screenName);
        email = findViewById(R.id.register_email);
        password1 = findViewById(R.id.register_password1);
        password2 = findViewById(R.id.register_password2);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
//         Make sure there's no empty fields
        if(!screenName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password1.getText().toString().isEmpty() && !password2.getText().toString().isEmpty()) {
            //make sure there is a legit email
            if(email.getText().toString().contains("@") && email.getText().toString().contains(".")) {
                //make sure passwords match
                if(password1.getText().toString().equals(password2.getText().toString())) {

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password1.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("createNewUser", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);

                                        Log.d("createNewUser", "onComplete: new user id = " + mAuth.getCurrentUser().getUid());

                                        myRef = database.getReference("users/" + mAuth.getCurrentUser().getUid() + "/screen_name");
                                        myRef.setValue(screenName.getText().toString());

                                        myRef = database.getReference("users/" + mAuth.getCurrentUser().getUid() + "/user_id");
                                        myRef.setValue(mAuth.getCurrentUser().getUid());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("createNewUser", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });


//                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password1.getText().toString())
//                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in currentUser's information
//                                Log.d("registerUser", "createUserWithEmail:success");
//                                FirebaseUser currentUser = mAuth.getCurrentUser();
//
//                                //Create a user outside Authentication with newly created userID
//                                db.createUser(currentUser.getUid(), screenName.getText().toString());
//
//                                updateUI(currentUser);
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.d("registerUser", "createUserWithEmail:failure", task.getException());
//                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
//                            }
//                        }
//                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords don't match!", Toast.LENGTH_LONG).show();
                    password1.setText("");
                    password2.setText("");
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please enter a legit email", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(RegisterActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }


    }

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            Log.d("createNewUser", "updateUI: user logged in with id: " + mAuth.getCurrentUser().getUid());
            startActivity(new Intent(this, ShoppingActivity.class));
        } else {
            Toast.makeText(this, "Please log in for access", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
