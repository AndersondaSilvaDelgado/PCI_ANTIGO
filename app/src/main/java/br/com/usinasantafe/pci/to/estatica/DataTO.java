package br.com.usinasantafe.pci.to.estatica;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pci.pst.Entidade;

/**
 * Created by anderson on 13/07/2017.
 */

@DatabaseTable(tableName="tbdata")
public class DataTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private String data;

    public DataTO() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
