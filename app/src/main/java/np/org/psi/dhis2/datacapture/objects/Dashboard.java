package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 5/12/2016.
 */
public class Dashboard {
    private String id;
    private String name;

    public Dashboard() {}

    public Dashboard(String ID, String NAME)
    {
        this.id = ID;
        this.name = NAME;
    }

    public String getId () { return id; }
    public void setId(String ID) { this.id = ID; }

    public String getName() { return name; }
    public void setName(String NAME) { this.name = NAME; }

}
