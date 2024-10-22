import java.util.Scanner;

public class ShawGen {
	
	public static void main(String [] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter router name in the following format: SHAW-XXXXXX");
		System.out.println("For example, enter as: SHAW-2A3C41");
		System.out.print("\nEnter the router name: SHAW-");
		String shawChoice = input.next();
		System.out.println("Entered: SHAW-" + shawChoice);
		
		char[] shawList = new char[6];
		for (int i = 0; i <= 5; i++)
			shawList[i] = shawChoice.charAt(i);

		RouterName routerName = new RouterName();
		
		int tempValue = 0;
		
		routerName.addSSID(shawList);
		routerName.getKeyDetails(tempValue);
		tempValue = 1;
		routerName.getKeyDetails(tempValue);
		tempValue = 2;
		routerName.getKeyDetails(tempValue);
		
		System.out.println("\n");
		System.out.println(routerName.getLowPegatron() + " " + routerName.getLowPegatronPass());
		System.out.println(routerName.getHighPegatron() + " " + routerName.getHighPegatronPass());
		System.out.println(routerName.getSSID());
		System.out.println(routerName.getLowHitron() + " " + routerName.getLowHitronPass());
		System.out.println(routerName.getHighHitron() + " " + routerName.getHighHitronPass());
		System.out.println(routerName.getPegatronKeyLow());
		System.out.println(routerName.getPegatronKeyHigh());
		
		int estimatedKey = ((routerName.getPegatronKeyLow() + routerName.getPegatronKeyHigh()) / 2);
		System.out.println("Estimated key: " + estimatedKey);
	}
}