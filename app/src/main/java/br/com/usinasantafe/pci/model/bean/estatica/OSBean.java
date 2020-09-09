package br.com.usinasantafe.pci.model.bean.estatica;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

/**
 * Created by anderson on 29/10/2015.
 */
@DatabaseTable(tableName="tbosest")
public class OSBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idOS;
    @DatabaseField
    private Long nroOS;
    @DatabaseField
    private Long idPlantaOS;
    @DatabaseField
    private Long qtdeDiaOS;
    @DatabaseField
    private String descrPeriodo;

    public OSBean() {
    }

    public Long getIdOS() {
        return idOS;
    }

    public void setIdOS(Long idOS) {
        this.idOS = idOS;
    }

    public Long getNroOS() {
        return nroOS;
    }

    public void setNroOS(Long nroOS) {
        this.nroOS = nroOS;
    }

    public Long getIdPlantaOS() {
        return idPlantaOS;
    }

    public void setIdPlantaOS(Long idPlantaOS) {
        this.idPlantaOS = idPlantaOS;
    }

    public Long getQtdeDiaOS() {
        return qtdeDiaOS;
    }

    public void setQtdeDiaOS(Long qtdeDiaOS) {
        this.qtdeDiaOS = qtdeDiaOS;
    }

    public String getDescrPeriodo() {
        return descrPeriodo;
    }

    public void setDescrPeriodo(String descrPeriodo) {
        this.descrPeriodo = descrPeriodo;
    }
}
