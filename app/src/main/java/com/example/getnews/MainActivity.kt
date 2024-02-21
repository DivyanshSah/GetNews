package com.example.getnews

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), newsItemClicked {
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recyclerView)
        fetch()
        recyclerView.layoutManager=LinearLayoutManager(this)
        adapter= NewsListAdapter(this)
        recyclerView.adapter=adapter

    }

    private fun fetch(){
        val url="https://newsapi.org/v2/top-headlines?country=in&apiKey=e76caecec1094da889ecfccbc7a8c003&pageSize=32"
//        val queue = MySingleton.getInstance(this.applicationContext).requestQueue
        val jsonObjectRequest =object: JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                // Success block
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                adapter.updateNews(newsArray)
            },
            {
                // Error block
                // Handle error, e.g., log it or notify the user
                Log.e("Volley Error", it.toString())
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(item: News) {
//        Toast.makeText(this,"Item clicked is $item",Toast.LENGTH_LONG).show()

        val intent = CustomTabsIntent.Builder()
            .build()
        intent.launchUrl(this@MainActivity, Uri.parse(item.url))
    }
}