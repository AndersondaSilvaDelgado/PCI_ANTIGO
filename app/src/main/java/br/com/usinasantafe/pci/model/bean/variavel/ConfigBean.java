package br.com.usinasantafe.pci.model.bean.variavel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.model.pst.Entidade;

@DatabaseTable(tableName="tbconfigvar")
public class ConfigBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long numLinhaConfig;

    public ConfigBean() {
    }

    public Long getNumLinhaConfig() {
        return numLinhaConfig;
    }

    public void setNumLinhaConfig(Long numLinhaConfig) {
        this.numLinhaConfig = numLinhaConfig;
    }
}
