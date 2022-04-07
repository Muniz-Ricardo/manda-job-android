package br.com.nouva.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.chat.R
import br.com.nouva.chat.databinding.ItemChatAssetMeBinding
import br.com.nouva.chat.databinding.ItemChatAssetOtherBinding
import br.com.nouva.chat.databinding.ItemChatMeBinding
import br.com.nouva.chat.databinding.ItemChatOtherBinding
import br.com.nouva.core.*
import br.com.nouva.core.data.Messages

class ChatAdapter(
    private val context: Context,
    private var messages: MutableList<Messages?>? = null,
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ME: Int = 20
        private const val VIEW_TYPE_OTHER: Int = 40
        private const val VIEW_TYPE_ASSET_ME: Int = 60
        private const val VIEW_TYPE_ASSET_OTHER: Int = 80
    }

    fun addMessage(message: Messages) {
        with(messages) {
            messages?.let {
                this?.add(0, message)
                notifyItemInserted(0)
            }
        }
    }

    fun clear() {
        with(messages) {
            messages?.let {
                this?.clear()
                this?.addAll(arrayListOf())
                this?.removeAll(messages!!)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_ME -> {
                ViewHolder(DataBindingUtil.inflate(
                    inflater, R.layout.item_chat_me,
                    parent, false), VIEW_TYPE_ME, context)
            }

            VIEW_TYPE_OTHER -> {
                ViewHolder(DataBindingUtil.inflate(
                    inflater, R.layout.item_chat_other,
                    parent, false), VIEW_TYPE_OTHER, context)
            }

            VIEW_TYPE_ASSET_OTHER -> {
                ViewHolder(DataBindingUtil.inflate(
                    inflater, R.layout.item_chat_asset_other,
                    parent, false), VIEW_TYPE_ASSET_OTHER, context)
            }

            VIEW_TYPE_ASSET_ME -> {
                ViewHolder(DataBindingUtil.inflate(
                    inflater, R.layout.item_chat_asset_me,
                    parent, false), VIEW_TYPE_ASSET_ME, context)
            }

            else -> ViewHolder(null, null, context)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        messages?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int {
        when (val message = messages?.get(position)) {
            is Messages -> {
                when (message.sender) {
                    Sender.OTHER -> {
                        return if (message.type == Type.IMAGE)
                            VIEW_TYPE_ASSET_OTHER else VIEW_TYPE_OTHER
                    }
                    Sender.ME -> {
                        return if (message.type == Type.IMAGE)
                            VIEW_TYPE_ASSET_ME else VIEW_TYPE_ME
                    }

                }
            }
        }
        return -1
    }

    override fun getItemCount(): Int = messages?.size!!

    class ViewHolder(
        private val binding: ViewDataBinding? = null,
        private val item: Int?,
        private val context: Context?,
    ) :
        RecyclerView.ViewHolder(binding?.root!!) {
        fun bind(data: Messages) {

            when (item) {
                VIEW_TYPE_ASSET_OTHER -> {
                    binding as ItemChatAssetOtherBinding
                    binding.ivResImageChat.fadeIn {
                        revealAnimLoad(binding.layMasterAsset, binding.reveal, context!!)
                        if (it) {
                            launchImage(data.message, 512, context) { bitmap ->
                                binding.ivResImageChat.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
                VIEW_TYPE_ASSET_ME -> {
                    binding as ItemChatAssetMeBinding
                    binding.ivResImageChat.fadeIn {
                        revealAnimLoad(binding.layMasterAsset, binding.reveal, context!!)
                        if (it) {
                            launchImage(data.message, 512, context) { bitmap ->
                                binding.ivResImageChat.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
                VIEW_TYPE_ME -> {
                    binding as ItemChatMeBinding
                    binding.tvMessage.text = data.message
                }
                VIEW_TYPE_OTHER -> {
                    binding as ItemChatOtherBinding
                    binding.tvMessage.text = data.message
                }
            }
        }
    }
}