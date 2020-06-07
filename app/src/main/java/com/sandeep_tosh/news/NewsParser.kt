package com.sandeep_tosh.news

import org.json.JSONObject

class NewsParser() {


    fun parseResponse( response:String):List<NewsEntity>{

        var jsonObject=JSONObject(response)

        var list= ArrayList<NewsEntity>()
        var size:Int=jsonObject.getJSONArray("articles").length()
        for(i in 0..size-1){
            var publisher= jsonObject.getJSONArray("articles").getJSONObject(i).getJSONObject("source").getString("name")
            var title=jsonObject.getJSONArray("articles").getJSONObject(i).getString("title")
            var desc=jsonObject.getJSONArray("articles").getJSONObject(i).getString("description")
            var urltoImage=jsonObject.getJSONArray("articles").getJSONObject(i).getString("urlToImage")
            var date=jsonObject.getJSONArray("articles").getJSONObject(i).getString("publishedAt")


            list.add(NewsEntity(title,desc,date,publisher,urltoImage))

        }



        return list
    }
}