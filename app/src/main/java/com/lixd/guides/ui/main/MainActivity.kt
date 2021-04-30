package com.lixd.guides.ui.main

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lixd.guides.R
import com.lixd.guides.base.BaseActivity
import com.lixd.guides.dao.entities.Image
import com.lixd.guides.databinding.ActivityMainBinding
import com.lixd.guides.getAppDatabase
import com.lixd.guides.ui.guides.GuidesActivity
import com.lixd.guides.ui.history.HistoryImagesFragment


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun init() {
        viewBinding?.apply {
            setSupportActionBar(toolbar)
            toolbar.setOnClickListener { }
        }

        supportFragmentManager.beginTransaction()
            .replace(
                viewBinding!!.flContainer.id,
                HistoryImagesFragment.newInstance(),
                HistoryImagesFragment.javaClass.simpleName

            )
            .commit()
    }

    /**
     * 渲染操作菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * 监听操作菜单点击
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            (R.id.add_photo) -> {
                callSystemImagePick()
                true
            }
            (R.id.add_links) -> {
                callAddLinksImageDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 调用添加链接图片弹窗
     */
    private fun callAddLinksImageDialog() {
        val dialog = AddLinksImageDialog()
        dialog.onConfirmListener = object : AddLinksImageDialog.OnConfirmListener {
            override fun onConfirm(links: String) {
                val image = insertImageToDb(links, Image.NETWORK_IMAGE)
                Log.d("guides", links)
                GuidesActivity.launch(this@MainActivity, image)
            }
        }
        dialog.show(supportFragmentManager, "")
    }

    /**
     * 调用系统图片浏览器
     */
    private fun callSystemImagePick() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 1) {
                val uri = data!!.data
                val cursor: Cursor? = contentResolver.query(
                    uri!!, null,
                    null, null, null
                )
                cursor?.apply {
                    moveToFirst()
                    val path = getString(getColumnIndex(MediaStore.Images.Media.DATA))
                    val image = insertImageToDb(path, Image.LOCAL_IMAGE)
                    GuidesActivity.launch(this@MainActivity, image)
                    Log.d("guides", path)
                    close()
                    /* for (i in 0 until columnCount) { // 取得图片uri的列名和此列的详细信息
                         Log.d("guides", "${getColumnName(i)}-${getString(i)}")
                     }*/
                }
            }
        }
    }

    /**
     * 插入图片到数据库
     */
    fun insertImageToDb(path: String, source: Int): Image {
        val image = Image(path, source, date = System.currentTimeMillis())
        getAppDatabase().imageDao().insert(image)
        val fragment =
            supportFragmentManager.findFragmentByTag(HistoryImagesFragment.javaClass.simpleName) as HistoryImagesFragment
        fragment.refresh()
        return image
    }
}