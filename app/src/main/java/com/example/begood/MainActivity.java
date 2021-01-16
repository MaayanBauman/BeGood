package com.example.begood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_feed);
//        loginBtn = findViewById(R.id.login_btn);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Tag", "login");
//            }
//        });

        ListView list = findViewById(R.id.feed_list_view);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
    }


    class MyAdapter extends BaseAdapter {
        List<String> data = new LinkedList<String>();

        MyAdapter(){
            for(int i=0; i<10; i++){
                data.add("element #" + i);
            }
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.volunteer_post, null);
            }
            String str = data.get(i);
            TextView title = view.findViewById(R.id.post_title);
            TextView author = view.findViewById(R.id.post_author_value);
            TextView description = view.findViewById(R.id.post_description);

            title.setText(str);
            author.setText(str);
            description.setText(str);

            return view;
        }


    }
}