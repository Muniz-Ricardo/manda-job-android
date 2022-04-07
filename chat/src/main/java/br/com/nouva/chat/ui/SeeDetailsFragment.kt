package br.com.nouva.chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.nouva.chat.R
import br.com.nouva.chat.adapter.AdapterShotsUrl
import br.com.nouva.chat.databinding.FragmentSeeDetailsBinding
import br.com.nouva.core.*
import br.com.nouva.core.data.KeyUser
import br.com.nouva.core.data.QueryUris
import br.com.nouva.core.module.AppModule
import br.com.nouva.core.utils.KoinUtilities
import br.com.nouva.core.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SeeDetailsFragment : Fragment() {

    private val loadModules by lazy { KoinUtilities.loadModules(AppModule.eachModules()) }
    private fun injectModules() = loadModules
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentSeeDetailsBinding
    private lateinit var adapter: AdapterShotsUrl

    private var resource: ArrayList<Int> = arrayListOf(
        R.color.colorSecondary,
        R.drawable.ic_arrow_dowm_secondary
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        injectModules()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentSeeDetailsBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        initObservers()

        return binding.root
    }

    private fun initView() {
        mainViewModel.setKeyUser(
            KeyUser(requireActivity()
                .getIntentExtras("key").tos()
            )
        )

        binding.layIconMore.setOnClickListener {
            showContent(binding.tvMoreDetails,
                binding.ivIconMore, resource) { isDone ->
                if (isDone) {
                    animRotation(binding.ivIconMore, binding.layMoreTextOverview)
                }
            }
        }

        binding.cvButtonChat.setOnClickListener {
            findNavController().navigate(
                R.id.action_seeDetailsFragment_to_chat_nav_graph
            )
        }
    }

    private fun initObservers() {
        mainViewModel.keyUser.observe(viewLifecycleOwner) {
            mainViewModel.getOverview(it)
            mainViewModel.getOverviewUris(it)
        }

        mainViewModel.uriSelected.observe(viewLifecycleOwner) {
            launchImage(it, 800, requireContext()) { bitmap ->
                binding.ivImgPreview.setImageBitmap(bitmap)
            }
        }

        mainViewModel.shots.observe(viewLifecycleOwner) {
            setAdapter(it)
        }

        mainViewModel.isShow.observe(viewLifecycleOwner) {
            resource = when {
                it -> {
                    arrayListOf(R.color.colorSecondary, R.drawable.ic_arrow_dowm_secondary)
                }
                !it -> {
                    arrayListOf(R.color.colorWhite, R.drawable.ic_arrow_dowm_large)
                }
                else -> TODO()
            }
        }

        mainViewModel.overview.observe(viewLifecycleOwner) {
            it?.forEach { over ->
                launchImage(over?.profile, 200, requireContext()) { profile ->
                    binding.ivPicUserDetails.setImageBitmap(profile)
                }

                launchImage(over?.wallpaper, 800, requireContext()) { wallpaper ->
                    binding.ivImgPreview.setImageBitmap(wallpaper)
                }

                val type = when {
                    over?.type.equals("Development") -> "Dev"
                    else -> over?.type
                }

                binding.tvNameUserOverview.text = over?.name?.lowercase()
                binding.tvCategoryOverview.text = type.separator()
                binding.tvTitleOverview.text = over?.title
                binding.tvDescOverview.text = over?.content

                binding.tvInfoUserDetails.spanAndSplit(
                    "Conectar com ${over?.name}",
                    0, 13, lazy {
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorLight030
                        )
                    }.value
                )
            }
        }

        showContent(binding.tvMoreDetails,
            binding.ivIconMore, resource) { isDone ->
            if (isDone) {
                animRotation(binding.ivIconMore, binding.layMoreTextOverview)
                mainViewModel.setIsIconChange(true)
            }
        }
    }

    private fun setAdapter(shots: Array<QueryUris?>?) {
        val manager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        adapter = AdapterShotsUrl(requireContext(), shots, mainViewModel)
        binding.recyclerviewAssets.adapter = adapter
        binding.recyclerviewAssets.layoutManager = manager
        binding.recyclerviewAssets.setHasFixedSize(true)
    }
}