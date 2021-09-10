package br.com.usinasantafe.pci;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;

import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;
import br.com.usinasantafe.pci.model.pst.DatabaseHelper;
import br.com.usinasantafe.pci.util.Tempo;

public class ReceberAlarme extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (DatabaseHelper.getInstance() == null) {
			new DatabaseHelper(context);
		}

		Log.i("PCO", "DATA HORA = " + Tempo.getInstance().dataCHora());
		teste();

	}

	public void teste() {

		CabecBean cabecBean = new CabecBean();
		List cabecList = cabecBean.all();

		for (int i = 0; i < cabecList.size(); i++) {

			cabecBean = (CabecBean) cabecList.get(i);
			Log.i("PCI", "CABEC");
			Log.i("PCI", "idCabec = " + cabecBean.getIdCabec());
			Log.i("PCI", "osCabec = " + cabecBean.getIdOSCabec());
			Log.i("PCI", "idFuncCabec = " + cabecBean.getIdFuncCabec());
			Log.i("PCI", "dataCabec = " + cabecBean.getDataCabec());
			Log.i("PCI", "statusCabec = " + cabecBean.getStatusCabec());
			Log.i("PCI", "statusApontCabec = " + cabecBean.getStatusApontCabec());

		}

		PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
		List plantaCabecList = plantaCabecBean.all();

		for(int i = 0; i < plantaCabecList.size(); i++){

			plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(i);
			Log.i("PCI", "PLANTA CABEC");
			Log.i("PCI", "idPlantaCabec = " + plantaCabecBean.getIdPlantaCabec());
			Log.i("PCI", "idPlanta = " + plantaCabecBean.getIdPlanta());
			Log.i("PCI", "idCabec = " + plantaCabecBean.getIdCabec());
			Log.i("PCI", "statusPlantaCabec = " + plantaCabecBean.getStatusPlantaCabec());
			Log.i("PCI", "statusApontPlanta = " + plantaCabecBean.getStatusApontPlanta());

		}

		RespItemBean respItemBean = new RespItemBean();
		List respItemList = respItemBean.all();

		for(int i = 0; i < respItemList.size(); i++){

			respItemBean = (RespItemBean) respItemList.get(i);
			Log.i("PCI", "RESP ITEM");
			Log.i("PCI", "idRespItem = " + respItemBean.getIdRespItem());
			Log.i("PCI", "idCabRespItem = " + respItemBean.getIdCabRespItem());
			Log.i("PCI", "idItOsMecanRespItem = " + respItemBean.getIdItOsMecanRespItem());
			Log.i("PCI", "opcaoRespItem = " + respItemBean.getOpcaoRespItem());
			Log.i("PCI", "obsRespItem = " + respItemBean.getObsRespItem());
			Log.i("PCI", "idPlantaCabecItem = " + respItemBean.getIdPlantaCabecItem());
			Log.i("PCI", "dthrRespItem = " + respItemBean.getDthrRespItem());

		}

	}

}