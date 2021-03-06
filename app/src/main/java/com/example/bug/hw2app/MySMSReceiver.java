package com.example.bug.hw2app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by bug on 2016. 4. 14..
 */
public class MySMSReceiver extends BroadcastReceiver {

    static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION)) {

            // SMS message parsing
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            Object[] messages = (Object[]) bundle.get("pdus");
            if (messages == null) return;

            SmsMessage[] smsMessage = new SmsMessage[messages.length];      // message 처리

            for (int i = 0; i < messages.length; i++) {
                // PDU 포맷으로 되어 있는 메시지를 복원.
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            // SMS 발신 번호 확인
            String origNumber = smsMessage[0].getOriginatingAddress();      // 광고 문자 시 이 번호로 답장

            // 받은 메세지를 String에 저장
            String message = smsMessage[0].getMessageBody().toString();
            Log.d("문자 내용", "발신자 : "+origNumber+", 내용 : " + message);
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


            // 문자 내용에서 "AD" 혹은 "ad"라는 단어가 포함되었다면
            if (message.contains("AD") || message.contains("ad")) {

                // SMS 발송을 위한 SmsManager 객체 받아옴
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(origNumber, null, "Do not Send AD", null, null);     // AD를 보낸 번호(origNumber)로 "Do not Send AD" 라는 메세지를 답장
            }

        }

    }

}



