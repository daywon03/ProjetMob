package iut.dam.projetdevmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MonHabitatFragment extends Fragment {
    public MonHabitatFragment() {
        // Constructeur vide requis
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Mon habitat");
        return inflater.inflate(R.layout.fragment_mon_habitat, container, false);
    }
}

