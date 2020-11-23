package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class UpcomingEventsAdapter: RecyclerView.Adapter<BranchViewHolder>() {

    private val branchDataList: MutableList<BranchApiData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(
            itemView = View.inflate(parent.context, R.layout.branch_list_item, null)
        )
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.onBind(branchDataList[position])
    }

    override fun getItemCount(): Int = branchDataList.size

    fun setList(
        branchDataList: List<BranchApiData>
    ) {
        this.branchDataList.clear()
        this.branchDataList.addAll(branchDataList)
        notifyDataSetChanged()
    }
}