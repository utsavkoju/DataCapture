package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/8/2016.
 */
public class Dataset {
    private String id;
    private String name;
    private String shortName;
    private String periodType;
    private String dataEntryFrom;

    public Dataset() {}

    public Dataset(String id, String name, String shortName, String periodType, String dataEntryFrom)
    {
        id = id;
        name = name;
        shortName = shortName;
        periodType = periodType;
        dataEntryFrom = dataEntryFrom;
    }

    public String getId() { return id; }
    public void setId(String id) {id = id; }

    public String getName() { return name; }
    public void setName(String name ){ name = name; }

    public String getShortName() { return shortName; }
    public void setShortName(String shortName) {shortName = shortName;}

    public String getPeriodType() { return periodType; }
    public void setPeriodType(String periodType) { periodType = periodType; }

    public String getDataEntryFrom() {return dataEntryFrom; }
    public void setDataEntryFrom(String dataEntryFrom) { dataEntryFrom = dataEntryFrom; }
}
