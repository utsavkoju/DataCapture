package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/9/2016.
 */
public class OptionSet {
    private Integer id;
    private String name;
    private String code;
    private String displayName;

    public OptionSet() {}

    public OptionSet(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name;}
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

}
