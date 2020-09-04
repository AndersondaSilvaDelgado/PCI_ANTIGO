package br.com.usinasantafe.pci.model.bean.estatica;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

/**
 * Created by anderson on 23/10/2015.
 */
@DatabaseTable(tableName="tbfuncionarioest")
public class FuncBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idFunc;
    @DatabaseField
    private Long matricFunc;
    @DatabaseField
    private String nomeFunc;
    @DatabaseField
    private Long idOficSecaoFunc;

    public FuncBean() {
    }

    public Long getMatricFunc() {
        return matricFunc;
    }

    public void setMatricFunc(Long matricFunc) {
        this.matricFunc = matricFunc;
    }

    public String getNomeFunc() {
        return nomeFunc;
    }

    public void setNomeFunc(String nomeFunc) {
        this.nomeFunc = nomeFunc;
    }

    public Long getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(Long idFunc) {
        this.idFunc = idFunc;
    }

    public Long getIdOficSecaoFunc() {
        return idOficSecaoFunc;
    }

    public void setIdOficSecaoFunc(Long idOficSecaoFunc) {
        this.idOficSecaoFunc = idOficSecaoFunc;
    }
}
