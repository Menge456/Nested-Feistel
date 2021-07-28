import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Feistel {
	private int[][] product; 
	private int[][] keys;
	private int rounds;
	public Feistel(){
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		
		//R2 xor F(R1 xor F(R2) ++ R1 xor F(R2) xor F(L2 xor F(L1 xor F(L2) ++ L1 xor F(L2) xor F(R2 xor (R1 xor F(R2) ++ R1 xor F(R2))) ++ L2 xor F(L1 xor F(L2) ++ L1 xor F(L2) xor F(R2 xor (R1 xor F(R2) ++ R1 xor F(R2))
		ArrayList<Integer> message = new ArrayList<Integer>();
	    int input = 0;
	    
	    //getting the message
	    while(input == 0 || input == 1)
	    {
	      System.out.println("\n0 or 1, anything outside of those 2 ends the cycle. ");
	      input = sc.nextInt();
	      if(input == 0 || input == 1)
	        message.add(input);
	    }
	    
	    //gets the message into 2 arrays, left and right
	      product = new int[2][message.size()/2];
	    
	    for(int i = 0; i < message.size()/2; i++)
	        product[0][i] = message.get(i);
	      
	    for(int i = message.size()/2; i < message.size(); i++)
	        product[1][i-message.size()/2] = message.get(i);
	    
	    //gets number of rounds the user wants it to go through
	    do{
	      System.out.print("Number of rounds: ");
	      rounds = sc.nextInt(); 
	    }while (rounds <= 0);

	    keys = new int[rounds][product[0].length];
	    for(int i = 0; i < keys.length; i++){
	      for(int j = 0; j < keys[i].length; j++){
	        //probably doesn't help that i have a random function in use tbh
	        keys[i][j] = r.nextInt(2);
	      }
	    }
	    
	  }
	
	public Feistel(ArrayList<Integer> message) {
		Random r = new Random();
		Scanner sc = new Scanner(System.in);
		product = new int[2][message.size()/2];
		    
		 for(int i = 0; i < message.size()/2; i++)
		        product[0][i] = message.get(i);
		      
		for(int i = message.size()/2; i < message.size(); i++)
		        product[1][i-message.size()/2] = message.get(i);
		    
		    //gets number of rounds the user wants it to go through
		do{
			System.out.print("Number of rounds: ");
			rounds = sc.nextInt(); 
		}while (rounds <= 0);
		keys = new int[rounds][product[0].length];

		for(int i = 0; i < keys.length; i++){
			for(int j = 0; j < keys[i].length; j++){
		        //probably doesn't help that i have a random function in use tbh
		        keys[i][j] = r.nextInt(2);
		     }
		}
		
	}
	  
	  //runs the vertible equation for the rounds
	  public int[] vertible(int[] key){ // "F = k XOR msg"
	    int[] newRight = new int[product[1].length];
	    //need to later change product[1][i] to newRight[i]  
	    for(int i = 0; i < newRight.length; i++)
	      newRight[i] = (key[i] + product[0][i]) % 2;
	    return newRight;
	  }
	  
	  //The rounds in which the left side and the right side do their thing
	  private void round(boolean en_or_de, int rounds){
	    int[] temp;
	    //swapping the left side to former right side after doing function
	    for(int i = 0; i < rounds; i++){
	      temp = product[1];
	      if(en_or_de)
	        product[1] = vertible(keys[i]);
	      else
	        product[1] = vertible(keys[(rounds-i)-1]);
	      
	      product[0] = temp;
	      
	    }
	    temp = product[1];
	    product[1] = product[0];
	    product[0] = temp;
	  }
	  
	  
	  public int[] encrypt(){
	    round(true,rounds);
	    int[] sum = new int[product[0].length + product[1].length];
	    int f = 0;
	    for(int x = 0; x < 2; x++)
	    {
	      for(int i = 0; i < product[x].length; i++)
	      {
	        if(f < sum.length){
	          sum[f] = product[x][i];
	         
	        }else{
	            throw new IndexOutOfBoundsException("F was too big at: " + f + " while sum's length was " +sum.length);
	        }
	        f++;
	      }
	    }
	    return sum;
	  } 

	public int[] decrypt(){
		round(false,rounds);
		int[] sum = new int[product[0].length + product[1].length];
		int f = 0;
		for(int x = 0; x < 2; x++){
        for(int i = 0; i < product[x].length; i++)
        {
          if(f < sum.length){
            sum[f] = product[x][i];
            f++;
            }
          }
        } 
	    return sum;
	}
	
	//changes the message in order for invested feistel to work
	public void setProduct(int[] a) {
		product = new int[2][a.length/2];
		for(int i = 0; i < a.length/2; i++)
			product[0][i] = a[i];
		      
	    for(int i = a.length/2; i < a.length; i++)
	        product[1][i-a.length/2] = a[i];
		
	}
	public String toString() {
		String ret = "";
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < product[i].length; j++)
				ret += product[i][j];
		return ret;
	}

}

