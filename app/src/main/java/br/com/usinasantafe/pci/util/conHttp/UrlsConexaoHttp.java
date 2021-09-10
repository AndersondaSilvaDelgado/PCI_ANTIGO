package br.com.usinasantafe.pci.util.conHttp;

import br.com.usinasantafe.pci.PCIContext;

public class UrlsConexaoHttp {

	private int tipoEnvio = 1;

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pciqa/view/";
	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pciqa/view/";

	public static String localPSTEstatica = "br.com.usinasantafe.pci.model.bean.estatica.";
	public static String localUrl = "br.com.usinasantafe.pci.util.conHttp.UrlsConexaoHttp";

	public static String put = "?versao=" + PCIContext.versaoAplic.replace(".", "_");

	public static String FuncBean = urlPrincipal + "funcionario.php" + put;
	public static String ServicoBean = urlPrincipal + "servico.php" + put;
	public static String PlantaBean = urlPrincipal + "planta.php" + put;
	public static String ComponenteBean = urlPrincipal + "componente.php" + put;

	
	public UrlsConexaoHttp() {
	}

	public String getsInsertChecklist() {
		return urlPrincEnvio + "inserirchecklist.php" + put;
	}

	public String urlVerifica(String classe) {
		String retorno = "";
		if (classe.equals("OS")) {
			retorno = urlPrincEnvio + "os.php" + put;
		} else if (classe.equals("Item")) {
			retorno = urlPrincEnvio + "itemos.php" + put;
		} else if (classe.equals("Atualiza")) {
			retorno = urlPrincEnvio + "atualaplic.php" + put;
		}
		return retorno;
	}


}