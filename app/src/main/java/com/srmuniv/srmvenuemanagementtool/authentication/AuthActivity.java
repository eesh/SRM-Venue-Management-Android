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
import com.srmuniv.srmvenuemanagementtool.network.UserClient;
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

    UserClient.UserAPI client;
    UserRepository repository;
    FragmentLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_login);
        client = UserClient.getClient();
        repository = UserRepository.getInstance(UserLocalRepository.getInstance(getApplicationContext()), new UserNetworkRepository());
        binding.loginButton.setOnClickListener(this);
        repository.getAuthToken(new UserDataSource.GetTokenCallback() {
            @Override
            public void onTokenLoaded(String token, long expiry) {
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
        
        
        repository.storeUser(authRes.user, new UserDataSource.StoreUserCallback() {
            @Override
            public void onUserStored() {
                repository.storeAuthToken(authRes.token, authRes.expiry);
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
