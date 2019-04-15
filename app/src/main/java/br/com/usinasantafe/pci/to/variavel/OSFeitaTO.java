package br.com.usinasantafe.pci.to.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.pst.Entidade;

@DatabaseTable(tableName="tbosfeitavar")
public class OSFeitaTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long nroOS;
    @DatabaseField
    private String dataReal;

    public OSFeitaTO() {
    }

    public Long getNroOS() {
        return nroOS;
    }

    public void setNroOS(Long nroOS) {
        this.nroOS = nroOS;
    }

    public String getDataReal() {
        return dataReal;
    }

    public void setDataReal(String dataReal) {
        this.dataReal = dataReal;
    }
}
