package iut.dam.projetdevmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HabitatAdapter extends BaseAdapter {
    private Context context;
    private List<Habitat> habitatList;

    public HabitatAdapter(Context context, List<Habitat> habitatList) {
        this.context = context;
        this.habitatList = habitatList;
    }

    @Override
    public int getCount() {
        return habitatList.size();
    }

    @Override
    public Object getItem(int position) {
        return habitatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_habitat, parent, false);
        }

        Habitat habitat = habitatList.get(position);

        TextView userName = convertView.findViewById(R.id.habitat_user);
        TextView floorText = convertView.findViewById(R.id.habitat_floor);
        TextView equipmentText = convertView.findViewById(R.id.habitat_equipment);

        userName.setText("Utilisateur : " + habitat.getUser());
        floorText.setText("Étage : " + habitat.getFloor());

        // Récupération des équipements sous forme de liste
        StringBuilder equipmentList = new StringBuilder();
        for (String item : habitat.getEquipment()) {
            equipmentList.append(item).append(", ");
        }

        // Retirer la dernière virgule
        if (equipmentList.length() > 0) {
            equipmentList.setLength(equipmentList.length() - 2);
        }

        equipmentText.setText("Équipements : " + equipmentList.toString());

        return convertView;
    }
}
