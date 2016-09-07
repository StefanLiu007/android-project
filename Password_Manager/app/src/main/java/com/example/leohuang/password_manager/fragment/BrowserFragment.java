package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.ui.ClearEditText;

/**
 * Created by sun on 16/3/23.
 */
public class BrowserFragment extends Fragment {
    View view;
    WebView webView;
    ClearEditText editText;
    TextView slide;
    ImageView search;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.browser,null);
        }
        init(view);
        return view;
    }
private void init(View view){
    webView = (WebView)view.findViewById(R.id.webView);
    editText = (ClearEditText)view.findViewById(R.id.search_edit);
    toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    toolbar.setNavigationIcon(R.drawable.list_icon);
    toolbar.setTitle(getResources().getString(R.string.brower));
    webView.loadUrl("http://www.baidu.com");
    webView.setWebViewClient(new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    });
//    search.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String uri = editText.getText().toString().trim();
//            StringBuilder url = new StringBuilder();
//            url.append("http://");
//            if (uri != null) {
//                url.append(uri);
//                webView.loadUrl(url.toString());
//            }
//
//        }
//    });
//    slide.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            SecondActivity.mDrawerLayout.openDrawer(GravityCompat.START);
//        }
//    });
}



}
