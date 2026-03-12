import java.util.ArrayList;

public class Games {

    private String titel;
    private ArrayList<DLC> dlcList;

    public Games(String titel) {
        this.titel = titel;
        this.dlcList = new ArrayList<DLC>();
    }

    public void addDlc(String dlcName) {
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public ArrayList<DLC> getDLCs() {
        return this.dlcList;
    }

    public void setDLCs(ArrayList<DLC> dlcList) {
        this.dlcList = dlcList;
    }
}
