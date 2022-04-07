package br.com.nouva.core.repository

import br.com.nouva.core.data.*
import br.com.nouva.core.network.Services
import br.com.nouva.core.service.safeApiCall
import br.com.nouva.core.utils.ResponseAny
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainRepositoryImpl(private val api: Services) : MainRepository {
    override suspend fun signup(): ResponseAny<Signup> = safeApiCall { api.signup() }
    override suspend fun queryOrders() = safeApiCall { api.queryOrders() }
    override suspend fun inbox() = safeApiCall { api.inbox() }
    override suspend fun discoverMore() = safeApiCall { api.discoverMore() }
    override suspend fun overView(keyUser: KeyUser) = safeApiCall { api.overView(keyUser) }
    override suspend fun getUris(keyUser: KeyUser) = safeApiCall { api.getUris(keyUser) }
    override suspend fun sendResource(image: MultipartBody.Part?, name: RequestBody) =
        safeApiCall { api.sendResource(image, name) }
}