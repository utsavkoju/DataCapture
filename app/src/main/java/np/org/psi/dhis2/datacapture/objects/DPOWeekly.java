package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/18/2016.
 */
public class DPOWeekly {
    private Integer id;
    private String orgUnit_id;
    private String period;
    private String contact;
    private String referral;
    private String iucd;

    public DPOWeekly() {}

    public DPOWeekly(String orgUnitId, String prd, String cont, String ref, String IUCD)
    {
        this.orgUnit_id = orgUnitId;
        this.period = prd;
        this.contact = cont;
        this.referral = ref;
        this.iucd = IUCD;
    }

    public Integer getId() { return id; }
    public void setId(Integer Id) { this.id = Id;}

    public String getOrgUnit_id() { return orgUnit_id; }
    public void setOrgUnit_id(String orgUnitId) { this.orgUnit_id = orgUnitId; }
    public String getPeriod() { return period; }
    public void setPeriod(String Prd) {this.period = Prd; }
    public String getContact() { return contact; }
    public void setContact(String cont) { this.contact = cont; }
    public String getReferral() { return referral; }
    public void setReferral(String ref) { this.referral = ref; }
    public String getIucd() { return iucd; }
    public void setIucd(String IUCD) { this.iucd = IUCD; }



}
