package com.example.a10012032.dreamapplicationv2.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10012032.dreamapplicationv2.R;
import com.example.a10012032.dreamapplicationv2.UserAuth.Login;
import com.example.a10012032.dreamapplicationv2.UserAuth.signUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class cameraFragment extends Fragment {
    private static final String Tab = "cameraFragment";
    private static final int CAMERA_REQUEST_CODE = 102;

    private Button take;
    private ImageView holder;
    private EditText editText;
    private TextView caption;
    private Button post;
    Bitmap bitmap;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseRef;
    DatabaseReference mUserData;
    String keyUser;
    String UsernameStr;
    public static boolean refresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_fragment, container, false);
        keyUser = signUp.USER_KEY;
        UsernameStr= Login.usernameValue;
        bitmap = null;
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        mUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(keyUser);
        take = view.findViewById(R.id.btnCapture);
        post = view.findViewById(R.id.post);
        holder = view.findViewById(R.id.imageView2);
        editText = view.findViewById(R.id.editText2);
        editText.setVisibility(View.INVISIBLE);
        caption = view.findViewById(R.id.cap);
        caption.setVisibility(View.INVISIBLE);
        post.setVisibility(View.INVISIBLE);


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromCamera();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caption = editText.getText().toString();
                DatabaseReference mChildDatabase = mDatabaseRef.child("Posts").push();
                mChildDatabase.child("imageString").setValue(bitmapToString(bitmap));
                mChildDatabase.child("likeCount").setValue(0);
                Log.d("TAGUSERNAME", UsernameStr+" is null");
                mChildDatabase.child("Username").setValue(UsernameStr);
                mChildDatabase.child("Message").setValue(caption);
                holder.setImageResource(0);
                editText.setVisibility(View.INVISIBLE);
                post.setVisibility(View.INVISIBLE);
                refresh=true;

            }
        });


        return view;
    }
    public void takePictureFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
    public String bitmapToString(Bitmap bm){
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bm.recycle();
        byte[] byteArray = bYtE.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            holder.setImageBitmap(bitmap);
            editText.setVisibility(View.VISIBLE);
            caption.setVisibility(View.VISIBLE);
            post.setVisibility(View.VISIBLE);
            editText.setText("");
        }
    }
}
