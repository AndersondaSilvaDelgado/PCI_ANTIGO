package br.com.usinasantafe.pci.model.bean.estatica;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

@DatabaseTable(tableName="tbplantaest")
public class PlantaBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idPlanta;
    @DatabaseField
    private String codPlanta;
    @DatabaseField
    private String descrPlanta;

    public PlantaBean() {
    }

    public Long getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Long idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getCodPlanta() {
        return codPlanta;
    }

    public void setCodPlanta(String codPlanta) {
        this.codPlanta = codPlanta;
    }

    public String getDescrPlanta() {
        return descrPlanta;
    }

    public void setDescrPlanta(String descrPlanta) {
        this.descrPlanta = descrPlanta;
    }

}
