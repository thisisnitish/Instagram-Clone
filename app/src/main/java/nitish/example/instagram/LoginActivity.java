package nitish.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //user has actually pressed the enter key after filling all the credentials
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnLogin);

                }

                return false;
            }
        });

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

                if(edtLoginUsername.getText().toString().equals("") ||
                        edtLoginPassword.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity.this,
                            " Username, Password is required!!! ",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                }
                else {
                    ParseUser.logInInBackground(edtLoginUsername.getText().toString(),
                            edtLoginPassword.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {

                                    if (user != null && e == null) {

                                        FancyToast.makeText(LoginActivity.this, user.getUsername() +
                                                        " logged in successfully", Toast.LENGTH_SHORT,
                                                FancyToast.SUCCESS, true).show();

                                    }

                                }
                            });

                }
                break;
            case R.id.txtSignUpPage:

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                break;

        }

    }

    //hiding the keyboard when user type the empty area on the screen
    public void rootLayoutTappedLogin(View view){

        try{

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
        catch (Exception e){

            e.printStackTrace();

        }

    }
}