package com.example.a10012032.dreamapplicationv2.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10012032.dreamapplicationv2.R;
import com.example.a10012032.dreamapplicationv2.UserAuth.signUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profileFragment extends Fragment {
    private static final String Tab = "profileFragment";
    FirebaseAuth mAuth;
    String keyUser;
    TextView email,pass,user,dateofbirth,phoneNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user,container,false);
        mAuth=FirebaseAuth.getInstance();
        keyUser= signUp.USER_KEY;
        email = view.findViewById(R.id.email2Txt);
        pass = view.findViewById(R.id.pass2Txt);
        user = view.findViewById(R.id.user2Txt);
        dateofbirth = view.findViewById(R.id.dob2Txt);
        phoneNumber = view.findViewById(R.id.phNo2Txt);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference postsRef = rootRef.child("Users");
        ValueEventListener Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String Email = dataSnapshot.child(keyUser).child("EmailUser").getValue(String.class);
               email.setText("Email address: "+Email);
               String Password = dataSnapshot.child(keyUser).child("PasswordUser").getValue(String.class);
               pass.setText("Password: "+Password);
               String Username = dataSnapshot.child(keyUser).child("Username").getValue(String.class);
               user.setText("Username: "+Username);
               String DOB = dataSnapshot.child(keyUser).child("birthday").getValue(String.class);
               dateofbirth.setText("Date Of Birth: "+DOB);
               String PhNum = dataSnapshot.child(keyUser).child("phoneNumber").getValue(String.class);
               phoneNumber.setText("Mobile Number: "+PhNum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postsRef.addListenerForSingleValueEvent(Listener);
        return view;
    }
}
