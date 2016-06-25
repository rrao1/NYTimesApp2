package com.codepath.nytimessearch1;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by ramyarao on 6/20/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {
    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for position
        Article article = this.getItem(position);
        //check to see if existing view is being reused
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }


        //not using a recycled view -> inflate the layout

        //find the image view
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        //clear out recycled image from convertView from last time
        imageView.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadline());
        String thumbnail = article.getThumbNail();

        if (!TextUtils.isEmpty(thumbnail)) {
            Glide.with(getContext()).load(thumbnail).into(imageView);
        }
        else {
            //imageView.setImageResource(@drawable);
            //Picasso.with(getContext()).load("https://i.imgur.com/UhM2NYI.png").into(imageView);
        }
        return convertView;
        //populate the thumbnail image
        //remote download the image in the background
    }
}