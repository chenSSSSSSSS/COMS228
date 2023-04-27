package edu.iastate.cs228.hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse; 
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.fail; 

/**
 * @author Chen Sang
 */
public class MyTest {

	@Test
	public void animalLifeForm() {
		Plain p   = new Plain(3);
		Badger b  = new Badger(p,0,0,0);
		assertEquals(0,b.myAge());
		assertEquals(0,b.row);
		assertEquals(0,b.column);
		assertEquals(3,p.getWidth());
		assertEquals(State.BADGER,b.who());
	}
	
	@Test
	public void badgerTest() throws FileNotFoundException{
		Plain even = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/public1-3x3.txt");
		Plain odd  = new Plain(even.getWidth());
		odd.grid[0][1] = even.grid[0][1].next(odd);	
		assertEquals(State.FOX,odd.grid[0][1].who()); // fox if only one badger and more than one fox
		assertEquals(0,((Animal) odd.grid[0][1]).myAge());
		
	}
	
	@Test
	public void foxTest() throws FileNotFoundException {
		Plain even = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/public1-3x3.txt");
		Plain odd  = new Plain(even.getWidth());
		odd.grid[0][2] = even.grid[0][2].next(odd);
		assertEquals(State.EMPTY,odd.grid[0][2].who());
	}
	
	@Test
	public void emptyTest() throws FileNotFoundException{
		Plain even = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/emptytestCase");
		Plain odd  = new Plain(even.getWidth());
		odd.grid[2][0] = even.grid[2][0].next(odd);
		assertEquals(State.FOX,odd.grid[2][0].who());
	}
	
	@Test
	public void rabbitTest() throws FileNotFoundException{
		Plain even = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/public1-3x3.txt");
		Plain odd  = new Plain(even.getWidth());
		odd.grid[1][2] = even.grid[1][2].next(odd);
		assertEquals(State.FOX,odd.grid[1][2].who());
	}
	
	@Test
	public void grassTest() throws FileNotFoundException{
		Plain even = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/grassTestCase");
		Plain odd  = new Plain(even.getWidth());
		odd.grid[1][1] = even.grid[1][1].next(odd);
		assertEquals(State.GRASS,odd.grid[1][1].who());
	}
	
	@Test
	/*
	 * create a plain from file and write this plain into a new file
	 */
	public void plainTest() throws FileNotFoundException
	{
		Plain p        = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/grassTestCase");
		p.write("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/nothing.txt");
	}
	
	@Test
	public void livingConstructor()
	{
		Plain p          = new Plain(3);
		Living [][] grid = new Living [3][3];
		grid [0][0]      = new Grass(p,0,0);
		grid [0][1]      = new Badger(p,0,1,3);
		
		assertEquals(0,grid[0][0].column);
		assertEquals(0,grid[0][0].row);
		assertEquals(State.GRASS, grid[0][0].who());
		
		assertEquals(1,grid[0][1].column);
		assertEquals(0,grid[0][1].row);
		assertEquals(State.BADGER, grid[0][1].who());
		assertEquals(3,((Animal)grid[0][1]).myAge());
	}
	
	@Test
	public void wildlifeTest() throws FileNotFoundException
	{
		Plain even = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/public1-3x3.txt");
		Plain odd  = new Plain(even.getWidth());
		for(int a = 0; a < 5; a++){
			if(a % 2 == 0){
				Wildlife.updatePlain(even,odd);
			}
			else{
				Wildlife.updatePlain(odd,even);
			}
		}
		Plain lastP = new Plain("/Users/suncEI/Desktop/CS228/homework1/src/edu/iastate/cs228/hw1/public1-5cycles.txt");
		assertTrue(odd.toString().equals(lastP.toString()));
	}

	
}
