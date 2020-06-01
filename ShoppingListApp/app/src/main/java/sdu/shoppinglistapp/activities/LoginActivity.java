package sdu.shoppinglistapp.activities;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.persistence.DbHandler;
import sdu.shoppinglistapp.business.User;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button loginBtn;
    private Button registerBtn;

    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("MyTag", "onCreate: login found");

        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String email = emailField.getText().toString();
        final String pw = passwordField.getText().toString();

        if (email.equals("") || pw.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_LONG).show();
            return;
        }

        fdb.collection("users").whereEqualTo("Email", email).get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                    if (doc.get("Password").equals(pw)) {

                        final ArrayList<ShopList> slist = new ArrayList<>();
                        /*
                        for (String sid : (ArrayList<String>)doc.get("subscribed_to")) {
                            fdb.collection("lists").document(sid).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            task.getResult().get("")
                                            slist.add();
                                        }
                                    });

                        }
                        */
                        // TODO get shoppinglists for the user and add them to it

                        MainActivity.getInstance().setUser(new User((String)doc.get("Screen_name"), (String)doc.get("Email"), doc.getId(), slist));

                        Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password did not match", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Email was not recognized", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }
}
