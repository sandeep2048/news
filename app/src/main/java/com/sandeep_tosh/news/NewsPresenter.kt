package com.sandeep_tosh.news


import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NewsPresenter(var view: MainActivity, var context: Context) {

    init {
        view.presenter = this
    }


    fun loadData() {


        view.viewModel?.viewModelScope?.launch {

            view.updateView(apiCall())
        }
    }

    suspend fun apiCall(): List<NewsEntity> {

        return GlobalScope.async(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context,
                NewsDatabase::class.java, "newsDatabase.db"
            ).build()
            if (isNetworkConnectionAvailable(context)) {
                val response = sendGET()

                db.newsDao().deleteAll()

                var parser = NewsParser()
                db.newsDao().insertAll(parser.parseResponse(response))
            }
            return@async db.newsDao().getAll()

        }.await()

    }

    fun isNetworkConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    @Throws(IOException::class)
    private fun sendGET(): String {
        val obj =
            URL("https://newsapi.org/v2/top-headlines?country=us&apiKey=a40bf45fb5bb487c9e4e179d868516ec")
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection
        con.setRequestMethod("GET")
        val responseCode: Int = con.getResponseCode()
        println("GET Response Code :: $responseCode")
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            val `in` = BufferedReader(
                InputStreamReader(
                    con.getInputStream()
                )
            )
            var inputLine: String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            `in`.close()
            // print result
            return response.toString()
            println("madworld" + response.toString())
        } else {
            return ""
            println("GET request not worked")
        }
    }


}