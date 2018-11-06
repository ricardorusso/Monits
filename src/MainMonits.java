import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class MainMonits {

	public static void main(String[] args) {
		
//		Calendar c =Calendar.getInstance(TimeZone.getTimeZone("Europe/Lisbon"));
//			
//		System.out.println(c.get(Calendar.MONTH)+1);
//		System.out.println();
//		System.out.println();
//		
		try {
			CheckMonits.check();
		
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}

	}

}
