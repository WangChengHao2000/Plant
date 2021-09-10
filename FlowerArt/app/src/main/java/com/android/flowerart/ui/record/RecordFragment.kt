package com.android.flowerart.ui.record

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.flowerart.FlowerApplication
import com.android.flowerart.R
import com.android.flowerart.logic.bean.Record

class RecordFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(RecordViewModel::class.java) }

    private lateinit var timelineAdapter: TimelineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val timelineLayoutManager = GridLayoutManager(activity, 1)
        val timelineListView = activity?.findViewById<RecyclerView>(R.id.recordListView)
        timelineListView?.layoutManager = timelineLayoutManager
        timelineAdapter = TimelineAdapter(requireActivity(), viewModel.recordList)
        timelineListView?.adapter = timelineAdapter

        if (FlowerApplication.account != 0) {
            viewModel.selectRecord(FlowerApplication.account)
        }

        viewModel.recordResultLiveData.observe(viewLifecycleOwner) { result ->
            viewModel.recordList.clear()
            val records = result.getOrNull()
            if (records != null) {
                viewModel.recordList.addAll(records)
            }
            timelineAdapter.notifyDataSetChanged()
        }
    }

    inner class TimelineAdapter(
        private val context: Context,
        private val recordList: List<Record>
    ) :
        RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val recordTime: TextView = view.findViewById(R.id.timelineTime)
            val recordPlantName: TextView = view.findViewById(R.id.timelineRecord)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.records_time, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val record = recordList[position]
            val time =
                record.time.subSequence(0, 10).toString() + "\t" + record.time.subSequence(11, 19)
            holder.recordTime.text = time
            holder.recordPlantName.text = record.plantName
        }

        override fun getItemCount(): Int {
            return recordList.count()
        }

    }

}