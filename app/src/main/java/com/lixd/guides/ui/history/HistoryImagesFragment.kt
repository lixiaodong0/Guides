package com.lixd.guides.ui.history

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lixd.guides.*
import com.lixd.guides.base.BaseFragment
import com.lixd.guides.dao.entities.Image
import com.lixd.guides.databinding.FragmentHistoryImagesBinding
import com.permissionx.guolindev.PermissionX
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.*

/**
 *  Created by lixiaodong
 *  Created bt date 2021/4/28 4:32 PM
 *  @description
 */
class HistoryImagesFragment : BaseFragment<FragmentHistoryImagesBinding>() {

    private lateinit var adapter: HistoryImagesAdapter

    companion object {
        fun newInstance(): Fragment {
            val fragment = HistoryImagesFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun init() {
        requestPermission()
        viewBinding?.apply {
            adapter = HistoryImagesAdapter(context!!)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = GridLayoutManager(context, 3)

            initRefreshLayout(smartRefreshLayout)
        }
    }

    //当前页数
    var page = DEFAULT_MIN_PAGE

    //页数的大小
    var pageSize = DEFAULT_MAX_PAGE_SIZE

    /**
     * 查询数据
     */
    private fun request(isPullRefresh: Boolean) {
        CoroutineScope(Dispatchers.Main)
            .launch(Dispatchers.Main) {//主线程开启携程
                val data = withContext(Dispatchers.IO) {
                    getAppDatabase().imageDao().pagingQuery(page, pageSize)
                }
                LogUtils.e(data)
                viewBinding?.apply {
                    if (isPullRefresh) {
                        smartRefreshLayout.finishRefresh()
                        adapter.setNewData(data)
                    } else {
                        if (data.size >= pageSize) {
                            smartRefreshLayout.finishLoadMore()
                        } else {
                            smartRefreshLayout.finishLoadMoreWithNoMoreData()
                        }
                        adapter.addData(data)
                    }
                }
            }
    }


    /**
     * 请求权限
     */
    private fun requestPermission() {
        PermissionX.init(this)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                val message = "Guides需要您同意以下权限才能正常使用"
                scope.showRequestReasonDialog(deniedList, message, "确定", "取消")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    refresh()
                } else {
                    activity?.finish()
                }
            }
    }


    /**
     * 手动触发刷新的方法
     */
    fun refresh() {
        viewBinding?.apply {
            smartRefreshLayout.autoRefresh()
        }
    }

    /**
     * 初始化刷新布局
     */
    private fun initRefreshLayout(smartRefreshLayout: SmartRefreshLayout) {
        smartRefreshLayout.setRefreshHeader(MaterialHeader(context))
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(context))
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                //下拉刷新
                page = DEFAULT_MIN_PAGE
                request(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                //上拉加载
                page++
                request(false)
            }
        })
    }
}