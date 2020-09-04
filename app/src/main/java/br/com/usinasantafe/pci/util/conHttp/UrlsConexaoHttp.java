package br.com.usinasantafe.pci.util.conHttp;

public class UrlsConexaoHttp {

	private int tipoEnvio = 1;

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pci/";
	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pci/";

	public static String localPSTEstatica = "br.com.usinasantafe.pci.model.bean.estatica.";
	public static String localUrl = "br.com.usinasantafe.pci.util.conWEB.UrlsConexaoHttp";
	
	public static String FuncTO = urlPrincipal + "funcionario.php";
	public static String ServicoTO = urlPrincipal + "servico.php";
	public static String PlantaTO = urlPrincipal + "planta.php";
	public static String ComponenteTO = urlPrincipal + "componente.php";

	
	public UrlsConexaoHttp() {
	}

	public String getsApontChecklist() {
		return urlPrincEnvio + "inserirchecklist.php";
	}

	public String urlVerifica(String classe) {
		String retorno = "";
		if (classe.equals("OS")) {
			retorno = urlPrincEnvio + "veros.php";
		} else if (classe.equals("Item")) {
			retorno = urlPrincEnvio + "veritemos.php";
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