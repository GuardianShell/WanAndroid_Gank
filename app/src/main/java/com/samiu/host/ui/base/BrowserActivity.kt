package com.samiu.host.ui.base

import android.graphics.Bitmap
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.samiu.base.ui.BaseActivity
import com.samiu.base.ui.viewBinding
import com.samiu.host.R
import com.samiu.host.databinding.ActivityBrowserBinding
import com.samiu.host.global.TITLE
import com.samiu.host.global.URL
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author Samiu 2020/3/4
 * @github https://github.com/SamiuZhong
 * @blog samiu.top
 */
class BrowserActivity : BaseActivity() {
    private val binding by viewBinding(ActivityBrowserBinding::inflate)
    override fun getBindingRoot() = binding.root

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        initWebView()
    }

    override fun initData() {
        intent.getStringExtra(URL)?.let {
            binding.webView.loadUrl(it)
        }
        intent.getStringExtra(TITLE)?.let{
            binding.toolbarTitle.text = it
        }
    }

    private fun initWebView() {
        val binding = this.binding

        binding.progressBar.progressDrawable =
            AppCompatResources.getDrawable(this, R.drawable.color_progressbar)
        binding.webView.run {
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
                    binding.progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(p0: WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    binding.progressBar.visibility = View.GONE
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    binding.progressBar.progress = p1
                }
            }
        }
    }
}