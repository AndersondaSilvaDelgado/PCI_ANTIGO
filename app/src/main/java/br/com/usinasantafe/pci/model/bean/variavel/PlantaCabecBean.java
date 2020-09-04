package br.com.usinasantafe.pci.model.bean.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

@DatabaseTable(tableName="tbplantacabvar")
public class PlantaCabecBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idPlantaCabec;
    @DatabaseField
    private Long idPlanta;
    @DatabaseField
    private Long idCabec;
    @DatabaseField
    private Long statusPlantaCabec; //1 - Falta apontamento; 2 - Todos item apontado; 3 Enviando; 4 Enviado
    @DatabaseField
    private Long verApontaPlanta;

    public PlantaCabecBean() {
    }

    public Long getIdPlantaCabec() {
        return idPlantaCabec;
    }

    public void setIdPlantaCabec(Long idPlantaCabec) {
        this.idPlantaCabec = idPlantaCabec;
    }

    public Long getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Long idPlanta) {
        this.idPlanta = idPlanta;
    }

    public Long getIdCabec() {
        return idCabec;
    }

    public void setIdCabec(Long idCabec) {
        this.idCabec = idCabec;
    }

    public Long getStatusPlantaCabec() {
        return statusPlantaCabec;
    }

    public void setStatusPlantaCabec(Long statusPlantaCabec) {
        this.statusPlantaCabec = statusPlantaCabec;
    }

    public Long getVerApontaPlanta() {
        return verApontaPlanta;
    }

    public void setVerApontaPlanta(Long verApontaPlanta) {
        this.verApontaPlanta = verApontaPlanta;
    }
}
