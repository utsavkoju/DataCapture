package np.org.psi.dhis2.datacapture.objects;

/**
 * Created by utsav on 3/8/2016.
 */
public class Dataset_OrgUnit {
    private String dataset_id;
    private String orgUnit_id;

    public Dataset_OrgUnit() {}

    public Dataset_OrgUnit(String dataset_id, String orgUnit_id) { dataset_id = dataset_id; orgUnit_id = orgUnit_id; }

    public String getDataset_id() {return dataset_id;}
    public void setDataset_id(String dataset_id) {dataset_id = dataset_id; }

    public String getOrgUnit_id() { return  orgUnit_id;}
    public void setOrgUnit_id(String orgUnit_id) {orgUnit_id = orgUnit_id; }
}
