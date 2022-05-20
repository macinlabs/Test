package com.example.machinetests.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class HomeInteractor {
    companion object {
        private val TAG: String = HomeInteractor::class.java.simpleName
    }

    interface OnFinishedListener {
        fun onResultSuccess(arrNewsUpdates: List<ArticlesItem>)
        fun onResultFail(strError: String)
    }

    fun requestNewsDataAPI(onFinishedListener: OnFinishedListener) {
        val client = OkHttpClient()


        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?sources=bbc-news")
            .header("X-Api-Key", "80290c0a0a8e49e38ed899837c3a3da8")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onFinishedListener.onResultFail("Something went wrong")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")



                   // println(response.body?.string())
                    val obj = JSONObject(response.body!!.string())
                    val articlesItemList: MutableList<ArticlesItem> = mutableListOf()
                    for (i in 0 until obj.getJSONArray("articles").length()) {

                        var nm = ArticlesItem(
                            obj.getJSONArray("articles").getJSONObject(i).getString("title"),
                            obj.getJSONArray("articles").getJSONObject(i).getString("content"),
                            obj.getJSONArray("articles").getJSONObject(i).getString("title")
                        ,false)
                        articlesItemList.add(nm)

                    }
               //     articlesItemList.addAll(articlesItemList)
                    articlesItemList[0].header=true

                    articlesItemList[3].header=true


                    Log.e("size", "" + articlesItemList.size)
                    Handler(Looper.getMainLooper()).post {
                        if (articlesItemList.isNotEmpty()) {
                            onFinishedListener.onResultSuccess(articlesItemList)
                        } else {
                            onFinishedListener.onResultFail("Nothing to show")
                        }
                    }


                }
            }
        })
    }


}