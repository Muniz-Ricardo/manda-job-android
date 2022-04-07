package br.com.nouva.mandajob.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import br.com.nouva.mandajob.R;
import br.com.nouva.mandajob.activity.WalletActivity;

public class ModelViewNewPayment implements View.OnClickListener {

    protected static int SELECTED_PLAN = 0;

    protected LinearLayout planGross, planPro;
    protected CardView labelCardGross, labelCardPro;

    protected Context context;
    protected View view;

    public ModelViewNewPayment(Context context, View view) {
        this.context = context;
        this.view = view;
        init();
    }

    private void init() {
        planGross = view.findViewById(R.id.layout_plan_gross);
        planPro = view.findViewById(R.id.layout_plan_pro);
        labelCardGross = view.findViewById(R.id.cv_card_plan_gross);
        labelCardPro = view.findViewById(R.id.cv_card_plan_pro);
        selectPlan(1);
    }

    private void selectPlan(int item) {
        if (SELECTED_PLAN != item) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fadein);
            if (item == 1) {
                planGross.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_card_plan_selected));
                labelCardGross.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorSecondary));
                planGross.startAnimation(animation);
                labelCardGross.startAnimation(animation);

                planPro.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_card_plan_unselected));
                labelCardPro.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorDarkBlue));
            } else if (item == 2) {
                planPro.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_card_plan_selected));
                labelCardPro.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorSecondary));
                planPro.startAnimation(animation);
                labelCardPro.startAnimation(animation);

                planGross.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_card_plan_unselected));
                labelCardGross.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorDarkBlue));
            }
        }
    }

    public void finalizeUi() {
        Activity activity = (Activity) view.getContext();
        activity.finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.card_return_add_new_method) {
            finalizeUi();
        } else if (id == R.id.cv_button_buy_plan) {
            Intent intent = new Intent(context, WalletActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.cv_button_plan_gross) {
            selectPlan(1);
        } else if (id == R.id.cv_button_plan_pro) {
            selectPlan(2);
        }
    }
}
