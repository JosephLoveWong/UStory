package com.promiseland.ustory.ui.mvp.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.promiseland.ustory.R;
import com.promiseland.ustory.ui.mvp.about.AboutUsActivity;
import com.promiseland.ustory.ui.mvp.introscreen.IntroScreenActivity;
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewActivity;
import com.promiseland.ustory.ui.mvp.whatsnew.WhatsNewActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoSplashActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.listView)
    ListView mListView;

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
        list.add("WhatsNew");
        list.add("Introduce");
        list.add("About");
        list.add("Settings");
        list.add("ViewDemo");
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                WhatsNewActivity.Companion.launch(this);
                break;
            case 1:
                IntroScreenActivity.Companion.launch(this);
                break;
            case 2:
                AboutUsActivity.Companion.launch(this);
                break;
            case 3:
                SettingsOverviewActivity.Companion.launch(this);
                break;
            case 4:
                DemoViewActivity.launch(this);
                break;
            default:
                break;
        }
    }
}
