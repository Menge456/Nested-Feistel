import java.util.ArrayList;
import java.util.Scanner;
public class fDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method 
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> message = new ArrayList<Integer>();
	    int input = 0;
	    
	    //getting the message
	    for(int i = 0; i < 8; i++) {
	    	message.add((int) (Math.random() * 2));
	    	System.out.print(message.get(i));
	    }
	    System.out.println();
	    
	    
	    
	    ArrayList<Integer> left = new ArrayList<Integer>();
	    ArrayList<Integer> right = new ArrayList<Integer>();
	    
	    int a = message.size()/2;
	    for(int i = 0; i < a; i++) 
	    	left.add( message.get(i));
	    
	    for(int i = a; i < a * 2; i++) 
	    	right.add(i-a, message.get(i));
	    
	   
	    	
	    Feistel first = new Feistel(left);
	    Feistel second = new Feistel(right);
	    
	    int[] fRet = first.encrypt();
	    int[] sRet = second.encrypt();
	    
	    ArrayList finale = new ArrayList<Integer>();
	    
	    for(int i = 0; i < fRet.length; i++)
	    	finale.add(fRet[i]);
	    
	    for(int i = 0; i < sRet.length; i++)
	    	finale.add(sRet[i]);
	    
	    Feistel fin = new Feistel(finale);
	    
	    int[] fine = fin.encrypt();
	    int[] fLeft = new int[fine.length/2];
	    int[] fRight = new int[fine.length/2];
	    
	    fine = fin.decrypt();
	    //checking my work
	    for(int i = 0; i < fLeft.length; i++)
	    	fLeft[i] = fine[i];
	    
	    for(int i = 0; i < fRight.length; i++)
	    	fRight[i] = fine[fRight.length + i];
	    
	    first.setProduct(fLeft);
	    second.setProduct(fRight);
	    
	    //decrypt final 2
	    first.decrypt();
	    second.decrypt();
	    System.out.println(first.toString() + second);
	    sc.close();
	    
	}

}
