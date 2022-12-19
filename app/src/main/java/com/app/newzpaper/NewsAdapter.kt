package com.app.newzpaper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(val listener: NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<NewsModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_layout_item, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
        listener.onItemClicked(items[viewHolder.adapterPosition])
    }
            return viewHolder

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.newsHeadingView.text = currentItem.heading
        holder.newsTimeView.text = currentItem.time
        holder.newsDateView.text = currentItem.date
        Picasso.get().load(currentItem.imageUrl).into(holder.newsImage)
    }

    override fun getItemCount(): Int {
       return items.size
    }
    fun updateNews(updatedNews:ArrayList<NewsModel>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}
interface  NewsItemClicked{
    fun onItemClicked(item:NewsModel)
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val newsHeadingView:TextView = itemView.findViewById(R.id.newsHeadingTV)
    val newsDateView:TextView = itemView.findViewById(R.id.newsdateTV)
    val newsTimeView:TextView = itemView.findViewById(R.id.newsTimeTV)
    val newsImage: ImageView = itemView.findViewById(R.id.newsImageIV)
}
