package br.com.nouva.chat.messages

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.chat.databinding.ItemChatAssetMeBinding
import br.com.nouva.chat.databinding.ItemChatAssetOtherBinding
import br.com.nouva.chat.databinding.ItemChatMeBinding
import br.com.nouva.core.launchImage

abstract class MessageViewHolder<in T>(itemView: ViewDataBinding)
    : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: T)

    class SelfMessageText(val view: ViewDataBinding) :
        MessageViewHolder<MessageItemUi>(view) {
        override fun bind(item: MessageItemUi) {
            view as ItemChatMeBinding
            view.tvMessage.text = item.content
        }
    }

    class SelfMessageImage(val view: ViewDataBinding) :
        MessageViewHolder<MessageItemUi>(view) {
        override fun bind(item: MessageItemUi) {
            launchImage(item.content, 400, view.root.context) {
                view as ItemChatAssetMeBinding
                view.ivResImageChat.setImageBitmap(it)
            }
        }
    }

    class ReceivedMessageText(val view: ViewDataBinding) :
        MessageViewHolder<MessageItemUi>(view) {
        override fun bind(item: MessageItemUi) {
            view as ItemChatMeBinding
            view.tvMessage.text = item.content
        }
    }

    class ReceivedMessageImage(val view: ViewDataBinding) :
        MessageViewHolder<MessageItemUi>(view) {
        override fun bind(item: MessageItemUi) {
            launchImage(item.content, 400, view.root.context) {
                view as ItemChatAssetOtherBinding
                view.ivResImageChat.setImageBitmap(it)
            }
        }
    }
}