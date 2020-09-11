package br.com.usinasantafe.pci.model.bean.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

/**
 * Created by anderson on 23/10/2015.
 */
@DatabaseTable(tableName="tbitemvar")
public class RespItemBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idRespItem;
    @DatabaseField
    private Long idCabRespItem;
    @DatabaseField
    private Long idItOsMecanRespItem;
    @DatabaseField
    private Long opcaoRespItem;
    @DatabaseField
    private String obsRespItem;
    @DatabaseField
    private Long idPlantaCabecItem;
    @DatabaseField
    private String dthrRespItem;

    public RespItemBean() {
    }

    public Long getIdRespItem() {
        return idRespItem;
    }

    public Long getIdCabRespItem() {
        return idCabRespItem;
    }

    public void setIdCabRespItem(Long idCabRespItem) {
        this.idCabRespItem = idCabRespItem;
    }

    public Long getIdItOsMecanRespItem() {
        return idItOsMecanRespItem;
    }

    public void setIdItOsMecanRespItem(Long idItOsMecanRespItem) {
        this.idItOsMecanRespItem = idItOsMecanRespItem;
    }

    public Long getOpcaoRespItem() {
        return opcaoRespItem;
    }

    public void setOpcaoRespItem(Long opcaoRespItem) {
        this.opcaoRespItem = opcaoRespItem;
    }

    public String getObsRespItem() {
        return obsRespItem;
    }

    public void setObsRespItem(String obsRespItem) {
        this.obsRespItem = obsRespItem;
    }

    public Long getIdPlantaCabecItem() {
        return idPlantaCabecItem;
    }

    public void setIdPlantaCabecItem(Long idPlantaCabecItem) {
        this.idPlantaCabecItem = idPlantaCabecItem;
    }

    public String getDthrRespItem() {
        return dthrRespItem;
    }

    public void setDthrRespItem(String dthrRespItem) {
        this.dthrRespItem = dthrRespItem;
    }
}
