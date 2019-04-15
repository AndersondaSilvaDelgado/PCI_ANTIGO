package br.com.usinasantafe.pci.conWEB;

public class UrlsConexaoHttp {

	private int tipoEnvio = 1;

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pcidev/";
	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pcidev/";

	public static String localPSTEstatica = "br.com.usinasantafe.pci.to.estatica.";
	public static String localUrl = "br.com.usinasantafe.pci.conWEB.UrlsConexaoHttp";
	
	public static String FuncTO = urlPrincipal + "funcionario.php";
	public static String ServicoTO = urlPrincipal + "servico.php";
	public static String PlantaTO = urlPrincipal + "planta.php";
	public static String ComponenteTO = urlPrincipal + "componente.php";

	
	public UrlsConexaoHttp() {
		// TODO Auto-generated constructor stub
	}

	public String getsApontChecklist() {
		if(tipoEnvio == 1)
			return urlPrincEnvio + "inserirchecklist.php";
		else
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