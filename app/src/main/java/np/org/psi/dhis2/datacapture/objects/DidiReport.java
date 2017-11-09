package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/10/2016.
 */
public class DidiReport {
    private Integer id;
    private String orgUnit_id;
    private String period;
    private String locality;
    private String wardNo;
    private String newContact;
    private String previousContact;
    private String okIUCD;
    private String onlineSync;

    public DidiReport() {}

    public DidiReport(Integer Id, String orgUnitId, String perd, String loc, String ward, String newCon, String previous, String okIUCD, String online)
    {
        this.id = Id;
        this.orgUnit_id = orgUnitId;
        this.period = perd;
        this.locality = loc;
        this.wardNo = ward;
        this.newContact = newCon;
        this.previousContact = previous;
        this.okIUCD = okIUCD;
        this.onlineSync = online;
    }

    public Integer getId() { return id; }
    public void setId(Integer Id) { this.id = Id; }

    public String getOrgUnit_id() { return orgUnit_id; }
    public String getPeriod() { return period; }
    public String getNewContact() { return newContact; }
    public String getPreviousContact() { return previousContact; }
    public String getOkIUCD() { return okIUCD; }
    public String getOnlineSync() { return onlineSync; }
}
