package com.example.a10012032.dreamapplicationv2.Main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import com.example.a10012032.dreamapplicationv2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.a10012032.dreamapplicationv2.Main.cameraFragment.refresh;
import static java.lang.Integer.parseInt;


public class feedFragment extends Fragment {
    private static final String Tab = "feedFragment";
    ListView list;
    DatabaseReference mDatabaseRef, mUserCheckData, mGodPlease;
    ArrayList<Post> array;
    CustomAdapter customAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment,container,false);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Posts").child("Posts");
        array=new ArrayList<>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference postsRef = rootRef.child("Posts").child("Posts");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String message = ds.child("Message").getValue(String.class);
                    Log.d("TAGVAL",message);
                    String image = ds.child("imageString").getValue(String.class);
                    Log.d("TAGVAL",image);
                    String usernaem = ds.child("Username").getValue(String.class);
                    Log.d("TAGVAL",usernaem);
                    int Likes = ds.child("likeCount").getValue(Integer.class);
                    Log.d("TAGVAL",Likes+"");
                    array.add(new Post(Likes, image, message, usernaem));
                    Log.d("sizeofarray",array.size()+"");
                }

                customAdapter=new CustomAdapter(getActivity(),R.layout.item,array);
                list = view.findViewById(R.id.id_listView);
                list.setAdapter(customAdapter);
                //customAdapter.clear();

                customAdapter.notifyDataSetChanged();
                Log.d("sizeofarray",array.size()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        postsRef.addListenerForSingleValueEvent(valueEventListener);
        if(refresh){
            customAdapter.notifyDataSetChanged();
            refresh=false;
        }
        return view;
    }
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
            TextView lik = adapterView.findViewById(R.id.likeCountTxt);
            Log.d("TAGHI",array.get(i).getUsername()+" is null");
            userN.setText(String.valueOf(array.get(i).getUsername()));
            lik.setText(String.valueOf(array.get(i).getLikes()));
            postI.setImageBitmap(stringToBitMap(array.get(i).getBitmapString()));
            capt.setText(String.valueOf(array.get(i).getCaption()));
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
