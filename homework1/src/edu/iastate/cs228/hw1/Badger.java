package edu.iastate.cs228.hw1;

/**
 *  
 * @author Chen Sang
 *
 */

/**
 * A badger eats a rabbit and competes against a fox. 
 */
public class Badger extends Animal
{
	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Badger (Plain p, int r, int c, int a) 
	{
		super(p,r,c,a);
	}
	
	/**
	 * A badger occupies the square. 	 
	 */
	public State who()
	{
		
		return State.BADGER; 
	}
	
	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group of foxes. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew)
	{
		census(population);

		if (myAge() == 4)   // if age is 4, empty
		{
			return new Empty(pNew,row,column);
		}
		else if( population[0] == 1 &&  population[2] > 1)   // if only 1 badger and more than 1 fox, fox with 0 age 
		{
			return new Fox(pNew,row,column,0);
		}
		else if( population[0] +  population[2] >  population[4])    // if the number of badger and fox > rabbits, empty
		{
			return new Empty(pNew,row,column);
		}
		else
		{
			return new Badger(pNew,row,column,age + 1); // badger and age + 1
		}
		
	}
}
