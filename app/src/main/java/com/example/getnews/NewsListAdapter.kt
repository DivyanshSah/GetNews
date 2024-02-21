package com.example.getnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listener: newsItemClicked): RecyclerView.Adapter<NewsViewHolder> (){

    private val item:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClick(item[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current =item[position]
        holder.titleView.text=current.title
        holder.author.text=current.author
        Glide.with(holder.itemView.context).load(current.imageurl).into(holder.image)
    }

    fun updateNews(updatedNews:ArrayList<News>){
        item.clear()
        item.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView:TextView=itemView.findViewById(R.id.tvTitle)
    val image:ImageView=itemView.findViewById(R.id.imageView)
    val author:TextView=itemView.findViewById(R.id.author)
}

interface newsItemClicked{
    fun onItemClick(item:News)
//    abstract fun <JsonObjectRequest> JsonObjectRequest(get: Int, url: String, nothing: Nothing?): JsonObjectRequest
}