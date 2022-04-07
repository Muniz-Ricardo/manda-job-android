package br.com.nouva.mandajob.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import br.com.nouva.mandajob.R;

public class MigrateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_label_migrate;
    private LinearLayout layout_button_migrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migrate);

        init();
        spanableTextview();

        layout_button_migrate.setOnClickListener(this);
    }

    private void init() {
        tv_label_migrate = findViewById(R.id.tvMigrateDescription);
        layout_button_migrate = findViewById(R.id.layout_button_sign);
    }

    private void spanableTextview() {
        Spannable spannable = new SpannableString("Ande mais rápido e fique no topo de ofertas e perfis.");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)),
                28, 33, spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_label_migrate.setText(spannable);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layout_button_sign) {
            Intent intent = new Intent(MigrateActivity.this, NewPaymentActivity.class);
            startActivity(intent);
        }
    }
}
