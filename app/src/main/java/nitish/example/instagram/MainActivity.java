package nitish.example.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Components
    private EditText edtSignUpEmail, edtSignUpUsername, edtSignUpPassword;
    private Button btnSignUp;
    private TextView txtLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        btnSignUp = findViewById(R.id.btnSignUp);

        txtLoginPage = findViewById(R.id.txtLoginPage);

        btnSignUp.setOnClickListener(this);
        txtLoginPage.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:

                final ParseUser appUser = new ParseUser();
                appUser.setEmail(edtSignUpEmail.getText().toString());
                appUser.setUsername(edtSignUpUsername.getText().toString());
                appUser.setPassword(edtSignUpPassword.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Signin up " + edtSignUpUsername.getText().toString());
                progressDialog.show();

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(MainActivity.this, appUser.getUsername() +
                                    " is signed up successfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                    true).show();
                        }
                        else{
                            FancyToast.makeText(MainActivity.this,
                                    " There was an error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });

                break;

            case R.id.txtLoginPage:

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;

        }

    }
}