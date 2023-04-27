package edu.iastate.cs228.hw1;

/**
 *  
 * @author Chen Sang
 *
 */

/**
 * A fox eats rabbits and competes against a badger. 
 */
public class Fox extends Animal 
{
	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (Plain p, int r, int c, int a) 
	{
		super(p,r,c,a);
	}
		
	/**
	 * A fox occupies the square. 	 
	 */
	public State who()
	{
		
		return State.FOX; 
	}
	
	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew)
	{ 
		census(population);

		if (myAge() == 6)   // if age is 4, empty   
		{
			return new Empty(pNew,row,column);
		}
		else if(population[0] > population[2])   // if number of badger > number of fox, Badger with 0 age 
		{
			return new Badger(pNew,row,column,0);  
		}
		else if( population[0] +  population[2] >  population[4])// if the number of badger and fox > rabbits, empty
		{
			return new Empty(pNew,row,column);
		}
		else
		{
			return new Fox(pNew,row,column,age + 1); // badger and age + 1
		}
	}
}
