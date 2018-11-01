import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class MainMonits {

	public static void main(String[] args) {
		
		try {
			CheckMonits.check();
		
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}

	}

}
