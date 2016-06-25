package com.codepath.nytimessearch1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.nytimessearch1.activities.ArticleActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ramyarao on 6/22/16.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ArticleAdapterRV extends
        RecyclerView.Adapter<ArticleAdapterRV.ViewHolder> {
    //@BindView(R.id.tvTitle) TextView tvTitle;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.ivImage) ImageView imageView;
        @BindView(R.id.tvTitle) TextView tvTitle;
        //@BindView(R.id.tvTitle) ImageView imageView;
        //public ImageView imageView;
        //public TextView tvTitle;
        private Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);
             //imageView = (ImageView) itemView.findViewById(R.id.ivImage);


             //tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.context = context;
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);

//            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
//            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            //create an intent to display article
                Intent i = new Intent(context, ArticleActivity.class);
                //get the article to display
                Article article = mArticles.get(position);
                //i.putExtra("url", article.getWebUrl());

                //pass in article into intent
                i.putExtra("article", Parcels.wrap(article));
                //launch activity
                //Context context
                view.getContext().startActivity(i);

            // We can access the data within the views
            //Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
        }
    }

    // Store a member variable for the contacts
    private  List<Article> mArticles;
    // Store the context for easy access
    private  Context mContext;

    // Pass in the contact array into the constructor
    public ArticleAdapterRV(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticleAdapterRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(getContext(), contactView);
        return viewHolder;
    }

//    @Override public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder holder;
//        if (view != null) {
//            holder = (ViewHolder) view.getTag();
//        } else {
//            view = inflater.inflate(R.layout.whatever, parent, false);
//            holder = new ViewHolder(view);
//            view.setTag(holder);
//        }
//        holder.name.setText("John Doe");
//        // etc...
//        return view;
//    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapterRV.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on your views and data model
        TextView textView1 = viewHolder.tvTitle;
        textView1.setText(article.getHeadline());

        ImageView imageView1 = viewHolder.imageView;
        imageView1.setImageResource(0);
        //Button button = viewHolder.messageButton;
        //button.setText("Message");
        String thumbnail = article.getThumbNail();
        if (!TextUtils.isEmpty(thumbnail)) {
            //Picasso.with(getContext()).load(thumbnail).into(imageView1);
            Glide.with(getContext())
                    .load(thumbnail)
                    .into(imageView1);
        }
        else {
            imageView1.setImageResource(R.drawable.news_item);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}