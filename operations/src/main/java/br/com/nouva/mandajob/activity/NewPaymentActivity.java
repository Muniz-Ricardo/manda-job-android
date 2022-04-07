package br.com.nouva.mandajob.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import br.com.nouva.mandajob.R;
import br.com.nouva.mandajob.viewModel.ModelViewNewPayment;

public class NewPaymentActivity extends AppCompatActivity {

    public Context context = this;
    public ModelViewNewPayment model;
    public DrawableCrossFadeFactory factory =
            new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
    private CardView cv_button_plan_gross, cv_button_plan_pro;
    private CardView card_return_add_new_method, cv_button_buy_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_payment);

        init();
        View viewGroup = getWindow().getDecorView().getRootView();
        model = new ModelViewNewPayment(context, viewGroup);

        card_return_add_new_method.setOnClickListener(model);
        cv_button_buy_plan.setOnClickListener(model);
        cv_button_plan_gross.setOnClickListener(model);
        cv_button_plan_pro.setOnClickListener(model);

    }

    private void init() {
        card_return_add_new_method = findViewById(R.id.card_return_add_new_method);
        cv_button_buy_plan = findViewById(R.id.cv_button_buy_plan);
        cv_button_plan_gross = findViewById(R.id.cv_button_plan_gross);
        cv_button_plan_pro = findViewById(R.id.cv_button_plan_pro);
    }
}
