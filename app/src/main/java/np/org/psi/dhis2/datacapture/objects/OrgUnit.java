package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 2/24/2016.
 */
public class OrgUnit {
    private String id;
    private String name;
    private String shortName;
    private String code;
    private String level;
    private String coordinates;
    private String parentId;

    public OrgUnit() {}

    public OrgUnit(String id, String name, String sName, String cd, String lvl, String codinates) {
        this.id = id;
        this.name = name;
        this.shortName = sName;
        this.code = cd;
        this.level = lvl;
        this.coordinates = codinates;
    }

    public String getId() { return id; }
    public void setId(String id) {id = id; }

    public String getName() { return name; }
    public void setName(String name ){ name = name; }

    @Override
    public String toString() { return name; }
    public String getShortName() { return shortName; }
    public void setShortName(String shortName) {shortName = shortName;}

    public String getCode() { return code; }
    public void setCode(String code) { code = code; }

    public String getLevel() { return level; }
    public void setLevel(String level) { level = level;}

    public String getCoordinates() {return coordinates; }
    public void setCoordinates(String coordinates) { coordinates = coordinates;}

    public String getParentId() { return parentId; }
    public  void setParentId(String parentId) { parentId = parentId; }
}
