package br.com.usinasantafe.pci.model.bean.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

/**
 * Created by anderson on 23/10/2015.
 */
@DatabaseTable(tableName="tbcabecalhovar")
public class CabecBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idCabec;
    @DatabaseField
    private Long idOSCabec;
    @DatabaseField
    private Long idOficSecaoCabec;
    @DatabaseField
    private Long idFuncCabec;
    @DatabaseField
    private String dataCabec;
    @DatabaseField
    private Long statusCabec;  //1 - Aberto; 2 - Fechado
    @DatabaseField
    private Long statusApontCabec;

    public CabecBean() {
    }

    public Long getIdCabec() {
        return idCabec;
    }

    public Long getIdOSCabec() {
        return idOSCabec;
    }

    public void setIdOSCabec(Long idOSCabec) {
        this.idOSCabec = idOSCabec;
    }

    public String getDataCabec() {
        return dataCabec;
    }

    public void setDataCabec(String dataCabec) {
        this.dataCabec = dataCabec;
    }

    public Long getIdFuncCabec() {
        return idFuncCabec;
    }

    public void setIdFuncCabec(Long idFuncCabec) {
        this.idFuncCabec = idFuncCabec;
    }

    public Long getIdOficSecaoCabec() {
        return idOficSecaoCabec;
    }

    public void setIdOficSecaoCabec(Long idOficSecaoCabec) {
        this.idOficSecaoCabec = idOficSecaoCabec;
    }

    public Long getStatusCabec() {
        return statusCabec;
    }

    public void setStatusCabec(Long statusCabec) {
        this.statusCabec = statusCabec;
    }

    public Long getStatusApontCabec() {
        return statusApontCabec;
    }

    public void setStatusApontCabec(Long statusApontCabec) {
        this.statusApontCabec = statusApontCabec;
    }
}
