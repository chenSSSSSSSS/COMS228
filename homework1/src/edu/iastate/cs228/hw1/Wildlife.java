package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Chen Sang
 *
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with
 * squares inhabited by badgers, foxes, rabbits, grass, or none.
 * 
 */
public class Wildlife 
{
	/**
	 * Update the new plain from the old plain in one cycle. 
	 *  For every life form (i.e., a Living object) in the grid pOld, generate a Living object in the grid pNew at the corresponding location such that 
		the former life form changes into the latter life form. Employ the method next() of the Living class. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew)
	{
		for( int row = 0; row < pOld.getWidth(); row ++)
		{
			for (int col = 0; col < pOld.getWidth(); col++)
			{
				pNew.grid[row][col] = pOld.grid[row][col].next(pNew);
			}
		}
	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * // Generate wildlife simulations repeatedly like shown in the 
		// sample run in the project description. 
		// 
		// 1. Enter 1 to generate a random plain, 2 to read a plain from an input
		//    file, and 3 to end the simulation. (An input file always ends with 
		//    the suffix .txt.)
		// 
		// 2. Print out standard messages as given in the project description. 
		// 
		// 3. For convenience, you may define two plains even and odd as below. 
		//    In an even numbered cycle (starting at zero), generate the plain 
		//    odd from the plain even; in an odd numbered cycle, generate even 
		//    from odd. 
	    // 4. Print out initial and final plains only.  No intermediate plains should
		//    appear in the standard output.  (When debugging your program, you can 
		//    print intermediate plains.)
		// 
		// 5. You may save some randomly generated plains as your own test cases. 
		// 
		// 6. It is not necessary to handle file input & output exceptions for this 
		//    project. Assume data in an input file to be correctly formated. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	  
		Plain even = null;   				 // the plain after an even number of cycles 
		Plain odd  = null;                   // the plain after an odd number of cycles
		Scanner sc      = new Scanner(System.in);
		int numInput    = 0;
		int numTrail    = 1;
		int len         = 0;
		int cycles      = 0;
		String fileName = "";

		System.out.println("Simulation of Wildlife of the Plain \n" + 
				"keys: 1 (random plain)  2 (file input)  3 (exit)");
		while(numInput != 3){ 
			System.out.println("Trail " + numTrail);
			numInput = sc.nextInt();

			if(numInput == 1){
				System.out.println("Random Plain \n" + "Enter grid width: ");
				len    = sc.nextInt();
				even   = new Plain(len);
				even.randomInit();
			}
			else if(numInput == 2){
				System.out.println("Plain input from a file \n" + "Enter file name: ");
				fileName = sc.next();
				even     = new Plain(fileName);
			}
			else if(numInput == 3){
				break;
			}
			odd     = new Plain(even.getWidth());
			
			System.out.println("Enter the number of cycles: ");
			cycles  = sc.nextInt();
			System.out.println("Initial Plain \n" + even);

			for(int a = 0; a < cycles; a++){
				if(a % 2 == 0){
					updatePlain(even,odd);
				}
				else{
					updatePlain(odd,even);
				}
			}
			if(cycles % 2 == 0 ){
				System.out.println("Final plain \n" + even);
			}
			else {
				System.out.println("Final plain \n" + odd);
			}
			numTrail += 1;
		}
	}
}
