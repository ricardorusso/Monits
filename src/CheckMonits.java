
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public abstract class CheckMonits {
	
	private static final int EVERYDAY =69;
	private static final int QUINZENAL = 15;
	private static final String OK = "----------------OK--------------";
	private static final String NOK = "----------------NOK--------------";


	static Date date = new Date(System.currentTimeMillis());


	static Map<Monits, Integer> map = new HashMap<Monits, Integer>();

	private static void loadMaps() {
		map.put(new Monits("BATCH1", "/"), EVERYDAY);
		map.put(new Monits("BATCH2", "/nova"), EVERYDAY);
		map.put(new Monits("BATCH3", "/nova"), QUINZENAL);

		
	}
	public static long avgFileSize(Vector<LsEntry> vector) {
		long avg = 0;
		int count = 0;
		for (LsEntry lsEntry : vector) {
			String fileName = lsEntry.getFilename();
			if(fileName.endsWith(".txt")||fileName.endsWith(".xlsx")||fileName.endsWith(".csv")) {
				avg+= lsEntry.getAttrs().getSize();
				count++;
			}
		}
		return (avg/count);
	}
	public static void check() throws JSchException, SftpException {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Lisbon"));
		//connect
		JSch jsch = new JSch();
		Session session = jsch.getSession("user", "localhost", 22);
		session.setPassword("user");
		    
		session.setConfig("StrictHostKeyChecking", "no");
		System.out.println("Establishing Connection...");
		
		if(session.isConnected()) {
			session.disconnect();
		}else {
			session.connect();
			System.out.println("Connection established.");
		}	
		
		System.out.println("Creating SFTP Channel.");
		ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
		sftpChannel.connect();
		System.out.println("SFTP Channel created.");
 			
		//data hoje
		calendar.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dataStr = format.format(date);	
		
		loadMaps();
		boolean ok = false;
		System.out.println("Data: " + dataStr + " |Dia do mês: " + calendar.get(Calendar.DAY_OF_MONTH)+ "\n");
		boolean size = true;
		for (Entry<Monits, Integer> entry : map.entrySet()) {
			Vector <LsEntry> vector = sftpChannel.ls(entry.getKey().getDir()) ;
			
			if(entry.getValue() == calendar.get(Calendar.DAY_OF_MONTH) ||entry.getValue() == EVERYDAY) {
				if(vector.isEmpty()) {
					System.out.println("Diretorio vazio");
					System.out.println(NOK);
					break;
				}
				for (LsEntry string : vector) {
				
					if(string.getFilename().contains(dataStr) && (string.getFilename().endsWith(".csv")||string.getFilename().endsWith(".txt")||string.getFilename().endsWith(".xlsx"))) {
						if(string.getAttrs().getSize() == 0) {
							
							size = false;		
							System.out.println("\n"+entry.getKey().getNameBatch()+ " Tamanho zero ");
							System.out.println(NOK);
							break;
						}
						ok = true;				
						System.out.println("\n"+entry.getKey().getNameBatch());
						System.out.println("\n"+string.getFilename());
						System.out.println(OK);
					}
				}
				if(!ok && size) {
					System.out.println("\n"+entry.getKey().getNameBatch());
					System.out.println(NOK);
				}
			}
			
		}
		sftpChannel.disconnect();
        session.disconnect();
        //sftpChannel.cd
        System.out.println("Disconnect");
	}


	private CheckMonits() {
		
	}
   

}
