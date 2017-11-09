package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/9/2016.
 */
public class Option {

    private String id;
    private String name;
    private String code;
    private String optionSet_id;

    public Option() {}

    public Option(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name;}
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getOptionSet_id() { return optionSet_id; }
    public void setOptionSet_id(String optionSet_id) { this.optionSet_id = optionSet_id; }

}
