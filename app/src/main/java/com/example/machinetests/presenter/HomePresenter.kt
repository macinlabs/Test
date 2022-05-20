package com.example.machinetests.presenter

import com.example.machinetests.model.ArticlesItem
import com.example.machinetests.model.HomeInteractor
import com.example.machinetests.view.HomeView



class HomePresenter(private var homeView: HomeView?, private val homeInteractor: HomeInteractor)
    : HomeInteractor.OnFinishedListener {

    fun getNewsData() {
        homeView?.showProgress()
        homeInteractor.requestNewsDataAPI(this)
    }

    fun onDestroy() {
        homeView = null
    }

    override fun onResultSuccess(arrNewsUpdates: List<ArticlesItem>) {
        homeView?.hideProgress()



        homeView?.setNewsData(arrNewsUpdates)
    }

    override fun onResultFail(strError: String) {
        homeView?.hideProgress()
        homeView?.getDataFailed(strError)
    }

    fun onItemClick(adapterPosition: Int) {
        homeView?.onItemClick(adapterPosition)
    }
}