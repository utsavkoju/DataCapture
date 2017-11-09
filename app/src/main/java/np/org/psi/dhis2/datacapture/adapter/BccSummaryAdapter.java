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
import np.org.psi.dhis2.datacapture.database.BccSummarys;
import np.org.psi.dhis2.datacapture.objects.BccSummary;
import np.org.psi.dhis2.datacapture.ui.fragments.BCC.BCCEditFragment;

/**
 * Created by utsav on 3/14/2016.
 */
public class BccSummaryAdapter extends RecyclerSwipeAdapter<BccSummaryAdapter.SimpleViewHolder> {
    private List<BccSummary> bccSummary;
    private Context mContext;
    FragmentManager fragmentManager;

    public BccSummaryAdapter(Context context, List<BccSummary> objects, FragmentManager fragment) {
        this.mContext = context;
        this.bccSummary = objects;
        this.fragmentManager = fragment;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bcc_summary_list_row, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final BccSummary item = bccSummary.get(position);

        //BccSummary report = bccSummary.get(position);
        viewHolder.orgUnit.setText(item.getOrgUnit_id());
        viewHolder.actType.setText(item.getActiivityType());
        viewHolder.period.setText(item.getPeriod());
        viewHolder.male.setText(item.getMale());
        viewHolder.female.setText(item.getFemale());
        viewHolder.referral.setText(item.getReferral());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Left
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

        // Drag From Right
        //viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


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

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " onClick : " + item.getId() + " \n" + item.getActiivityType(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(item.getId()));
                BCCEditFragment editFragment = new BCCEditFragment();
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
                BccSummarys bsummary = new BccSummarys(mContext);
                Boolean status = bsummary.remove(item.getId());
                if(status == true) {
                    bccSummary.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, bccSummary.size());
                    mItemManger.closeAllItems();
                    Toast.makeText(view.getContext(), "Deleted " + viewHolder.orgUnit.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(), "Something went wrong while deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() { return bccSummary.size(); }

    @Override
    public int getSwipeLayoutResourceId(int position) { return R.id.swipe; }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView orgUnit, period, actType, male, female, referral, edit, delete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            orgUnit = (TextView) itemView.findViewById(R.id.orgUnit);
            actType = (TextView) itemView.findViewById(R.id.activityType);
            male = (TextView) itemView.findViewById(R.id.male);
            female = (TextView) itemView.findViewById(R.id.female);
            referral = (TextView) itemView.findViewById(R.id.Referral);
            period = (TextView) itemView.findViewById(R.id.date);
            edit = (TextView) itemView.findViewById(R.id.btnEdit);
            delete = (TextView) itemView.findViewById(R.id.btnDelete);
        }
    }

}
