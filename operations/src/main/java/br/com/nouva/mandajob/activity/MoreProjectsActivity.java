package br.com.nouva.mandajob.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.nouva.mandajob.R;
import br.com.nouva.mandajob.adapter.AdapterViewMoreDiscover;
import br.com.nouva.mandajob.body.BodySearch;
import br.com.nouva.mandajob.helper.ApiGetService;
import br.com.nouva.mandajob.model.ModelMoreDiscover;
import br.com.nouva.mandajob.service.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreProjectsActivity extends AppCompatActivity {

    protected static int LIST_SELECTED = 0;
    protected static int SECTION_SELECT_TEST = 1;
    protected static String FILTER_DATA_TYPE = "All";
    protected static String FILTER_DATA_SUB = "onload";

    protected Context context = this;

    protected RecyclerView recyclerview_more_discover;
    protected ProgressBar progress_more_project;
    protected LinearLayout layout_more_projects;
    protected TextView tv_title_more_items;

    protected AdapterViewMoreDiscover adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_projects);
        init();
        setDataBundle();
    }

    private void init() {
        recyclerview_more_discover = findViewById(R.id.recyclerview_more_discover);
        progress_more_project = findViewById(R.id.progress_more_project);
        layout_more_projects = findViewById(R.id.layout_more_projects);
        tv_title_more_items = findViewById(R.id.tv_title_more_items);
    }

    private void setDataBundle() {
        LIST_SELECTED = getBundleParamsList();
        changeTitle(LIST_SELECTED);
        createConnectionServer(getBundleParams(), getBundleParamsSub());
    }

    @NonNull
    private String getBundleParams() {
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        if (from != null && !from.isEmpty()) {
            return from;
        }
        assert from != null;
        return from;
    }

    @NonNull
    private String getBundleParamsSub() {
        Intent intent = getIntent();
        String from = intent.getStringExtra("sub");
        if (from != null && !from.isEmpty()) {
            return from;
        }
        assert from != null;
        return from;
    }

    private int getBundleParamsList() {
        Intent intent = getIntent();
        return intent.getIntExtra("list", 0);
    }

    private void changeTitle(int current) {
        switch (current) {
            case 1:
                tv_title_more_items.setText("For you");
                break;
            case 2:
                tv_title_more_items.setText("General");
                break;
        }
    }

    private void createConnectionServer(String type, String sub) {
        String subject = null;
        if (!sub.isEmpty()) subject = sub;

        progress_more_project.setVisibility(View.VISIBLE);
        layout_more_projects.setVisibility(View.GONE);

        Service service = ApiGetService.getRetrofitInstance().create(Service.class);
        BodySearch bodySearch = new BodySearch(type, subject);

        service.viewMoreProjects(bodySearch).enqueue(new Callback<List<ModelMoreDiscover>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelMoreDiscover>> call, @NonNull Response<List<ModelMoreDiscover>> response) {
                if (response.code() == 200) setDataListLoad(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelMoreDiscover>> call, @NonNull Throwable t) {
                Toast.makeText(MoreProjectsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataListLoad(List<ModelMoreDiscover> body) {
        GridLayoutManager mLayoutManager = new GridLayoutManager(context, 2);

        if (LIST_SELECTED == 2) Collections.reverse(body);

        adapter = new AdapterViewMoreDiscover(body, context);
        recyclerview_more_discover.setHasFixedSize(true);
        recyclerview_more_discover.setLayoutManager(mLayoutManager);
        recyclerview_more_discover.setAdapter(adapter);

        recyclerview_more_discover.setNestedScrollingEnabled(false);

        progress_more_project.setVisibility(View.GONE);
        layout_more_projects.setVisibility(View.VISIBLE);
    }

}