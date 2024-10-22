import java.io.FileReader;
import java.io.BufferedReader;

public class RouterName {
	
	private String SSID = "";
	private int keyLength;
	private int atHere = 0;
	private int pegatronSSID;
	private int hitronSSID;
	private int lowerPegatronSSID = -1000000000;
	private int higherPegatronSSID = 1000000000;
	private int lowerHitronSSID = -1000000000;
	private int higherHitronSSID = 1000000000;
	private int lowPegatron;
	private int highPegatron;
	private int lowHitron;
	private int highHitron;
	private int pegatronOperational = 0;
	private int hitronOperational = 0;
	private String knownLowPegatronPassword;
	private String knownHighPegatronPassword;
	private String knownLowHitronPassword;
	private String knownHighHitronPassword;
	private int knownLowPegatronPasswordInt;
	private int knownHighPegatronPasswordInt;
	private char[] ssidChar = new char[6];
	private int[] ssidInt = new int[6];
	private char[] pegatronChar = new char[6];
	private int[] pegatronInt = new int[6];
	private char[] hitronChar = new char[6];
	private int[] hitronInt = new int[6];

	public RouterName() {
	}
	
	void addUserChoice(int userChoice) {
		if (userChoice == 1)
			keyLength = 12;
		else 
			keyLength = 9;
	}
	
	void addSSID(char tempChar[]) {
		for (int i = 0; i < tempChar.length; i++) {
			ssidChar[i] = tempChar[i];
			SSID = SSID + ssidChar[i];
			ssidInt[i] = Character.getNumericValue(tempChar[i]);
		}
		for (int i = 0; i <= 5; i++) {
			pegatronSSID = pegatronSSID + (ssidInt[i] * ((int)Math.pow(16, (5 - i))));
			hitronSSID = hitronSSID + (ssidInt[i] * ((int)Math.pow(16, (5 - i))));
		}
	}
	
	public int getSSID() {
		return pegatronSSID;
	}
	
	public int getKeyLength() {
		return keyLength;
	}
	
	void getKeyDetails(int passedValue) {
		atHere = passedValue;
			
		FileReader firstReader;
		BufferedReader firstBuffer;
		FileReader pegatronReader;
		BufferedReader pegatronBuffer;
		FileReader hitronReader;
		BufferedReader hitronBuffer;
		
		if (atHere == 0) {
			try {
				firstReader = new FileReader("Shaw_Pin.txt");
				firstBuffer = new BufferedReader(firstReader);
				String s;
				while ((s = firstBuffer.readLine()) != null)
						checkDefaultPassword(s);
				firstReader.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (atHere == 1) {
			String pegatronPass;
			try {
				pegatronReader = new FileReader("Shaw_Pin.txt");
				pegatronBuffer = new BufferedReader(pegatronReader);
				while ((pegatronPass = pegatronBuffer.readLine()) != null) {
					if (pegatronPass.substring(0, 1).equals("P"))
						pegatronGeneration(pegatronPass);
				}
			
				pegatronReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (atHere == 2) {
			String hitronPass;
			try {
				hitronReader = new FileReader("Shaw_Pin.txt");
				hitronBuffer = new BufferedReader(hitronReader);
				while ((hitronPass = hitronBuffer.readLine()) != null) {
					if (hitronPass.substring(0, 1).equals("H"))
						hitronGeneration(hitronPass);
				}
			
				hitronReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}
	
	void checkDefaultPassword(String s) {
		if (s.substring(7, 13).equals(SSID)) {
			if (s.substring(0, 1).equals("H")) {
				System.out.println("SHAW-" + s.substring(7, 13) + ":" + s.substring(14, 26));
			}
			else {
				System.out.println("SHAW-" + s.substring(7, 13) + ":" + s.substring(14, 23));
			}
			System.exit(0);
		}
	}
	
	void pegatronGeneration(String s) {
		atHere = 1;
		int tempDecimalValuePegatron = 0;
		String tempStringPegatron = s.substring(7, 13);
		String tempPassPegatron = s.substring(14, 23);
		for (int i = 0; i <= 5; i++) {
			pegatronChar[i] = tempStringPegatron.charAt(i);
			pegatronInt[i] = Character.getNumericValue(pegatronChar[i]);
			tempDecimalValuePegatron = tempDecimalValuePegatron + (pegatronInt[i] * ((int)Math.pow(16, (5 - i))));
		}
			if (pegatronOperational == 1) {
				highPegatron = tempDecimalValuePegatron;
				knownHighPegatronPassword = tempPassPegatron;
				pegatronOperational = 0;
			}
		
		if (pegatronSSID > tempDecimalValuePegatron) {
			if ((tempDecimalValuePegatron - pegatronSSID) > lowerPegatronSSID) {
				lowerPegatronSSID = (tempDecimalValuePegatron - pegatronSSID);
				lowPegatron = tempDecimalValuePegatron;
				knownLowPegatronPassword = tempPassPegatron;
				pegatronOperational = 1;
			}
		}
	}
	
	void hitronGeneration(String s) {
		atHere = 2;
		int tempDecimalValueHitron = 0;
		String tempStringHitron = s.substring(7, 13);
		String tempPassHitron = s.substring(14, 26);
		for (int i = 0; i <= 5; i++) {
			hitronChar[i] = tempStringHitron.charAt(i);
			hitronInt[i] = Character.getNumericValue(hitronChar[i]);
			tempDecimalValueHitron = tempDecimalValueHitron  + (hitronInt[i] * ((int)Math.pow(16, (5 - i))));
		}
		
		if (hitronOperational == 1) {
			highHitron = tempDecimalValueHitron;
			knownHighHitronPassword = tempPassHitron;
			hitronOperational = 0;
		}
		
		if (hitronSSID > tempDecimalValueHitron ) {
			if ((tempDecimalValueHitron - hitronSSID) > lowerHitronSSID) {
				lowerHitronSSID = (tempDecimalValueHitron  - hitronSSID);
				lowHitron = tempDecimalValueHitron;
				knownLowHitronPassword = tempPassHitron;
				hitronOperational = 1;
			}
		}
	}
	
	public int getLowPegatron() {
		return lowPegatron;
	}
	
	public int getHighPegatron() {
		return highPegatron;
	}
	
	public int getLowHitron() {
		return lowHitron;
	}
	
	public int getHighHitron() {
		return highHitron;
	}
	
	public int getLowPegatronPass() {
		knownLowPegatronPasswordInt = Integer.parseInt(knownLowPegatronPassword);
		return knownLowPegatronPasswordInt;
	}
	
	public int getHighPegatronPass() {
		knownHighPegatronPasswordInt = Integer.parseInt(knownHighPegatronPassword);
		return knownHighPegatronPasswordInt;
	}
	
	public String getLowHitronPass() {
		return knownLowHitronPassword;
	}
	
	public String getHighHitronPass() {
		return knownHighHitronPassword;
	}
	
	public int getPegatronKeyLow() {
		int knownDecimal = getLowPegatron();
		int unknownDecimal = pegatronSSID;
		int knownPassword = getLowPegatronPass();
		int unknownPassword = 0;
		int rConstant = 0;
		int qConstant;
		
		for (int i = 0; i < 20; i++) {
			if (i == 0)
				rConstant = (unknownDecimal - knownDecimal) - knownPassword;
			if (i > 0) {
				qConstant = (rConstant + knownPassword) /2;
				rConstant = qConstant;
			}
		}
		return rConstant;
	}
	
	public int getPegatronKeyHigh() {
		int knownDecimal = getHighPegatron();
		int unknownDecimal = pegatronSSID;
		int knownPassword = getHighPegatronPass();
		int unknownPassword = 0;
		int rConstant = 0;
		int qConstant;
		
		for (int f = 0; f < 20; f++) {
			if (f == 0)
				rConstant = (unknownDecimal - knownDecimal) - knownPassword;
			if (f > 0) {
				qConstant = (rConstant + knownPassword) /2;
				rConstant = qConstant;
			}
		}
		return rConstant;
	}
}