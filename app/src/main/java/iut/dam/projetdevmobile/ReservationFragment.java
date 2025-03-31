package iut.dam.projetdevmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ReservationFragment extends Fragment {
    public ReservationFragment() {
        // Constructeur vide requis
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Réservations");
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }
}

