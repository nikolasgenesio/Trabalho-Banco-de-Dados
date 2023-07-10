package com.example.pets_donation.Adocao;

import com.example.pets_donation.Adotante;
import com.example.pets_donation.ui.fragments.AdotanteAdocao_Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapter extends FragmentStateAdapter {

    public VPAdapter(@NonNull AdotanteAdocao_Fragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new fragment2Adocao();
            default:
                return new fragment1Adocao();
        }
    }

    /**
     * Funcao para retornar o tamanho do TabLayout
     * @return tamanho
     */
    @Override
    public int getItemCount() {
        return 2;
    }

}
