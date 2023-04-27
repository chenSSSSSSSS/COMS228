package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;
/**
 * Main class to test out MsgTree 
 * @author Chen Sang
 *
 */
public class MyTree {
	
	public static void main(String[]args) throws FileNotFoundException
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the archive file name");
		String fileName = sc.next();
		
		File f          = new File(fileName);
		Scanner s1      = new Scanner(f);
		String schema   = "";  // tree schema
		String path     = "";  // binary numbers in arch
		String line;
		
		schema          = s1.nextLine();
		line            = s1.nextLine();
		// if arch has 3 lines 
		if(s1.hasNextLine()) 
		{
			schema = schema + "\n" + line; 
			path   = s1.nextLine();
		}
		else // arch only have two lines
		{
			path   = line;  
		}
		s1.close();
		
		MsgTree tree = new MsgTree(schema);
		System.out.println("Character" + "\t" + "Code");
		System.out.println("-------------------------");
		tree.printCodes(tree, "");
		System.out.println();
		tree.decode(tree, path);
		MsgTree.printStats();
	}
}
	


/**
 * first attemp to wirte the code but not clear and effeciency
 */
//	private static String[] readMyFile(String file) throws FileNotFoundException
//	{
//		String[] arr = new String[2];
//		File f       = new File(file);
//		Scanner s1   = new Scanner(f);
//		String schema= "";
//		String path  = "";
//		String line  =  "";
//		
//		while(s1.hasNextLine())
//		{
//			line = s1.nextLine();
//			char c = line.charAt(0);
//			if(!Character.isDigit(c))
//			{
//				if(schema != "")
//				{
//					schema += "\n";
//				}
//				else
//				{
//					schema += line;
//				}
//			}
//			else
//			{
//				path += line;
//			}
//		}
//		
//		s1.close();
//		
//		arr[0] = schema;
//		arr[1] = path;
//		return arr;
	
//	//ArrayList arr1 = new ArrayList();
//	
//	
//	while(s1.hasNextLine())
//	{
//		line = s1.nextLine();
//		arr1.add(line);
////		line = s1.nextLine();
////		if(line.contains("^"))
////		{
////			if(!schema.equals(""))
////			{
////				schema += "\n";
////			}
//////			if(s1.hasNextLine() && line.contains("^"))
//////			{
//////				schema += "\n";
//////			}
////			schema += line;
////		}
////		else
////		{
////			path += line;
////		}
//	}
//	
//	if(arr1.size() > 2)
//	{
//		schema = arr1.get(0) + "\n" + arr1.get(1);
//		path   = arr1.get(2);
//	}
//	System.out.println(schema);
//	length = schema.length();
//	System.out.println(length);
//	
//	String yes = "^^ ^e^h^^gd^^^kI^yul^^^a^,^^bmp^oi^^^^\n" + "^c.f^^rnw^st";
//	int o = yes.length();
//	System.out.println(o);
//	System.out.println(yes);
//	MsgTree hh = new MsgTree(schema);
//	hh.printCodes(hh, "");
	
	
//	/Users/suncEI/Desktop/CS228/homework4/src/edu/iastate/cs228/hw4
//	String[] code = readMyFile(fileName);
//		MsgTree hh = new MsgTree(code[0]);
//	hh.printCodes(hh, "");
//	hh.decode(hh, path);
//	
//	MsgTree yea = new MsgTree("^8^ ^Z^^^\n^^`^^^^ro^xu^^i]^nmW^'^M-^,^^ba^\"^P^^<^)^VN^(^e>^^l^.^_^d^Y^|@^I;");
//	yea.printCodes(yea, "");
	//yea.decode(, );
//	}

