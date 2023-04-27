package edu.iastate.cs228.hw1;

/**
 *  
 * @author Chen Sang
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten. 
 *
 */
public class Grass extends Living 
{
	public Grass (Plain p, int r, int c) 
	{
		super(p,r,c);
	}
	
	public State who()
	{
		
		return State.GRASS; 
	}
	
	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
	 */
	public Living next(Plain pNew)
	{
		census(population);
		if ( population[4]/ population[3]>= 3)
		{
			return new Empty(pNew,row,column);
		}
		else if( population[4] >= 3)
		{
			return new Rabbit(pNew,row,column,0); 
		}
		else
		{
			return new Grass(pNew,row,column);
		}
	}
}
