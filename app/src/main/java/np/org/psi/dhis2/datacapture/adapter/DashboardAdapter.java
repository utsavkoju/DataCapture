package np.org.psi.dhis2.datacapture.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.objects.DashboardItem;

/**
 * Created by utsav on 5/12/2016.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    private List<DashboardItem> dashItems;
    private String TAG = "DashAdapter";

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e(TAG, String.valueOf(position));
        final DashboardItem item = dashItems.get(position);
        String chart_id = item.getChart_id();
        Log.e(TAG, chart_id);
        String URL = "https://dev.psi-mis.org/api/eventCharts/"+chart_id+"/data";


        holder.wView.setWebViewClient(new WebViewClient());
        holder.wView.getSettings().setLoadWithOverviewMode(true);
        holder.wView.getSettings().setUseWideViewPort(true);
        HashMap<String, String> map = new HashMap<String, String>();
        String creds = "c2t0YW1yYWthcjpVdHNhdjEyMyo=";
        String authorization = "Basic " + creds;
        map.put("Authorization", authorization);
        Log.e(TAG, URL);
        holder.wView.loadUrl("https://dev.psi-mis.org/api/eventCharts/"+chart_id+"/data", map);
    }

    @Override
    public int getItemCount() {
        return dashItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public WebView wView;
        public MyViewHolder(View itemView) {
            super(itemView);
            wView = (WebView) itemView.findViewById(R.id.webview);
        }
    }

    public DashboardAdapter(List<DashboardItem> dashItems) {this.dashItems = dashItems; }

}
