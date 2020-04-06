package com.samiu.host.ui.base.nav

import android.graphics.Bitmap
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.samiu.base.ui.BaseFragment
import com.samiu.base.ui.viewBinding
import com.samiu.host.R
import com.samiu.host.databinding.ActivityBrowserBinding
import com.samiu.host.global.URL
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_browser.*

/**
 * @author Samiu 2020/4/6
 */
class BrowserFragment : BaseFragment(R.layout.activity_browser) {
    private val binding by viewBinding(ActivityBrowserBinding::bind)

    override fun initView() {
        binding.toolBar.title = getString(R.string.is_loading)
        binding.toolBar.navigationIcon = getDrawable(requireContext(), R.drawable.arrow_back)
        initWebView()
    }

    override fun initData() {
        arguments?.getString(URL)?.let {
            binding.webView.loadUrl(it)
        }
    }

    private fun initWebView() {
        binding.progressBar.progressDrawable =
            getDrawable(requireContext(), R.drawable.color_progressbar)
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

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { tool_bar.title = p1 }
                }

            }
        }
    }
}