package nitish.example.instagram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginUsername, edtLoginPassword;
    private Button btnLogin;
    private TextView txtSignUpPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtSignUpPage = findViewById(R.id.txtSignUpPage);

        btnLogin.setOnClickListener(this);
        txtSignUpPage.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){

            ParseUser.getCurrentUser().logOut();

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnLogin:
                ParseUser.logInInBackground(edtLoginUsername.getText().toString(),
                        edtLoginPassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                if(user != null && e == null){

                                    FancyToast.makeText(LoginActivity.this, user.getUsername() +
                                            " logged in successfully", Toast.LENGTH_SHORT,
                                            FancyToast.SUCCESS, true).show();

                                }

                            }
                        });


                break;
            case R.id.txtSignUpPage:

                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);

                break;

        }

    }
}