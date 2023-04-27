package edu.iastate.cs228.hw2;

/**
 *  
 * @author Chen Sang
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// TODO 
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		PointScanner[] scanners = new PointScanner[4]; 
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		// A sample scenario is given in Section 2 of the project description. 
		int trail    = 1;
		int inputNum = 0;
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning\n" + "\n"
				+ "keys: 1 (random integers)  2 (file input)  3 (exit)");
		Scanner sc = new Scanner(System.in);
		while(inputNum != 3)
		{
			System.out.println("Trail " + trail + ": ");
			inputNum = sc.nextInt();
			if(inputNum == 1)
			{
				System.out.println("Enter number of random points: ");
				int rdPoint = sc.nextInt();  // read random points
				Random r    = new Random();  // create random object
				Point[] p   = generateRandomPoints(rdPoint,r); // generate random point[]
				scanners[0] = new PointScanner(p,Algorithm.SelectionSort);
				scanners[1] = new PointScanner(p,Algorithm.InsertionSort);
				scanners[2] = new PointScanner(p,Algorithm.MergeSort);
				scanners[3] = new PointScanner(p,Algorithm.QuickSort);
			}
			else if (inputNum == 2)
			{
				System.out.println("Points from file\n" + "File name: ");
				String s    = sc.next();
				scanners[0] = new PointScanner(s,Algorithm.SelectionSort);
				scanners[1] = new PointScanner(s,Algorithm.InsertionSort);
				scanners[2] = new PointScanner(s,Algorithm.MergeSort);
				scanners[3] = new PointScanner(s,Algorithm.QuickSort);
			}
			else if(inputNum == 3)
			{
				break;
			}
			System.out.println("\nalgorithm   size    time(ns)");
			System.out.println("----------------------------------");
			for(int i = 0; i < 4; i++)
			{
				scanners[i].scan();
				System.out.println(scanners[i].stats()); //+ scanners[i].toString());
			}
			System.out.println("----------------------------------\n");
			trail ++;
		}
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if(numPts < 1)
		{
			throw new IllegalArgumentException();
		}
		else 
		{
			Point[] p = new Point[numPts]; // initialized a new p with same length

			for(int i = 0; i < numPts; i++)
			{
				p[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
			}
			return p; 
		}
	}
}
