package com.example.a10012032.dreamapplicationv2.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a10012032.dreamapplicationv2.UserAuth.Login;
import com.example.a10012032.dreamapplicationv2.UserAuth.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.example.a10012032.dreamapplicationv2.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class feedFragment extends Fragment {
    private static final String Tab = "feedFragment";
    ListView list;
    DatabaseReference mDatabaseRef, mUserCheckData, mGodPlease;
    ArrayList<Post> array;
    CustomAdapter customAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment,container,false);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Posts").child("Posts");
        array=new ArrayList<>();
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.prof);
        array.add(new Post(bitmapToString(bm),"Caption","Username"));
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    mUserCheckData=mDatabaseRef.child(ds.getKey());
                    array.add(new Post(mUserCheckData.child("imageString").toString(),mUserCheckData.child("Message").toString(),mUserCheckData.child("Username").toString()));
                    Log.d("TAGPLS",mUserCheckData.child("Username").toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        customAdapter=new CustomAdapter(getActivity(),R.layout.item,array);
        list = view.findViewById(R.id.id_listView);
        list.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
        return view;
    }
   /* public ArrayList<Post> retrieve(){
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return array;
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        array.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Post newPost=ds.getValue(Post.class);
            array.add(newPost);
        }
    }
*/
    public class CustomAdapter extends ArrayAdapter<Post> {
        Context context;
        List<Post> list;

        public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Post> objects) {
            super(context, resource, objects);
            this.context=context;
            list=objects;
        }

        @NonNull
        @Override
        public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(R.layout.item,null);

            TextView userN = adapterView.findViewById(R.id.profMainTxt);
            ImageView postI = adapterView.findViewById(R.id.imagePost);
            TextView capt = adapterView.findViewById(R.id.captionTxt);
            Log.d("TAGHI",array.get(i).getUsername()+" is null");
            userN.setText(array.get(i).getUsername());
            postI.setImageBitmap(stringToBitMap(array.get(i).getBitmapString()));
            capt.setText(array.get(i).getCaption());
            notifyDataSetChanged();
            return adapterView;

        }
    }
    public Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public String bitmapToString(Bitmap bm){
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bm.recycle();
        byte[] byteArray = bYtE.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
