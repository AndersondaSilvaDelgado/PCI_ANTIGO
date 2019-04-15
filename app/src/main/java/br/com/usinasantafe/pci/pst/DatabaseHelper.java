package br.com.usinasantafe.pci.pst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.usinasantafe.pci.to.estatica.ComponenteTO;
import br.com.usinasantafe.pci.to.estatica.FuncTO;
import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.estatica.OSTO;
import br.com.usinasantafe.pci.to.estatica.PlantaTO;
import br.com.usinasantafe.pci.to.estatica.ServicoTO;
import br.com.usinasantafe.pci.to.variavel.AtualizaTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.ConfiguracaoTO;
import br.com.usinasantafe.pci.to.variavel.OSFeitaTO;
import br.com.usinasantafe.pci.to.variavel.PlantaCabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String FORCA_DB_NAME = "pci_db";
	public static final int FORCA_BD_VERSION = 1;

	private static DatabaseHelper instance;
	
	public static DatabaseHelper getInstance(){
		return instance;
	}
	
	public DatabaseHelper(Context context) {
		
		super(context, FORCA_DB_NAME,
				null, FORCA_BD_VERSION);
		// TODO Auto-generated constructor stub
		instance = this;
		
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		
		instance = null;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		// TODO Auto-generated method stub
		try{

			TableUtils.createTable(cs, FuncTO.class);
			TableUtils.createTable(cs, OSTO.class);
			TableUtils.createTable(cs, ItemTO.class);
			TableUtils.createTable(cs, PlantaTO.class);
			TableUtils.createTable(cs, ServicoTO.class);
			TableUtils.createTable(cs, ComponenteTO.class);

			TableUtils.createTable(cs, CabecTO.class);
			TableUtils.createTable(cs, RespItemTO.class);
			TableUtils.createTable(cs, ConfiguracaoTO.class);
			TableUtils.createTable(cs, PlantaCabecTO.class);
			TableUtils.createTable(cs, OSFeitaTO.class);

		}
		catch(Exception e){
			Log.e(DatabaseHelper.class.getName(),
					"Erro criando banco de dados...",
					e);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,
			ConnectionSource cs,
			int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub
		try {
			if(oldVersion == 1 && newVersion == 2){
				//TableUtils.createTable(cs, ConfiguracaoTO.class);
				oldVersion = 2;
			}
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Erro atualizando banco de dados...", e);
		}
		
	}

}
