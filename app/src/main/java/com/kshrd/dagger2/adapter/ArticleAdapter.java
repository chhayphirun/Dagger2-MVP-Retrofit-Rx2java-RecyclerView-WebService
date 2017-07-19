package com.kshrd.dagger2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kshrd.dagger2.R;
import com.kshrd.dagger2.entity.Article;
import com.kshrd.dagger2.listener.MyclickListener;

import java.util.List;

/**
 * Created by LIER on 7/10/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {
    private Context context;
    List<Article> articleList;
    MyclickListener myclickListener;


    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article=articleList.get(position);
        holder.title.setText(article.getTitle().toString());
        holder.tvid.setText(article.getId()+"");
        holder.description.setText(article.getDescription());
        /*Picasso.with(context)
                .load("http://"+movie.getPosterUrl())
                .noFade()
                .placeholder(R.drawable.ic_android_black_24dp)
                .error(R.drawable.ic_android_black_24dp)
                .into(holder.imageView);*/
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        myclickListener= (MyclickListener) recyclerView.getContext();
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvid;
        TextView title;
        TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);

            title= (TextView) itemView.findViewById(R.id.tvTitle);
            tvid= (TextView) itemView.findViewById(R.id.tvId);
            description= (TextView) itemView.findViewById(R.id.tvDescription);
            ImageView more= (ImageView) itemView.findViewById(R.id.ivMore);
           /*trailer.setOnClickListener(this);
            imageView.setOnClickListener(this);
*/
        }

        @Override
        public void onClick(View v) {
            /*switch(v.getId()){
                case (R.id.ivThumbnail):
                    myclickListener.onDetailclick(getAdapterPosition(), v);
                    break;
                case (R.id.trailer):
                    myclickListener.onClicked(getAdapterPosition(), v);
                    break;
            }*/
        }
    }
}
