package br.com.usinasantafe.pci.util.conHttp;

import br.com.usinasantafe.pci.PCIContext;

public class UrlsConexaoHttp {

	private int tipoEnvio = 1;

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pcidev/view/";
	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pcidev/view/";

	public static String localPSTEstatica = "br.com.usinasantafe.pci.model.bean.estatica.";
	public static String localUrl = "br.com.usinasantafe.pci.util.conHttp.UrlsConexaoHttp";

	public static String put = "?versao=" + PCIContext.versaoAplic.replace(".", "_");

	public static String FuncTO = urlPrincipal + "funcionario.php" + put;
	public static String ServicoTO = urlPrincipal + "servico.php" + put;
	public static String PlantaTO = urlPrincipal + "planta.php" + put;
	public static String ComponenteTO = urlPrincipal + "componente.php" + put;

	
	public UrlsConexaoHttp() {
	}

	public String getsInsertChecklist() {
		return urlPrincEnvio + "inserirchecklist.php" + put;
	}

	public String urlVerifica(String classe) {
		String retorno = "";
		if (classe.equals("OS")) {
			retorno = urlPrincEnvio + "os.php";
		} else if (classe.equals("Item")) {
			retorno = urlPrincEnvio + "itemos.php";
		} else if (classe.equals("Planta")) {
			retorno = urlPrincEnvio + "planta.php";
		} else if (classe.equals("Servico")) {
			retorno = urlPrincEnvio + "servico.php";
		} else if (classe.equals("Atualiza")) {
			retorno = urlPrincEnvio + "atualizaaplic.php";
		} else if (classe.equals("Funcionario")) {
			retorno = urlPrincEnvio + "funcionario.php";
		}
		return retorno;
	}


}