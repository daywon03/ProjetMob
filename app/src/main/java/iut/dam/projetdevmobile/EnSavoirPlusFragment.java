package iut.dam.projetdevmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class EnSavoirPlusFragment extends Fragment {
    public EnSavoirPlusFragment() {
        // Constructeur vide requis
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("En savoir plus");
        return inflater.inflate(R.layout.fragment_en_savoir_plus, container, false);
    }
}

