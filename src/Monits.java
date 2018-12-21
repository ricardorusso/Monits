
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;


public class Monits {

	private String nameBatch;
	//private List<String> lista;
	private String dir;
	private String specialCase;
	
	

	public String getDir() {
		return dir;
	}
	public String getSpecialCase() {
		return specialCase;
	}
	public void setSpecialCase(String specialCase) {
		this.specialCase = specialCase;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	private static final List<String> listaBatchDir = Arrays.asList(new File("D:\\FileEx").list());


	public String getNameBatch() {
		return nameBatch;
	}
	public void setNameBatch(String nameBatch) {
		this.nameBatch = nameBatch;
	}

//	public List<String> getLista() {
//		return this.lista;
//	}
//
//	public void setLista(List<String> lista) {
//		this.lista = lista;
//	}
	public Monits(String nameBatch, String diretorio) {
		super();
		this.nameBatch = nameBatch;
		this.dir = diretorio;
		//this.lista = Arrays.asList(new File(diretorio).list());
	}





}
