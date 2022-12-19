package com.app.newzpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class NewsActivity : AppCompatActivity(), NewsItemClicked {
    lateinit var detailsHeadingTV: TextView
    lateinit var detailsDescriptionTV: TextView
    lateinit var detailsImageIV: ImageView
    lateinit var detailsBackBtn: ImageView
    lateinit var adapter:NewsAdapter
    lateinit var progressBar:ProgressBar
    lateinit var detailsLL: LinearLayout
    lateinit var readMoreTV: TextView
    lateinit var authorTV: TextView
    val url = "https://inshorts.deta.dev/news?category=sports"
    lateinit var rcv:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        progressBar = findViewById(R.id.progressBar)
        detailsLL = findViewById(R.id.detailsLL)
        readMoreTV = findViewById(R.id.readMoreTV)
        detailsHeadingTV = findViewById(R.id.detailsHeadingTV)
        detailsDescriptionTV = findViewById(R.id.detailsDescriptionTV)
        detailsImageIV = findViewById(R.id.detailsImageIV)
        detailsBackBtn = findViewById(R.id.detailsBackBtn)
        authorTV = findViewById(R.id.authorTV)

        detailsBackBtn.setOnClickListener(View.OnClickListener {
            rcv.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            detailsLL.visibility = View.GONE

        })



        progressBar.visibility = View.VISIBLE

        rcv = findViewById(R.id.newsRCV)
        rcv.layoutManager = LinearLayoutManager(this)
        fetchNews()
        adapter = NewsAdapter(this)
        rcv.adapter = adapter
    }

    private fun fetchNews() {
        val queue :RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{response->
            try {
                val newsJsonArray = response.getJSONArray("data")
                val newsArray = ArrayList<NewsModel>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsModel(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("content"),
                        newsJsonObject.getString("date"),
                        newsJsonObject.getString("time"),
                        newsJsonObject.getString("imageUrl"),
                        newsJsonObject.getString("readMoreUrl"),
                        newsJsonObject.getString("author")
                    )
                    newsArray.add(news)
                }
                adapter.updateNews(newsArray)
                progressBar.visibility = View.GONE


            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }, Response.ErrorListener {
            Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(request)


    }

    override fun onItemClicked(item: NewsModel) {
        rcv.visibility = View.GONE
        progressBar.visibility = View.GONE
        detailsLL.visibility = View.VISIBLE
        Log.i("adi", item.heading)
        Picasso.get().load(item.imageUrl).into(detailsImageIV)
        detailsHeadingTV.text = item.heading
        detailsDescriptionTV.text = item.description

        authorTV.text = "- ${item.authorName}"

    }
}