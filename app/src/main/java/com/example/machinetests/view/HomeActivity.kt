package com.example.machinetests.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetests.R
import com.example.machinetests.StickyHeaderItemDecoration
import com.example.machinetests.model.ArticlesItem
import com.example.machinetests.model.HomeInteractor
import com.example.machinetests.presenter.HomePresenter
import com.example.machinetests.showToast

class HomeActivity : AppCompatActivity(), HomeView {
    private lateinit var homePresenter: HomePresenter
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activit_home)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        homePresenter = HomePresenter(this, HomeInteractor())
        progressBar.visibility = View.GONE
        recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        homePresenter.getNewsData()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setNewsData(arrNewsUpdates: List<ArticlesItem>) {
//        recyclerView.adapter = NewsListAdapter(arrNewsUpdates, newsHomePresenter::onItemClick)    // OR
        Log.e("her", "hrer")
        recyclerView.setHasFixedSize(true)
        viewManager = LinearLayoutManager(this)
        viewAdapter = HomeAdapter(arrNewsUpdates)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter


        recyclerView.addItemDecoration(
            StickyHeaderItemDecoration(
                viewAdapter as HomeAdapter
            )
        )

    }

    override fun getDataFailed(strError: String) {
        showToast(this, strError)
    }

    override fun onItemClick(adapterPosition: Int) {
        showToast(this, "You clicked $adapterPosition")
    }

    override fun onDestroy() {
        homePresenter.onDestroy()
        super.onDestroy()
    }
}
