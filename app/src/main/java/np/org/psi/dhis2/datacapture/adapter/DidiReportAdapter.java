package np.org.psi.dhis2.datacapture.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.DidiReports;
import np.org.psi.dhis2.datacapture.objects.DidiReport;
import np.org.psi.dhis2.datacapture.ui.fragments.DidiReport.DidiReportEditFragment;
import np.org.psi.dhis2.datacapture.ui.fragments.DidiReport.DidiReportViewFragment;

/**
 * Created by utsav on 3/13/2016.
 */
public class DidiReportAdapter extends RecyclerSwipeAdapter<DidiReportAdapter.SimpleViewHolder> {

    private List<DidiReport> didiReport;
    private Context mContext;
    FragmentManager fragmentManager;
    String hasSynced;

    public DidiReportAdapter(Context context, List<DidiReport> objects, FragmentManager fragment) {
        this.mContext = context;
        this.didiReport = objects;
        this.fragmentManager = fragment;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.didi_report_list_row, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final DidiReport item = didiReport.get(position);

        viewHolder.orgUnit.setText(item.getOrgUnit_id());
        viewHolder.contact.setText(item.getNewContact());
        viewHolder.date.setText(item.getPeriod());
        viewHolder.prevContact.setText(item.getPreviousContact());
        viewHolder.okIUCD.setText(item.getOkIUCD());

        hasSynced = item.getOnlineSync();
        if(!hasSynced.equals("0")) {
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Left
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));
        }

        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });


        viewHolder.view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(item.getId()));
                DidiReportViewFragment viewFragment = new DidiReportViewFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, viewFragment);
                viewFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        if(!hasSynced.equals("0")) {
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(item.getId()));
                    DidiReportEditFragment editFragment = new DidiReportEditFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, editFragment);
                    editFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            });


            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    DidiReports drs = new DidiReports(mContext);
                    Boolean status = drs.remove(item.getId());
                    if (status == true) {
                        didiReport.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, didiReport.size());
                        mItemManger.closeAllItems();
                        Toast.makeText(view.getContext(), "Deleted " + viewHolder.orgUnit.getText().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Something went wrong while deleting", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() { return didiReport.size(); }

    @Override
    public int getSwipeLayoutResourceId(int position) { return R.id.swipe; }


    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        public TextView orgUnit, contact, date, prevContact, okIUCD, edit, view, delete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            orgUnit = (TextView) itemView.findViewById(R.id.orgUnit);
            contact = (TextView) itemView.findViewById(R.id.contact);
            date = (TextView) itemView.findViewById(R.id.week);
            prevContact = (TextView) itemView.findViewById(R.id.prevContact);
            okIUCD = (TextView) itemView.findViewById(R.id.okReferral);
            edit = (TextView) itemView.findViewById(R.id.btnEdit);
            view = (TextView) itemView.findViewById(R.id.btnView);
            delete = (TextView) itemView.findViewById(R.id.btnDelete);
        }
    }
}
