package com.app.newzpaper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
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
     var isDetailsOpened: Boolean  = false
    val url = "https://inshorts.deta.dev/news?category=science"
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
            readMoreTV.visibility = View.GONE

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
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
            finish()
        })
        queue.add(request)


    }

    override fun onItemClicked(item: NewsModel) {
        isDetailsOpened = true
        rcv.visibility = View.GONE
        progressBar.visibility = View.GONE
        detailsLL.visibility = View.VISIBLE
        Log.i("adi", item.heading)
        Picasso.get().load(item.imageUrl).into(detailsImageIV)
        detailsHeadingTV.text = item.heading
        detailsDescriptionTV.text = item.description
        authorTV.text = "- ${item.authorName}"
        readMoreTV.visibility=View.VISIBLE
        readMoreTV.setOnClickListener(View.OnClickListener {
            val url = item.detailedUrl
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        })

    }
    override fun onBackPressed(){
        if(isDetailsOpened){
            rcv.visibility = View.VISIBLE
            detailsLL.visibility = View.GONE
            readMoreTV.visibility=View.GONE
            isDetailsOpened=false
        }
        else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }



}