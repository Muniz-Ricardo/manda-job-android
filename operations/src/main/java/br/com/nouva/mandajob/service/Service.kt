package br.com.nouva.mandajob.service

import br.com.nouva.mandajob.body.BodySearch
import br.com.nouva.mandajob.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {
    @POST("/search")
    fun moreProjects(@Body body: BodySearch?): Call<List<ModelMoreSearch>>

    @POST("/search")
    fun seeInboxUser(@Body body: BodySearch?): Call<List<ModelInbox?>?>?

    @POST("/search")
    fun viewMoreProjects(@Body body: BodySearch?): Call<List<ModelMoreDiscover?>?>?

    @POST("/overview")
    fun seeOverview(@Body key: Overview?): Call<List<ModelOverview?>?>?

    @POST("/urls")
    fun getUrls(@Body key: Urls?): Call<List<ModelUrls?>?>?
}