package br.com.usinasantafe.pci.to.variavel;

/**
 * Created by anderson on 24/07/2017.
 */

public class AtualizaTO {

    private Long idCelularAtual;
    private String versaoAtual;
    private String versaoNova;

    public AtualizaTO() {
    }

    public Long getIdCelularAtual() {
        return idCelularAtual;
    }

    public void setIdCelularAtual(Long idCelularAtual) {
        this.idCelularAtual = idCelularAtual;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }

    public String getVersaoNova() {
        return versaoNova;
    }

    public void setVersaoNova(String versaoNova) {
        this.versaoNova = versaoNova;
    }
}
