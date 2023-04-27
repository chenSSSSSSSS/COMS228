package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 *  
 * @author Chen Sang
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "MergeSort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		//base case 
		if(pts.length <= 1)
		{
			return;
		}
		
		//split into two new arrays
		int firstLength = pts.length / 2;
		Point [] first  = Arrays.copyOf(pts, firstLength);
		Point [] second = Arrays.copyOfRange(pts, firstLength,pts.length);
		
		//Recursively sort each half and merge back
		mergeSortRec(first);
		mergeSortRec(second);
		
		//merged result put into result 
		merge(first,second,pts);
	}

	private void merge(Point[] left, Point[] right, Point[]result)
	{
		int leftIndex  = 0; // index for left
		int rightIndex = 0; // index for right
		int index      = 0; // index for Point[] result
		
		while(leftIndex < left.length && rightIndex < right.length)
		{
			if(pointComparator.compare(left[leftIndex],right[rightIndex])<0)
			{
				result[index] = left[leftIndex];
				leftIndex++;
			}
			else
			{
				result[index] = right[rightIndex];
				rightIndex++;
			}
			index    ++;
		}
		//pick up any stragglers
		while(leftIndex < left.length)
		{
			result[index] = left[leftIndex];
			leftIndex++;
			index    ++;
		}
		while(rightIndex < right.length)
		{
			result[index] = right[rightIndex];
			rightIndex++;
			index     ++;
		}
		
	}
	// Other private methods if needed ...

}
