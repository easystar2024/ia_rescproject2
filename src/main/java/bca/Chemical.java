package bca;

public class Chemical {
    private String chemical_id;
    private String chemical_name;
    
    public Chemical(String chem_id, String chem_name) {
        this.chemical_id = chem_id;
        this.chemical_name = chem_name;
    }
    public String getChem_id() {
        return chemical_id;
    }
    public void setChem_id(String chem_id) {
        this.chemical_id = chem_id;
    }
    public String getChem_name() {
        return chemical_name;
    }
    public void setChem_name(String chem_name) {
        this.chemical_name = chem_name;
    }

    public String toString(){
        return chemical_name;
    }

    

}
