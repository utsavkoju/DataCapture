package np.org.psi.dhis2.datacapture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.objects.ListDetail;

/**
 * Created by utsav on 4/4/2016.
 */
public class ListViewAdapter extends ArrayAdapter<ListDetail> {

    public ListViewAdapter(Context context, ArrayList<ListDetail> lists) {
        super(context, 0, lists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListDetail ld = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.title);
        TextView tvDetail = (TextView) convertView.findViewById(R.id.summary);
        tvTitle.setText(ld.title);
        tvDetail.setText(ld.detail);
        return convertView;
    }
}
