package com.example.apitestig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText emailEditText;
    CheckBox termsAndConditionsCheckBox;
    Button signUpButton;
    TextView haveAccountTextView;

    User user;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initialize all the variable here
        usernameEditText = findViewById(R.id.sign_up_username);
        passwordEditText = findViewById(R.id.sign_up_password);
        emailEditText = findViewById(R.id.sign_up_email);
        termsAndConditionsCheckBox = findViewById(R.id.sign_up_terms_and_conditions);
        signUpButton = findViewById(R.id.sign_up_button);
        haveAccountTextView = findViewById(R.id.sign_up_have_account);

        usernameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!User.validateUsername(usernameEditText.getText().toString())) {
                    usernameEditText.setError("Username should be at least 6 characters long");
                }
            }
        });

        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!User.validatePassword(passwordEditText.getText().toString())) {
                    passwordEditText.setError("Password should be at least 8 characters long");
                }
            }
        });

        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!User.validateEmail(emailEditText.getText().toString())) {
                    emailEditText.setError("Email should be a valid email address");
                }
            }
        });

        termsAndConditionsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                termsAndConditionsCheckBox.setError("You must accept the terms and conditions");
            }
        });



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);


//        when the signup button is clicked, create a new user object and show a toast message with the username, password and email

        signUpButton.setOnClickListener(v -> {
            if (User.validateUsername(usernameEditText.getText().toString()) &&
                    User.validatePassword(passwordEditText.getText().toString()) &&
                    User.validateEmail(emailEditText.getText().toString()) &&
                    termsAndConditionsCheckBox.isChecked()) {
                user = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString());
                // write Retrofit code to send the user object to the server localhost 8000/api/users


                Call<Void> call = userInterface.createUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if ( response.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(SignUpActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
                    }
                });


                // if the user is successfully created, show a toast message with the username, password and email
                // if the user is not successfully created, show a toast message with the error message from the servers


            }else {
                if (!User.validateUsername(usernameEditText.getText().toString())) {
                    usernameEditText.setError("Username should be at least 6 characters long");
                }
                if (!User.validatePassword(passwordEditText.getText().toString())) {
                    passwordEditText.setError("Password should be at least 8 characters long");
                }
                if (!User.validateEmail(emailEditText.getText().toString())) {
                    emailEditText.setError("Email should be a valid email address");
                }
                if (!termsAndConditionsCheckBox.isChecked()) {
                    termsAndConditionsCheckBox.setError("You must accept the terms and conditions");
                }
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }



        });

    }

//    create retrofit instance
//    create an instance of the user interface
//    create a call object in the user interface
//    call the enqueue method on the call object
//    override the onResponse method


    interface UserInterface {
        @POST("signup")
        Call<Void> createUser(@Body User user);
    }


}



