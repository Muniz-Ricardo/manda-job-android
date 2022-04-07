package br.com.nouva.core.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import br.com.nouva.core.data.*
import br.com.nouva.core.repository.MainRepository
import br.com.nouva.core.service.ServiceGenerator
import br.com.nouva.core.service.ServiceInterceptor
import br.com.nouva.core.utils.read
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainViewModel(
    application: Application,
    private val repository: MainRepository,
    private val service: ServiceGenerator
) : AndroidViewModel(application),
    ServiceInterceptor.OnServiceResponseListener {

    private val _orders: MutableLiveData<List<ResponseMoreQuery>?> = MutableLiveData()
    val orders: LiveData<List<ResponseMoreQuery>?> get() = _orders

    private val _discover: MutableLiveData<List<DiscoverMore?>?> = MutableLiveData()
    val discover: LiveData<List<DiscoverMore?>?> get() = _discover

    private val _overview: MutableLiveData<List<Overview?>?> = MutableLiveData()
    val overview: LiveData<List<Overview?>?> get() = _overview

    private val _messages: MutableLiveData<List<Messages?>?> = MutableLiveData()
    val messages: LiveData<List<Messages?>?> get() = _messages

    private val _keyUser: MutableLiveData<KeyUser> = MutableLiveData()
    val keyUser: LiveData<KeyUser> get() = _keyUser

    private val _isLoad: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoad: LiveData<Boolean> get() = _isLoad

    private val _file: MutableLiveData<String> = MutableLiveData()
    val file: LiveData<String> get() = _file

    private val _isShow: MutableLiveData<Boolean> = MutableLiveData(true)
    val isShow: LiveData<Boolean> get() = _isShow

    private val _uriSelected: MutableLiveData<String?> = MutableLiveData()
    val uriSelected: LiveData<String?> get() = _uriSelected

    private val _last: MutableLiveData<Int?> = MutableLiveData()
    val last: LiveData<Int?> get() = _last

    private val _resImage: MutableLiveData<ResponseImage> = MutableLiveData()
    val resImage: LiveData<ResponseImage> get() = _resImage

    private val _shots: MutableLiveData<Array<QueryUris?>?> = MutableLiveData()
    val shots: LiveData<Array<QueryUris?>?> get() = _shots

    private val _isFileSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isFileSuccess: LiveData<Boolean> get() = _isFileSuccess

    private val _auth: MutableLiveData<Signup> = MutableLiveData()
    val auth: LiveData<Signup> get() = _auth

    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String> get() = _email

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: LiveData<String> get() = _password

    private val _isSignup: MutableLiveData<Boolean> = MutableLiveData()
    val isSignup: LiveData<Boolean> get() = _isSignup

    private val _isSignupButton: MutableLiveData<Boolean> = MutableLiveData()
    val isSignupButton: LiveData<Boolean> get() = _isSignupButton

    fun signup(key: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            service.key(key)
            repository.signup()
        }.read({
            _auth.value = it
        }, {
            Log.e("", "Error mainViewModel - ${it.message}")
        })
    }

    fun getOrdersByQuery(token: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            service.token(token)
            repository.queryOrders()
        }.read({
            _orders.value = it
        }, {
            Log.e("", "Error mainViewModel - ${it.message}")
        })
    }

    fun getDiscoverArea(token: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            service.token(token)
            repository.discoverMore()
        }?.read({
            _discover.value = it
        }, {
            Log.e("", "Error mainViewModel - ${it.message}")
        })
    }

    fun getOverview(keyUser: KeyUser) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.overView(keyUser)
        }?.read({
            _overview.value = it
            _isLoad.value = true
        }, {
            Log.e("", "Error mainViewModel - ${it.message}")
        })
    }

    fun getOverviewUris(keyUser: KeyUser) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.getUris(keyUser)
        }?.read({
            _shots.value = it
        }, {
            Log.e("", "Error mainViewModel - ${it.message}")
        })
    }

    private fun postResource(
        part: MultipartBody.Part?,
        body: RequestBody,
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.sendResource(part, body)
        }.read({
            _isFileSuccess.value = true
            _resImage.value = it
        }, {
            Log.e("", "Error mainViewModel - ${it.message}")
        })
    }

    fun setResource(file: File?, tag: String?) {
        val reqBody = file
            ?.asRequestBody("image/png".toMediaTypeOrNull())

        val resourcePart = reqBody?.let {
            MultipartBody.Part.createFormData(
                "file",
                file.name, it
            )
        }

        postResource(resourcePart, getMultiPartReqBody(tag)!!)
    }

    private fun getMultiPartReqBody(tag: String? = null):
            RequestBody? = tag?.toRequestBody(MultipartBody.FORM)

    fun getAssetRead(target: String, context: Context): File {
        return File(
            "${
                context.externalMediaDirs[0].absolutePath
            }/files/$target"
        )
    }

    fun isSignupButton(isEnable: Boolean) {
        _isSignup.value = isEnable
    }

    fun isSignup(isDone: Boolean) {
        _isSignup.value = isDone
    }

    fun setEmailOrPassword(email: String? = null,
                           password: String? = null) {
        if (!email.isNullOrBlank()) _email.value = email
        if (!password.isNullOrBlank()) _password.value = password
    }

    fun setUri(file: String) {
        _file.value = file
    }

    fun setKeyUser(keyUser: KeyUser) {
        _keyUser.value = keyUser
    }

    fun setUriPreview(uri: String?) {
        _uriSelected.value = uri
    }

    fun setLastImage(position: Int) {
        _last.value = position
    }

    fun setIsIconChange(isChange: Boolean) {
        _isShow.value = isChange
    }

    fun setChat(messages: MutableList<Messages?>) {
        _messages.value = messages
    }

    override fun onReceiveResponseCode(code: Int) {}

}