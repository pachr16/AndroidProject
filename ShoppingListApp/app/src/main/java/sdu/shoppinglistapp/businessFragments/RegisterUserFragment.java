package sdu.shoppinglistapp.businessFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.activities.LoginActivity;

public class RegisterUserFragment extends Fragment {

    EditText screenName;
    EditText email;
    EditText password1;
    EditText password2;
    Button btn_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_register_user, container, false);

        screenName = view.findViewById(R.id.login_email);
        email = view.findViewById(R.id.register_email);
        password1 = view.findViewById(R.id.register_password1);
        password2 = view.findViewById(R.id.register_password2);
        btn_register = view.findViewById(R.id.btn_register);



        return view;
    }

    public void onStart() {
        super.onStart();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity login = (LoginActivity) getActivity();
                login.testRegister();
            }
        });


    }

}
