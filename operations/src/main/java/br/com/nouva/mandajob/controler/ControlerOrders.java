package br.com.nouva.mandajob.controler;

import android.content.Context;

import java.util.List;
import java.util.Objects;

import br.com.nouva.mandajob.model.ModelMoreSearch;

@SuppressWarnings({"ALL", "FieldMayBeFinal"})
public class ControlerOrders {

    private Context context;

    public ControlerOrders(Context context) {
        this.context = context;
    }

    public boolean checkListUpdate(List<ModelMoreSearch> response, List<ModelMoreSearch> list) {
        if (!Objects.requireNonNull(response).containsAll(list)) {
            int count = 0;
            for (int i = 0; i < response.size(); i++) {

                if (!response.get(i).getName().equals(list.get(i).getName()) ||
                        !response.get(i).getProfile().equals(list.get(i).getProfile()) ||
                        !response.get(i).getContent().equals(list.get(i).getContent()) ||
                        !response.get(i).getTitle().equals(list.get(i).getTitle())) {

                    count++;

                    if (count == 1) return true;
                }
            }
        }
        return false;
    }

}
