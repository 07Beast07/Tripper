package com.example.amuntimilsina.bideshisawari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amuntimilsina.bideshisawari.Interface.UserInterfaces;
import com.example.amuntimilsina.bideshisawari.RetrofitInitilization.ApiClient;
import com.example.amuntimilsina.bideshisawari.models.LoginModel;
import com.example.amuntimilsina.bideshisawari.models.LoginResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPageActivity extends AppCompatActivity {

    EditText Phone,Password;
    Button Registerbtn,Loginbtn;
    ImageView bckBtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Registerbtn = findViewById(R.id.Registerbtn);
        bckBtn = findViewById(R.id.bckBtn);
        Phone = findViewById(R.id.Phone);
        Password = findViewById(R.id.Password);
        Loginbtn = findViewById(R.id.Loginbtn);
        sharedPreferences = getSharedPreferences("user_info",0);

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPageActivity.this,RegisterActivity.class));
            }
        });
        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String PhoneValue = Phone.getText().toString();
               String PasswordValue = Password.getText().toString();

                if(PhoneValue.isEmpty() || PasswordValue.isEmpty()){
                    Toast.makeText(LoginPageActivity.this, "Fill the fields!", Toast.LENGTH_SHORT).show();
                }else {
                    LoginModel loginModel = new LoginModel(PhoneValue,PasswordValue);
                    Retrofit retrofit = ApiClient.getApiClient();
                    UserInterfaces userInterfaces = retrofit.create(UserInterfaces.class);
                    Call<LoginResponseModel> call = userInterfaces.userLogin(loginModel);

                    call.enqueue(new Callback<LoginResponseModel>() {
                        @Override
                        public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                            String resp =  response.body().getResponse();
                            Log.i("msg",resp);
                            if(resp.equals("failed")){
                                Password.setText("");
                                Toast.makeText(LoginPageActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                            }else if(resp.equals("ok")){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("phone",PhoneValue);
                                editor.apply();
                                startActivity(new Intent(LoginPageActivity.this,HomeActivity.class));
                            }else {
                                Password.setText("");
                                Toast.makeText(LoginPageActivity.this, "Login Failed!Please check your connection!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponseModel> call, Throwable t) {

                            Log.i("error:", t.getMessage());
                            Log.i("error:", t.getMessage());
                            Toast.makeText(LoginPageActivity.this, "error:", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });



    }


}
