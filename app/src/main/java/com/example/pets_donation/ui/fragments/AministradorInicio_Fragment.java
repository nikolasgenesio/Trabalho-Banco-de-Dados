package com.example.pets_donation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pets_donation.Funcionario;
import com.example.pets_donation.R;
import com.example.pets_donation.SlideAdministradorAdapter;

public class AministradorInicio_Fragment extends Fragment {

    //declaracao das variaveis
    private ViewPager mSlideViewPager;
    private TextView textView;
    private LinearLayout mDotLayout;
    private SlideAdministradorAdapter slideAdapter;
    private TextView[] mDots;
    private Button mNextBtn;
    private Button mBackBtn;
    private int mCurrentPage;
    private Funcionario funcionario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tela Inicial");
        this.funcionario = (Funcionario) getActivity().getIntent().getSerializableExtra("funcionario");
        Log.i("Funcionario Inicio", "NOME: " + funcionario.getNome());
        return inflater.inflate(R.layout.fragment_aministrador_inicio_, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSlideViewPager = (ViewPager) view.findViewById(R.id.viewPag);
        mDotLayout = (LinearLayout) view.findViewById(R.id.linearLay);
        mNextBtn = (Button) view.findViewById(R.id.btnContinuar2);
        mBackBtn = (Button) view.findViewById(R.id.btnVoltar2);
        textView = (TextView) view.findViewById(R.id.esc);

        //exibicao dos slides
        slideAdapter = new SlideAdministradorAdapter(getActivity());
        mSlideViewPager.setAdapter(slideAdapter);
        textView.setText("Não compartilhe os dados!");

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    /**
     * Funcao para adicionar slide
     * @param position posicao
     */
    public void addDotsIndicator(int position) {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {

            mDots[i] = new TextView(getActivity());
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.laranja));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorHint));
        }
    }

    /**
     * Exibir slides
     */
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPage = i;

            if (i == 0) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                textView.setText("Não compartilhe os dados!");
                mNextBtn.setText("");
                mBackBtn.setText("");
            } else if (i == 1) {
                textView.setText("Verifique a consistência dos dados!");
            } else if (i == mDots.length - 1) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.INVISIBLE);
                textView.setText("Cumpra as regulamentações!");
                mNextBtn.setText("");
                mBackBtn.setText("");
            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.INVISIBLE);
                textView.setText("Implemente medidas de segurança!");
                mNextBtn.setText("");
                mBackBtn.setText("");
            }

        }


        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}