package src;
import java.util.ArrayList;
import java.util.Date;
import java.security.MessageDigest;

public class Block { 
	public String hash; 
	public String previousHash; 
	private String data; 
	private long timestamp;
	public Block(String data, String previousHash) { 
		this.data = data; 
		this.previousHash = previousHash; 
		this.timestamp = new Date().getTime();
		this.hash = calculateHash(); 
	} 
	public String calculateHash() { 
		String calculatedhash = crypt.sha256( previousHash + Long.toString(timestamp) + data); 
		return calculatedhash; 
	} 
} 
class crypt {
	public static String sha256(String input){
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			int i = 0;
			byte[] hash= sha.digest(input.getBytes("UTF-8"));
			StringBuffer hexHash = new StringBuffer();
			while (i < hash.length) {
				String hex	= Integer.toHexString(	0xff & hash[i]);
				if (hex.length() == 1) hexHash.append('0');
				hexHash.append(hex);
				i++;
			}
			return hexHash.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static boolean isChainValid(ArrayList<Block> blockchain){
		Block currentBlock;
		Block previousBlock;
		boolean result = true;
		for (int i = 1;i < blockchain.size();i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				result = false;
			}
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				result = false;
			}
		}
		return result;
	}
}
