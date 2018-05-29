package com.mylocarson.newsapp.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mylocarson.newsapp.R;
import com.mylocarson.newsapp.models.ArticlesItem;
import com.mylocarson.newsapp.util.AdBlocker;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


@SuppressWarnings("ALL")
public class ArticleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // --Commented out by Inspection (29/05/2018, 17:44):TextView articleTitle;
    // --Commented out by Inspection (29/05/2018, 17:44):TextView articleAuthor;
    // --Commented out by Inspection (29/05/2018, 17:44):TextView datepub;
    private ProgressBar progressBar;
    // TODO: Rename and change types of parameters
    private ArticlesItem mParam1;

    public ArticleFragment() {
        // Required empty public constructor
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ArticleFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ArticleFragment newInstance(ArticlesItem param1, String param2) {
//        ArticleFragment fragment = new ArticleFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public WebView getWebView() {
//        return webView;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().get(ARG_PARAM1);
            this.mParam1 = getArguments().getParcelable(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);

//        articleAuthor =(TextView)view.findViewById(R.id.article_author);
//        articleTitle = (TextView)view.findViewById(R.id.article_title);
//        datepub = (TextView)view.findViewById(R.id.date_pub);
        WebView webView = view.findViewById(R.id.webview);
        progressBar = view.findViewById(R.id.web_progress);


//        articleAuthor.setText(mParam1.getSource().getName());
//        articleTitle.setText(mParam1.getTitle());
//        datepub.setText(mParam1.getPublishedAt());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            private final Map<String, Boolean> loadedUrls = new HashMap<>();

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {


                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                boolean ad;
                if (!loadedUrls.containsKey(url)) {
                    ad = AdBlocker.isAd(url);
                    loadedUrls.put(url, ad);
                } else {
                    ad = loadedUrls.get(url);
                }
                return ad ? AdBlocker.createEmptyResource() :
                        super.shouldInterceptRequest(view, url);
            }
        });

        webView.loadUrl(mParam1.getUrl());
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
//                Log.e("BackPressed","Keycode : "+keycode );
//                if (keycode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){
//                    if (getFragmentManager().getBackStackEntryCount()>0){
//                        webView.goBack();
//                        return true;
//                    }
//                }
//                webView.goBack();
//                return true;
//
//            }
//        });
        return view;
    }


}
