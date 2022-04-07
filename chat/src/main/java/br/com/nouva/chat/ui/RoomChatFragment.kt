package br.com.nouva.chat.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.nouva.chat.adapter.ChatAdapter
import br.com.nouva.chat.databinding.FragmentRoomChatBinding
import br.com.nouva.core.*
import br.com.nouva.core.data.Messages
import br.com.nouva.core.module.AppModule
import br.com.nouva.core.utils.KoinUtilities
import br.com.nouva.core.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RoomChatFragment : Fragment() {

    private val loadModules by lazy { KoinUtilities.loadModules(AppModule.eachModules()) }
    private fun injectModules() = loadModules
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentRoomChatBinding
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectModules()
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainViewModel.setUri("")
                    findNavController().popBackStack()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentRoomChatBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        initObservers()

        return binding.root
    }

    private fun initView() {
        mainViewModel.getOverview(
            mainViewModel.keyUser.value!!
        )

        setAdapter(listOf(Messages(
            message = "Ola, tudo bem?",
            sender = Sender.OTHER))
        )

        binding.cvBtnSendMessage.setOnClickListener {
            adapter.addMessage(Messages(
                message = binding.edtMessage.text.tos(),
                sender = Sender.ME
            ))
            runDelay {
                if (it) binding.recyclerChat
                    .smoothScrollToPosition(0)
                binding.edtMessage.text.clear()
            }
        }

        binding.cvButtonAttach.setOnClickListener {
            val intent = Intent(requireContext().externalMediaDirs[0].path,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    private fun initObservers() {
        mainViewModel.file.observe(viewLifecycleOwner) {
            createAsFile(requireContext(), it) { isDone ->
                if (isDone) {
                    mainViewModel.setResource(
                        mainViewModel.getAssetRead(
                            "image.png", requireContext()
                        ), "8bb07e15c9019f8cuye98363nfo39y333-8cacfd8023beab66"
                    )
                }
            }
        }

        mainViewModel.isLoad.observe(viewLifecycleOwner) {
            binding.pgbChatRoom.visibility = if (it) View.GONE else View.VISIBLE
        }

        mainViewModel.resImage.observe(viewLifecycleOwner) {
            adapter.addMessage(
                Messages(
                    message = it.message,
                    sender = Sender.ME,
                    type = Type.IMAGE
                )
            )
        }

        mainViewModel.overview.observe(viewLifecycleOwner) {
            launchImage(it?.get(0)?.profile, 200, requireContext()) { bitmap ->
                binding.ivPicUserChat.setImageBitmap(bitmap)
                binding.tvTagNameChat.text = it?.get(0)?.name.lowerString()
                binding.tvNameUserChat.text = it?.get(0)?.name
            }
        }
    }

    private fun setAdapter(messages: List<Messages?>) {
        val manager = LinearLayoutManager(requireContext())
        val mutable: MutableList<Messages?> = ArrayList(messages)
        adapter = ChatAdapter(requireContext(), mutable)
        manager.stackFromEnd = false
        manager.reverseLayout = true
        binding.recyclerChat.adapter = adapter
        binding.recyclerChat.layoutManager = manager
        mainViewModel.setChat(mutable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val uri = fromUri(data?.data!!) // TODO [ROUTE IMAGE]
                mainViewModel.setUri(uri)
            }
        }
    }
}