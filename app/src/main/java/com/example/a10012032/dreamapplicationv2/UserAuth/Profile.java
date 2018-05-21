package com.example.a10012032.dreamapplicationv2.UserAuth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a10012032.dreamapplicationv2.Main.MainActivity;
import com.example.a10012032.dreamapplicationv2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {
    EditText fName, lName, PhNo, BDay, uName;
    Button svBtn;
    DatabaseReference mDataRef;
    String fNameStr, lNameStr, PhNoStr, BDayStr, uNameStr;
    String keyUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        keyUser = signUp.USER_KEY;
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(keyUser);
        fName = (EditText) findViewById(R.id.fName);
        lName = (EditText) findViewById(R.id.lName);
        PhNo = (EditText) findViewById(R.id.phno);
        BDay =(EditText)  findViewById(R.id.bday);
        uName = (EditText)findViewById(R.id.uName);
        svBtn = (Button) findViewById(R.id.svBtn);
        svBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fNameStr = fName.getText().toString().trim();
                lNameStr = lName.getText().toString().trim();
                PhNoStr = PhNo.getText().toString().trim();
                BDayStr = BDay.getText().toString().trim();
                uNameStr = uName.getText().toString().trim();

                if(!TextUtils.isEmpty(fNameStr) && !TextUtils.isEmpty(lNameStr) && !TextUtils.isEmpty(PhNoStr) && !TextUtils.isEmpty(uNameStr)) {
                    mDataRef.child("firstName").setValue(""+fNameStr);
                    mDataRef.child("lastName").setValue(""+lNameStr);
                    mDataRef.child("phoneNumber").setValue(""+PhNoStr);
                    mDataRef.child("Username").setValue(""+uNameStr);
                    if(Login.usernameValue==(null)){
                        Login.usernameValue=uNameStr;
                    }
                    mDataRef.child("isVerified").setValue("verified");
                    if(!TextUtils.isEmpty(BDayStr)){
                        mDataRef.child("birthday").setValue(""+BDayStr);
                    }else{
                        mDataRef.child("birthday").setValue("null");
                    }
                    Toast.makeText(Profile.this, "User profile added", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(Profile.this, MainActivity.class));

                }else{
                    Toast.makeText(Profile.this, "Failed to create User Account", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}

