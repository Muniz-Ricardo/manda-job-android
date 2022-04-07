package br.com.nouva.core.network

import br.com.nouva.core.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Services {
    @POST("/signup")
    suspend fun signup(): Response<Signup>

    @GET("/orders")
    suspend fun queryOrders(): Response<List<ResponseMoreQuery>>

    @GET("/orders")
    suspend fun inbox(): Response<List<Inbox>?>

    @GET("/orders")
    suspend fun discoverMore(): Response<List<DiscoverMore>?>

    @POST("/overview")
    suspend fun overView(@Body keyUser: KeyUser): Response<List<Overview>?>

    @POST("/uris")
    suspend fun getUris(@Body keyUser: KeyUser): Response<Array<QueryUris?>?>

    @POST("/send-asset-chat")
    @Multipart
    suspend fun sendResource(@Part image: MultipartBody.Part?,
                             @Part("file") name: RequestBody): Response<ResponseImage>
}