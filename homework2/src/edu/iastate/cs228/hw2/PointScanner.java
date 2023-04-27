package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *  
 * @author Chen Sang
 *
 */

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null || pts.length == 0)
		{
		   throw new IllegalArgumentException();
		}
		points = new Point[pts.length];
		
		for(int i = 0; i < pts.length; i++)
		{
			points[i] = new Point(pts[i]);
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
		File f           = new File(inputFileName);
		Scanner sc       = new Scanner(f);
		int length       = 0;  // number of the integers in this file
		while(sc.hasNextInt())
		{
			sc.nextInt();
			length += 1;
		}
		if(length % 2 != 0)  // if it is odd, throw exception
		{
			throw new InputMismatchException();
		}
		sc.close();
		// if it is even, create an array and its length is half of length
		points    = new Point[length / 2]; 
		Scanner s = new Scanner(f);
		int i     = 0;                   // index of the array
		while(s.hasNextInt())
		{
			int x     = s.nextInt();     // read x value
			int y     = s.nextInt();     // read y value
			points[i] = new Point(x,y);  // create a point object into Point[]
			i        += 1;
		}
		s.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{ 
		AbstractSorter aSorter; 
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		if(sortingAlgorithm == Algorithm.InsertionSort){
			aSorter = new InsertionSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.MergeSort){
			aSorter = new MergeSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.SelectionSort){
			aSorter = new SelectionSorter(points);
		}
		else{
			aSorter = new QuickSorter(points);
		}
		//sort with x
		aSorter.setComparator(0);
		long xS    =  System.nanoTime(); //x start time
		aSorter.sort();
//		Point[] p = new Point[points.length];
//		aSorter.getPoints(p); 
//		
//		for(Point s: p )
//		{
//			System.out.print(s.toString()+"_");
//		}
		long xTime = System.nanoTime(); // x end time
		int x      = aSorter.getMedian().getX(); //save MCP x value
		
		//sort with y
		aSorter.setComparator(1);
		long yS    =  System.nanoTime(); // y start time
		aSorter.sort();
		long yTime = System.nanoTime(); // y end time
		int y      = aSorter.getMedian().getY(); // save MCP y value
		
		medianCoordinatePoint = new Point(x,y);  // set MCP
		scanTime              = (xTime - xS) + (yTime - yS);   //sum up the time of two sorting around
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String s = sortingAlgorithm + "\t" + points.length + "\t" + scanTime;
		return s; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		scan();
		return 	"MCP: ("  + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";  
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File f         = new File("output.txt");
		PrintWriter pw = new PrintWriter(f);
		pw.write(toString());
		pw.close();
	}		
}
