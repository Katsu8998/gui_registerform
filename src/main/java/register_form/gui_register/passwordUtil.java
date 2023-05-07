package register_form.gui_register;

import java.math.BigInteger;
import java.security.MessageDigest;

public class passwordUtil {
	public  String execute(String new_pass) {
		String sha = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] result = digest.digest(new_pass.getBytes());
			sha = String.format("%040x", new BigInteger(1, result));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return sha;
	}
}
