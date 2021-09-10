package com.android.flowerart.ui.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.flowerart.R
import com.android.flowerart.logic.bean.Record
import com.android.flowerart.ui.record.RecordFragment
import com.android.flowerart.ui.record.RecordViewModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class CommunityFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(CommunityViewModel::class.java) }

    private lateinit var communityAdapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val communitiesLayoutManager = GridLayoutManager(activity, 1)
        val communitiesRecyclerView =
            activity?.findViewById<RecyclerView>(R.id.communityRecyclerView)
        communitiesRecyclerView?.layoutManager = communitiesLayoutManager
        communityAdapter = CommunityAdapter(requireActivity(), viewModel.communityList)
        communitiesRecyclerView?.adapter = communityAdapter

        viewModel.selectCommunity()

        viewModel.communityResultLiveData.observe(viewLifecycleOwner) { result ->
            viewModel.communityList.clear()
            val records = result.getOrNull()
            if (records != null) {
                viewModel.communityList.addAll(records)
            }
            communityAdapter.notifyDataSetChanged()
        }
    }

    inner class CommunityAdapter(
        private val context: Context,
        private val communityList: List<Record>
    ) :
        RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val userPic: CircleImageView = view.findViewById(R.id.userPic)
            val communityImage: ImageView = view.findViewById(R.id.communityImage)
            val communityName: TextView = view.findViewById(R.id.communityID)
            val communityTime: TextView = view.findViewById(R.id.communityTime)
            val communityContent: TextView = view.findViewById(R.id.communityContent)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.community_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val community = communityList[position]
            Glide.with(context).load("http://47.100.163.39:8802/downloadPic?filename=1.jpg")
                .into(holder.userPic)
            Glide.with(context)
                .load("http://47.100.163.39:8802/downloadPic?filename=" + community.address)
                .into(holder.communityImage)
            holder.communityName.text = community.account.toString()
            holder.communityTime.text = community.time.subSequence(0, 10)
            holder.communityContent.text = community.plantName
        }

        override fun getItemCount(): Int {
            return communityList.count()
        }

    }

}