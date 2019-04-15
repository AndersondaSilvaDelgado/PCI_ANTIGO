package br.com.usinasantafe.pci.to.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.pst.Entidade;

/**
 * Created by anderson on 23/10/2015.
 */
@DatabaseTable(tableName="tbcabecalhovar")
public class CabecTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idCabec;
    @DatabaseField
    private Long idExtCabec;
    @DatabaseField
    private Long osCabec;
    @DatabaseField
    private Long idFuncCabec;
    @DatabaseField
    private String dataCabec;
    @DatabaseField
    private Long statusCabec;  //0 - Aberto; 1 - Aberto com planta totalmente apontada; 2 - Fechado
    @DatabaseField
    private Long nroOSCabec;
    @DatabaseField
    private Long qtdeDiaOS;
    @DatabaseField
    private Long verApontCab;

    public CabecTO() {
    }

    public Long getIdCabec() {
        return idCabec;
    }

    public Long getOsCabec() {
        return osCabec;
    }

    public void setOsCabec(Long osCabec) {
        this.osCabec = osCabec;
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

    public Long getStatusCabec() {
        return statusCabec;
    }

    public void setStatusCabec(Long statusCabec) {
        this.statusCabec = statusCabec;
    }

    public Long getNroOSCabec() {
        return nroOSCabec;
    }

    public void setNroOSCabec(Long nroOSCabec) {
        this.nroOSCabec = nroOSCabec;
    }

    public Long getQtdeDiaOS() {
        return qtdeDiaOS;
    }

    public void setQtdeDiaOS(Long qtdeDiaOS) {
        this.qtdeDiaOS = qtdeDiaOS;
    }

    public Long getIdExtCabec() {
        return idExtCabec;
    }

    public void setIdExtCabec(Long idExtCabec) {
        this.idExtCabec = idExtCabec;
    }

    public Long getVerApontCab() {
        return verApontCab;
    }

    public void setVerApontCab(Long verApontCab) {
        this.verApontCab = verApontCab;
    }
}
