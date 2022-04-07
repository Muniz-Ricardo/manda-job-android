package br.com.nouva.core.repository

import br.com.nouva.core.data.*
import br.com.nouva.core.utils.ResponseAny
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MainRepository {
    suspend fun signup(): ResponseAny<Signup>
    suspend fun queryOrders(): ResponseAny<List<ResponseMoreQuery>>
    suspend fun inbox(): ResponseAny<List<Inbox?>?>?
    suspend fun discoverMore(): ResponseAny<List<DiscoverMore?>?>?
    suspend fun overView(keyUser: KeyUser): ResponseAny<List<Overview?>?>?
    suspend fun getUris(keyUser: KeyUser): ResponseAny<Array<QueryUris?>?>?
    suspend fun sendResource(image: MultipartBody.Part?, name: RequestBody): ResponseAny<ResponseImage>
}