package com.example.bug.hw2app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Runtime permission request const
    public static final int MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS = 0;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Initialize
        textView = (TextView)findViewById(R.id.textView);
        textView.setText("나는 SMS 수신 브로드캐스트 등록자 이다.");


        /** Runtime permission check in Android API 23~(marshmellow) */
        /** 1. 권한 확인 변수 설정 (내가 필요로 하는 permission이 이 액티비티에서 허가되었는지를 판단) */
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        /** 2. 권한 요청 (PERMISSION_GRANTED = permission 인정) */
        // 이 App에 대해 다음 permission들이 하나라도 허가되지 않았다면,
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            // 액티비티에서 permission들 요청
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS
            );
        }

    }


    /** 3. Permission 요청에 대한 응답을 Handle하는 callback 함수 override */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS :      // 내가 보낸 permission request에 대한 응답인지를 상수로 확인
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
        }
        // other 'case' lines to check for other permissions this app might request

    }
}
