package br.com.nouva.mandajob.viewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import br.com.nouva.mandajob.R;
import br.com.nouva.mandajob.controler.ControlerViewProfile;

public class ModelViewProfileView implements View.OnClickListener {

    public Context context;
    public ControlerViewProfile controler;

    public ModelViewProfileView(Context context, ControlerViewProfile controler) {
        this.context = context;
        this.controler = controler;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.layout_button_label_personal) {
            controler.translateTextCard();
        } else if (id == R.id.layout_button_exit) {
        } else if (id == R.id.layout_button_work_show) {
            controler.changeTabSelection(1);
        } else if (id == R.id.layout_button_about_show) {
            controler.changeTabSelection(2);
        }
    }
}
