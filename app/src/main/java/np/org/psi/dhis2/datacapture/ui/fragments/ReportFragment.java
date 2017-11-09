package np.org.psi.dhis2.datacapture.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

import np.org.psi.dhis2.datacapture.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_reports, container, false);
        WebView browser = (WebView) rootView.findViewById(R.id.webview);
        browser.setWebViewClient(new WebViewClient());
        HashMap<String, String> map = new HashMap<String, String>();
        String creds = "c2t0YW1yYWthcjpVdHNhdjEyMyo=";
        String authorization = "Basic " + creds;
        map.put("Authorization", authorization);
        browser.loadUrl("https://dev.psi-mis.org/api/analytics/events/aggregate/OKuALw0ohEn.html+css?stage=a4UvfEa0qei&dimension=pe:THIS_YEAR&dimension=ou:LEVEL-5;s9WCEATIyh9;V8Lvi57rhbR;kGILXEafjxd;kv6wemN1OSf;l2aBp6gJgHH;McqkIUqTwaB;o7cNdkWalmE&filter=a0MdWFgYDEy&hierarchyMeta=true&outputType=EVENT&displayProperty=NAME", map);

        /*WebView browser1 = (WebView) rootView.findViewById(R.id.webview1);
        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl("https://dev.psi-mis.org/api/eventCharts/Praw0xXTZiL/data", map);*/


        return rootView;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm) {
            handler.proceed("utsavkoju", "Utsav123*");
        }
    }

}
