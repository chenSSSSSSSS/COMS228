package edu.iastate.cs228.hw1;

/**
 *  
 * @author Chen Sang
 *
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal 
{	
	/**
	 * Creates a Rabbit object.
	 * @param p: plain  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (Plain p, int r, int c, int a) 
	{
		super(p,r,c,a);
	}
		
	// Rabbit occupies the square.
	public State who()
	{
		
		return State.RABBIT; 
	}
	
	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox.  
	 * @param pNew     plain of the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(Plain pNew)
	{
		census(population);

		if (myAge() == 3 )   // if age is 3, empty    
		{
			return new Empty(pNew,row,column);
		}
		else if( population[3] == 0)   //if there are no grass, empty
		{
			return new Empty(pNew,row,column); 
		}
		else if( (population[0] +  population[2] >=  population[4]) &&  (population[2] >  population[0]))    // if the number of badger and fox > rabbits, empty
		{
			return new Fox(pNew,row,column,0);
		}
		else if (population[0] >  population[4]) // if badger > rabbits,then badger
		{
			return new Badger(pNew,row,column, 0); // badger and age + 1
		}
		else
		{
			return new Rabbit(pNew,row,column,age + 1);
		}
	}
}
