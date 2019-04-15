package br.com.usinasantafe.pci.to.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.pst.Entidade;

@DatabaseTable(tableName="tbconfigvar")
public class ConfiguracaoTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long numLinha;

    public ConfiguracaoTO() {
    }

    public Long getNumLinha() {
        return numLinha;
    }

    public void setNumLinha(Long numLinha) {
        this.numLinha = numLinha;
    }
}
