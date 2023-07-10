package com.example.pets_donation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pets_donation.R;
import com.example.pets_donation.SlideAdapter;

public class AdotanteInformacoes_Fragment extends Fragment {

    //declaracao das variaveis
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    private Button mNextBtn;
    private Button mBackBtn;
    private int mCurrentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adotante_informacoes_, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSlideViewPager = (ViewPager) view.findViewById(R.id.view);
        mDotLayout = (LinearLayout) view.findViewById(R.id.linear);
        mNextBtn = (Button) view.findViewById(R.id.btnContinuar1);
        mBackBtn = (Button) view.findViewById(R.id.btnVoltar1);

        //aparecer slide na tela
        slideAdapter = new SlideAdapter(getActivity());
        mSlideViewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    /**
     * Funcao para adicionar slide
     * @param position posicao
     */
    public void addDotsIndicator(int position) {
        mDots = new TextView[11];
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
     * Ver os slides
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

                mNextBtn.setText("");
                mBackBtn.setText("");
            } else if (i == mDots.length - 1) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("");
                mBackBtn.setText("");
            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("");
                mBackBtn.setText("");
            }

        }


        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}