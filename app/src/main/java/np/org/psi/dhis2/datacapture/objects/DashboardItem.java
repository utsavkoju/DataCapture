package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 5/12/2016.
 */
public class DashboardItem {
    private String id;
    private String dashboard_id;
    private String report_type;
    private String chart_id;

    public DashboardItem () {}

    public DashboardItem(String ID, String DASHBOARD_ID, String REPORT_TYPE, String CHART_ID) {
        this.id = ID;
        this.dashboard_id = DASHBOARD_ID;
        this.report_type = REPORT_TYPE;
        this.chart_id = CHART_ID;
    }

    public String getId() {return id;}
    public String getDashboard_id() {return dashboard_id;}
    public String getReport_type() {return report_type; }
    public String getChart_id() { return chart_id; }

    public void setId(String ID) {this.id = ID;}
    public void setDashboard_id(String DASHBOARD_ID) { this.dashboard_id = DASHBOARD_ID;}
    public void setReport_type(String REPORT_TYPE) {this.report_type = REPORT_TYPE; }
    public void setChart_id(String CHART_ID) { this.chart_id = CHART_ID; }
}
