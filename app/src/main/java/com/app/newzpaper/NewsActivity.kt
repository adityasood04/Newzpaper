package com.app.newzpaper

import CategoriesAdapter
import android.content.Intent
import android.graphics.Color
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
import kotlin.random.Random

class NewsActivity : AppCompatActivity(), NewsItemClicked {
    lateinit var detailsHeadingTV: TextView
    lateinit var detailsDescriptionTV: TextView
    lateinit var detailsImageIV: ImageView
    lateinit var detailsBackBtn: ImageView
    lateinit var adapter:NewsAdapter
    lateinit var categoriesAdapter:CategoriesAdapter
    lateinit var progressBar:ProgressBar
    lateinit var detailsLL: LinearLayout
    lateinit var readMoreTV: TextView
    lateinit var authorTV: TextView
    lateinit var homeTV: TextView
    lateinit var nationalTV: TextView
    lateinit var sportsTV: TextView
    lateinit var politicsTV: TextView
    lateinit var businessTV: TextView
    lateinit var scienceTV: TextView
    lateinit var scrollView: HorizontalScrollView
     var isDetailsOpened: Boolean  = false
     lateinit var categories:Array<String>
    val url1 = "https://inshorts.deta.dev/news?category=politics"
    val url2 = "https://inshorts.deta.dev/news?category=national"
    val url3 = "https://inshorts.deta.dev/news?category=business"
    val url4 = "https://inshorts.deta.dev/news?category=science"
    val url5 = "https://inshorts.deta.dev/news?category=sports"
    lateinit var url:String
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
        homeTV = findViewById(R.id.homeTV)
        scienceTV = findViewById(R.id.scienceTV)
        politicsTV = findViewById(R.id.politicsTV)
        businessTV = findViewById(R.id.businessTV)
        nationalTV = findViewById(R.id.nationalTV)
        sportsTV = findViewById(R.id.sportsTV)
        rcv = findViewById(R.id.newsRCV)
        scrollView = findViewById(R.id.scrollView)
        scrollView.isHorizontalScrollBarEnabled = false;

        url = url4
        detailsBackBtn.setOnClickListener(View.OnClickListener {
            rcv.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            detailsLL.visibility = View.GONE
            readMoreTV.visibility = View.GONE

        })

        rcv.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this)
        rcv.adapter = adapter
        fetchNews(url3)
        setListeners()





    }

    private fun setListeners() {
        homeTV.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            rcv.visibility = View.GONE
            fetchNews(url3)
            homeTV.setTextColor(Color.RED)
            scienceTV.setTextColor(Color.BLACK)
            politicsTV.setTextColor(Color.BLACK)
            nationalTV.setTextColor(Color.BLACK)
            sportsTV.setTextColor(Color.BLACK)
            businessTV.setTextColor(Color.BLACK)
        })
        scienceTV.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            rcv.visibility = View.GONE
            homeTV.setTextColor(Color.BLACK)
            scienceTV.setTextColor(Color.RED)
            politicsTV.setTextColor(Color.BLACK)
            nationalTV.setTextColor(Color.BLACK)
            sportsTV.setTextColor(Color.BLACK)
            businessTV.setTextColor(Color.BLACK)
            fetchNews(url4)
        })
        politicsTV.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            rcv.visibility = View.GONE
            homeTV.setTextColor(Color.BLACK)
            scienceTV.setTextColor(Color.BLACK)
            politicsTV.setTextColor(Color.RED)
            nationalTV.setTextColor(Color.BLACK)
            sportsTV.setTextColor(Color.BLACK)
            businessTV.setTextColor(Color.BLACK)
            fetchNews(url1)
        })
        nationalTV.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            rcv.visibility = View.GONE
            homeTV.setTextColor(Color.BLACK)
            scienceTV.setTextColor(Color.BLACK)
            politicsTV.setTextColor(Color.BLACK)
            nationalTV.setTextColor(Color.RED)
            sportsTV.setTextColor(Color.BLACK)
            businessTV.setTextColor(Color.BLACK)
            fetchNews(url2)
        })
        sportsTV.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            rcv.visibility = View.GONE
            homeTV.setTextColor(Color.BLACK)
            scienceTV.setTextColor(Color.BLACK)
            politicsTV.setTextColor(Color.BLACK)
            nationalTV.setTextColor(Color.BLACK)
            sportsTV.setTextColor(Color.RED)
            businessTV.setTextColor(Color.BLACK)
            fetchNews(url5)
        })
        businessTV.setOnClickListener(View.OnClickListener {
            rcv.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            homeTV.setTextColor(Color.BLACK)
            scienceTV.setTextColor(Color.BLACK)
            politicsTV.setTextColor(Color.BLACK)
            nationalTV.setTextColor(Color.BLACK)
            sportsTV.setTextColor(Color.BLACK)
            businessTV.setTextColor(Color.RED)
            fetchNews(url3)
        })


    }

    private fun fetchNews(url:String) {
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
                rcv.visibility=View.VISIBLE


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