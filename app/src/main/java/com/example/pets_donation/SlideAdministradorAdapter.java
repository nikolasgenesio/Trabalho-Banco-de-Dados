package com.example.pets_donation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdministradorAdapter extends PagerAdapter  {
    //declaracao das variaveis
    Context context;
    LayoutInflater layoutInflater;


    public SlideAdministradorAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.administrador1,
            R.drawable.administrador2,
            R.drawable.administrador3,
            R.drawable.administrador4
    };

    public String[] slide_headings = {
            "dica1", "dica2", "dica3", "dica4"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImagemView = (ImageView) view.findViewById(R.id.imageView2);

        slideImagemView.setImageResource(slide_images[position]);

        container.addView(view);
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }
}
