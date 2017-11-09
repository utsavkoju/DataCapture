package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/14/2016.
 */
public class BccSummary {
    private Integer id;
    private String orgUnit_id, period, report_by, tole, actiivityType, male, female, children, referral, pc;

    public BccSummary() { }

    public BccSummary(Integer Id, String orgUnitId, String perd, String actType, String Male, String Female, String Referral) {
        this.id = Id;
        this.orgUnit_id = orgUnitId;
        this.period = perd;
        this.actiivityType = actType;
        this.male = Male;
        this.female = Female;
        this.referral = Referral;
    }

    public Integer getId () { return id; }
    public void setId(Integer Id) { this.id = Id; }

    public String getOrgUnit_id() { return orgUnit_id; }
    public String getPeriod() { return period; }
    public String getReport_by() { return report_by; }
    public String getActiivityType() { return actiivityType; }
    public String getMale() { return male; }
    public String getFemale() { return female; }
    public String getReferral() { return referral; }
}
