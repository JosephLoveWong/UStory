package com.promiseland.ustory.ui.mvp.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.promiseland.ustory.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.listView)
    ListView mListView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, DemoViewActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_splash);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                getData());

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    private ArrayList<String> list = new ArrayList<String>();

    private ArrayList<String> getData() {
        list.add("HorizontalScrollView");
        list.add("LeafLoadingView");
        list.add("PieView");
        list.add("RadarView");
        list.add("GooView");
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                DemoActivity.launch(this, "HorizontalScrollView");
                break;
            case 1:
                DemoActivity.launch(this, "LeafLoadingView");
                break;
            case 2:
                DemoActivity.launch(this, "PieView");
                break;
            case 3:
                DemoActivity.launch(this, "RadarView");
                break;
            case 4:
                DemoActivity.launch(this, "GooView");
                break;
            default:
                break;
        }
    }
}
