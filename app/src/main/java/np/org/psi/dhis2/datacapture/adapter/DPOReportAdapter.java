package np.org.psi.dhis2.datacapture.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.objects.DPOWeekly;

/**
 * Created by utsav on 3/25/2016.
 */
public class DPOReportAdapter extends RecyclerView.Adapter<DPOReportAdapter.MyViewHolder> {

    private List<DPOWeekly> dpoWeekly;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orgUnit, contact, referral, service, date;
        public MyViewHolder(View itemView) {
            super(itemView);
            orgUnit = (TextView) itemView.findViewById(R.id.orgUnit);
            contact = (TextView) itemView.findViewById(R.id.contact);
            date = (TextView) itemView.findViewById(R.id.week);
            referral = (TextView) itemView.findViewById(R.id.Referral);
            service = (TextView) itemView.findViewById(R.id.iucdService);
        }
    }

    public DPOReportAdapter(List<DPOWeekly> dpoWeekly) {this.dpoWeekly = dpoWeekly; }

    @Override
    public DPOReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dpo_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DPOReportAdapter.MyViewHolder holder, int position) {
        DPOWeekly weekly = dpoWeekly.get(position);
        holder.orgUnit.setText(weekly.getOrgUnit_id());
        holder.contact.setText(weekly.getContact());
        holder.date.setText(weekly.getPeriod());
        holder.referral.setText(weekly.getReferral());
        holder.service.setText(weekly.getIucd());
    }

    @Override
    public int getItemCount() { return dpoWeekly.size(); }
}
