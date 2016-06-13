package com.home.victor.chp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends Activity implements View.OnClickListener {
    private Fragment vkFragment;
    private Button buttonLogin;
    private Button buttonnonLogin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        buttonnonLogin = (Button) findViewById(R.id.buttonnonLogin);
        buttonnonLogin.setOnClickListener(this);

 //       VKSdk.initialize(this);
     //   Fragment vkFragment = getFragmentManager().findFragmentById(R.id.vkFragment);
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        switch (view.getId()) {
            case R.id.buttonLogin:
                startActivity(intent);
       //       VKSdk.login(vkFragment);
                break;

            case R.id.buttonnonLogin:
                intent.putExtra("nonLogin", true);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Log.e("Auth","ERROR AUTH");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
