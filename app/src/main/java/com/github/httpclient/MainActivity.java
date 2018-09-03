package com.github.httpclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.bean.TestBean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
        Volley.sendJsonRequest(null, url, TestBean.class, new IDataListener<TestBean>() {
            @Override
            public void onSuccess(TestBean testBean) {
                String string = testBean.toString();
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Log.e("test", "shibai");
            }
        });
    }
}
