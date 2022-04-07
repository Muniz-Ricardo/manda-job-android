package br.com.nouva.mandajob.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import br.com.nouva.mandajob.R;
import br.com.nouva.mandajob.controler.ControlerViewProfile;
import br.com.nouva.mandajob.viewModel.ModelViewProfileView;

public class ViewProfileActivity extends AppCompatActivity {

    protected static String ID_TEST_USER_MAN = "-M8Xh-KM6rtTgJfi3kXa";
    protected static String ID_TEST_USER_WOMAN = "-M8Xopfv1JFgkyZJ7tcm";
    public Context context = this;
    public ControlerViewProfile controler;
    public ModelViewProfileView model;
    private RelativeLayout layout_button_label_personal;
    private LinearLayout layout_button_exit, layout_button_work_show, layout_button_about_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        openIDS();
        //String key = getIntentACT();
        View rootView = getWindow().getDecorView().getRootView();

        controler = new ControlerViewProfile(context, rootView);
        controler.initializeService(ID_TEST_USER_WOMAN);

        model = new ModelViewProfileView(context, controler);
        layout_button_label_personal.setOnClickListener(model);
        layout_button_exit.setOnClickListener(model);
        layout_button_work_show.setOnClickListener(model);
        layout_button_about_show.setOnClickListener(model);
    }

    private void openIDS() {
        layout_button_label_personal = findViewById(R.id.layout_button_label_personal);
        layout_button_exit = findViewById(R.id.layout_button_exit);

        layout_button_work_show = findViewById(R.id.layout_button_work_show);
        layout_button_about_show = findViewById(R.id.layout_button_about_show);
    }

    private String getIntentACT() {
        Intent intent = getIntent();
        String extra = intent.getStringExtra("from");
        if (!extra.isEmpty()) return extra;
        return null;
    }

}