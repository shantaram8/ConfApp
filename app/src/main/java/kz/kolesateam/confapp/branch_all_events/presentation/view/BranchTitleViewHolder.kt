package kz.kolesateam.confapp.branch_all_events.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.BranchAllEventsListItem
import kz.kolesateam.confapp.models.BranchTitleItem
import kz.kolesateam.confapp.upcoming_events.presentation.view.BaseViewHolder

class BranchTitleViewHolder(itemView: View) : BaseViewHolder<BranchAllEventsListItem>(itemView) {

    private val branchTitleTextView: TextView =
        itemView.findViewById(R.id.branch_title_text_view)


    override fun onBind(
        data: BranchAllEventsListItem
    ) {
        val branchTitle: String = (data as? BranchTitleItem)?.branchTitle ?: return
        branchTitleTextView.text = branchTitle
    }
}