package com.example.machinetests.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetests.R
import com.example.machinetests.StickyHeaderItemDecoration
import com.example.machinetests.model.ArticlesItem


class HomeAdapter(private val users: List<ArticlesItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface  {
     private val TYPE_HEADER = 0
     private val TYPE_ITEM = 1
    override fun isHeader(itemPosition: Int): Boolean {
        return users[itemPosition].header
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        ((header as ConstraintLayout).getChildAt(0) as TextView).text =
                users[headerPosition].title
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.row_header
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var position = itemPosition
        do {
            if (this.isHeader(position)) {
                headerPosition = position
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_HEADER) {
            ViewHolderHeader(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_header, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_user_item, parent, false))
        }
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder) {
            holder.nameView.text = users[position].description
        } else if(holder is ViewHolderHeader) {
            holder.headerView.text = users[position].title
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if(users[position].header) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      //  val nameView = view.nameView as TextView
        val nameView: TextView = itemView.findViewById(R.id.nameView) as TextView
    }

    class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
      //  val headerView = view.headerView as TextView
        val headerView: TextView = itemView.findViewById(R.id.headerView) as TextView
    }

}