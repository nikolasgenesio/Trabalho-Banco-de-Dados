package com.example.pets_donation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    //declaracao das variaveis

    Context context;
    LayoutInflater layoutInflater;


    public SlideAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.dica1,
            R.drawable.dica2,
            R.drawable.dica3,
            R.drawable.dica4,
            R.drawable.dica5,
            R.drawable.dica6,
            R.drawable.dica7,
            R.drawable.dica8,
            R.drawable.dica11,
            R.drawable.dica10,
            R.drawable.dica9
    };

    public String[] slide_headings = {
            "dica1", "dica2", "dica3", "dica4", "dica5", "dica6", "dica7", "dica8", "dica9", "dica10", "dica11"
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
