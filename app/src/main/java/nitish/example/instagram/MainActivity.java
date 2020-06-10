package nitish.example.instagram;

import android.app.ProgressDialog;
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

        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                //user has actually pressed the enter key after filling all the credentials
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnSignUp);

                }

                return false;
            }
        });

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

                if(edtSignUpEmail.getText().toString().equals("") ||
                        edtSignUpUsername.getText().toString().equals("") ||
                        edtSignUpPassword.getText().toString().equals("")) {

                    FancyToast.makeText(MainActivity.this,
                                    " Email, Username, Password is required!!! ",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                }
                else {
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
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, appUser.getUsername() +
                                                " is signed up successfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                        true).show();
                            } else {
                                FancyToast.makeText(MainActivity.this,
                                        " There was an error: " + e.getMessage(),
                                        Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

                }
                break;
            case R.id.txtLoginPage:

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;

        }

    }

    //hiding the keyboard when user type the empty area on the screen
    public void rootLayoutTapped(View view){

        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){

            e.printStackTrace();

        }

    }

}