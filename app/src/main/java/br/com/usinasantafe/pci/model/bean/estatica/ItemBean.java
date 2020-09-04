package br.com.usinasantafe.pci.model.bean.estatica;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

/**
 * Created by anderson on 29/10/2015.
 */
@DatabaseTable(tableName="tbitemest")
public class ItemBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idItem;
    @DatabaseField
    private Long seqItem;
    @DatabaseField
    private Long idPlantaItem;
    @DatabaseField
    private Long idComponenteItem;
    @DatabaseField
    private Long idOsItem;
    @DatabaseField
    private Long idServicoItem;
    @DatabaseField
    private Long opcaoSelItem;

    public ItemBean() {
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Long getSeqItem() {
        return seqItem;
    }

    public void setSeqItem(Long seqItem) {
        this.seqItem = seqItem;
    }

    public Long getIdOsItem() {
        return idOsItem;
    }

    public void setIdOsItem(Long idOsItem) {
        this.idOsItem = idOsItem;
    }

    public Long getIdPlantaItem() {
        return idPlantaItem;
    }

    public void setIdPlantaItem(Long idPlantaItem) {
        this.idPlantaItem = idPlantaItem;
    }

    public Long getIdServicoItem() {
        return idServicoItem;
    }

    public void setIdServicoItem(Long idServicoItem) {
        this.idServicoItem = idServicoItem;
    }

    public Long getOpcaoSelItem() {
        return opcaoSelItem;
    }

    public void setOpcaoSelItem(Long opcaoSelItem) {
        this.opcaoSelItem = opcaoSelItem;
    }

    public Long getIdComponenteItem() {
        return idComponenteItem;
    }

    public void setIdComponenteItem(Long idComponenteItem) {
        this.idComponenteItem = idComponenteItem;
    }
}
