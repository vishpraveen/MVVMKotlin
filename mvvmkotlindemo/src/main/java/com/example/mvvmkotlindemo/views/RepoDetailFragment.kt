package com.example.mvvmkotlindemo.views

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.fragment.app.Fragment
import com.example.mvvmkotlindemo.R
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url= RepoDetailFragmentArgs.fromBundle(arguments!!).url

        setUpWebView()
        setClickListener()

        repo_web_view.loadUrl(url)
    }

    private fun setClickListener() {
        repo_back_button.setOnClickListener {
            repo_web_view.goBack()
        }

        repo_forward_button.setOnClickListener {
            repo_web_view.goForward()
        }

        repo_refresh_button.setOnClickListener {
            repo_web_view.reload()
        }
    }

    private fun setUpWebView() {
        repo_web_view.setInitialScale(1)
        val webViewSettings= repo_web_view.settings
        webViewSettings.setAppCacheEnabled(false)
        webViewSettings.builtInZoomControls=true
        webViewSettings.displayZoomControls=false
        webViewSettings.javaScriptEnabled=true
        webViewSettings.useWideViewPort=true
        webViewSettings.domStorageEnabled=true

        repo_web_view.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (repo_back_button!=null && repo_forward_button!=null && repo_refresh_button!=null && repo_progress_view!=null){
                    repo_back_button.isEnabled = repo_web_view.canGoBack()
                    repo_forward_button.isEnabled=repo_web_view.canGoForward()
                    repo_progress_view.visibility=View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (repo_back_button != null && repo_forward_button != null && repo_web_view != null && repo_progress_view != null) {
                    repo_back_button.isEnabled = repo_web_view.canGoBack()
                    repo_forward_button.isEnabled = repo_web_view.canGoForward()
                    repo_progress_view.visibility = View.GONE
                }
            }
        }
    }
}