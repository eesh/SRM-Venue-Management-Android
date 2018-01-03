package com.srmuniv.srmvenuemanagementtool.authentication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.home.HomeActivity;
import com.srmuniv.srmvenuemanagementtool.network.AuthClient;
import com.srmuniv.srmvenuemanagementtool.repositories.authentication.AuthDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.authentication.AuthRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.authentication.local.AuthLocalRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.user.local.UserLocalRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.user.network.UserNetworkRepository;
import com.srmuniv.srmvenuemanagementtool.databinding.FragmentLoginBinding;
import com.srmuniv.srmvenuemanagementtool.network.models.AuthRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eesh on 10/14/17.
 */

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    AuthClient.AuthAPI client;
    UserRepository userRepository;
    AuthRepository authRepository;
    FragmentLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_login);
        client = AuthClient.getClient();
        authRepository = AuthRepository.getInstance(AuthLocalRepository.getInstance(this));
        binding.loginButton.setOnClickListener(this);
        authRepository.getAuthToken(new AuthDataSource.GetTokenCallback() {
            @Override
            public void onTokenLoaded(String token, long expiry) {
                UserRepository.getInstance(UserLocalRepository.getInstance(getApplicationContext()), UserNetworkRepository.getInstance(token));
                Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
                intent.putExtra("authToken", token);
                startActivity(intent);
                finish();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        String email = binding.emailET.getText().toString();
        String password = binding.passwordET.getText().toString();
        if(email.length() < 5 || password.length() < 5) {
            Toast.makeText(this, "Check email or password", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("onClick", "clicked");
        Call<AuthRes> call = client.loginUser(email, password);
        call.enqueue(new Callback<AuthRes>() {
            @Override
            public void onResponse(Call<AuthRes> call, Response<AuthRes> response) {
                Log.e("onClick", "response");
                if(response.isSuccessful()) {
                    AuthRes authRes = response.body();
                    if(authRes == null) {
                        Toast.makeText(AuthActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    finishLogin(response.body());
                } else Toast.makeText(AuthActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AuthRes> call, Throwable t) {
                Toast.makeText(AuthActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishLogin(final AuthRes authRes) {
        
        
        userRepository.storeUser(authRes.user, new UserDataSource.StoreUserCallback() {
            @Override
            public void onUserStored() {
                authRepository.storeAuthToken(authRes.token, authRes.expiry);
                UserRepository.getInstance(UserLocalRepository.getInstance(getApplicationContext()), UserNetworkRepository.getInstance(authRes.token));
                Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
                intent.putExtra("authToken", authRes.token);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError() {
                Toast.makeText(AuthActivity.this, "Couldn't store user information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
