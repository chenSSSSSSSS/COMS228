package edu.iastate.cs228.hw3;

import java.util.Iterator;
import java.util.ListIterator;

public class MyStoutList {
	@SuppressWarnings("unchecked")
	public static void main(String[]args)
	{
		StoutList list2 = new StoutList(4);
		
		list2.add("I");
		list2.add("Hate");
		list2.add("this");
		list2.add("Project");
		list2.add("And");
		list2.add("I");
		list2.add("want");
		list2.add("to");
		list2.add("cry");
		
		
		
//	System.out.println();	
//	System.out.println(list2.toStringInternal());
//    System.out.println("test Iterator next()");
	Iterator<String> iter = list2.iterator();
	System.out.println(iter.next()); // I
	System.out.println(iter.next()); // hate
	System.out.println(iter.next()); // this
	System.out.println(iter.next()); // project 
	System.out.println(iter.next()); // And
	System.out.println(list2.remove(0)); //[(Hate, this, Project, And), (I, want, to, -), (cry, I, -, -)]
	System.out.println(list2.toStringInternal());
	System.out.println("test iterator hasNext()\n");
	System.out.println();
	System.out.println(iter.hasNext()); // true
	System.out.println(iter.next());    // I
	System.out.println(iter.next());    // want
	System.out.println(iter.next());    // to
	System.out.println(iter.next());    // cry
	//System.out.println(iter.hasNext()); // false
    System.out.println();
	
//	list2.add("a");
//	list2.add("c");
//	list2.add("b");
//	list2.add("s");
//	list2.add("d");
	
		
//	System.out.println(list2.toStringInternal());
//	System.out.println(list2.remove(6));
//	System.out.println(list2.toStringInternal());
	list2.add("I");
//	System.out.println(list2.toStringInternal());
//	list2.sort();
//	System.out.println(list2.toStringInternal());
	//list2.sortReverse();
//	System.out.println(list2.toStringInternal());
	
	
		
		
		
		
		
		
		
		
		
		
		
	
    ListIterator i = list2.listIterator();// list iterator 
    System.out.println("testing hasNext in ListIterator");
    System.out.println(list2.toStringInternal());
    System.out.println(i.next()); // return I
    System.out.println(i.next()); //return Hate
    System.out.println(i.next()); // return this
    System.out.println(i.next()); // project
    i.remove();
    System.out.println(list2.toStringInternal());
    System.out.println();// 
   
    System.out.println(i.previous()); // project
    System.out.println(i.previous());
    System.out.println(i.previous());
    i.remove();
    System.out.println(list2.toStringInternal());
    //System.out.println(i.previous());//  this
//    i.set("228"); // I, Hate, 228, project
//    System.out.println(list2.toStringInternal());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    i.set("WTF");  //( I, Hate, 228, project,) (WTF,I,want, to)
//    System.out.println(list2.toStringInternal());
//    System.out.println();
//    
//    
//    list2.add(9, "Shit");
//    System.out.println(list2.toStringInternal()); //[(I, Hate, 228, Project), (WTF, I, want, to), (cry, shit, -, -)]
//    System.out.println();
//    list2.add(4, "3");
//    System.out.println(list2.toStringInternal()); //[(I, Hate, 228, Project), (3, I, want, to), (cry, Shit, -, -)]
//    System.out.println();
//    list2.add(11, "omg");
//    System.out.println(list2.toStringInternal()); //[(I, Hate, 228, Project), (3, WTF, I, -), (want, to, -, -), (cry, Shit, omg, -)]
//    System.out.println();
//    list2.add(7, "die");
//    System.out.println(list2.toStringInternal());// (I, Hate, 228, Project), (3, WTF, I, die), (want, to, -, -), (cry, Shit, omg, -)]
//    System.out.println();
//    list2.add(12, "where"); //[(I, Hate, 228, Project), (3, WTF, I, die), (want, to, -, -), (cry, Shit, where, omg)]
//    System.out.println(list2.toStringInternal());
//    System.out.println();
//    list2.add(13, "is"); 
//    System.out.println(list2.toStringInternal());//[(I, Hate, 228, Project), (3, WTF, I, die), (want, to, -, -), (cry, is, Shit, -), (where, omg, -, -)]
//    System.out.println();
//    
//    list2.add(14, "LOL"); 
//    System.out.println(list2.toStringInternal());
//    System.out.println();
//    
//    list2.add(15, "LOL"); 
//    System.out.println(list2.toStringInternal());
//    System.out.println();
//    
//    list2.add(16, "wtf"); 
//    System.out.println(list2.toStringInternal());
//    System.out.println();
//    
//    System.out.println(i.next());//wtf
//    System.out.println(i.next());//I
//    i.set("YEah");
//    System.out.println(list2.toStringInternal());
//    //[(I, Hate, 228, Project), (3, WTF, YEah, die), (want, to, -, -), (cry, is, Shit, -), (where, wtf, LOL, -), (LOL, omg, -, -)]
//    System.out.println(i.previous());
//    i.set("Okay");
//    System.out.println(list2.toStringInternal());
//    //[(I, Hate, 228, Project), (3, WTF, Okay, die), (want, to, -, -), (cry, is, Shit, -), (where, wtf, LOL, -), (LOL, omg, -, -)]
//    System.out.println(i.next());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    i.set("OPSi");
//    System.out.println(list2.toStringInternal());//[(I, Hate, this, Project), (3, WTF, YEah, die), (OPSi, to, -, -), (cry, is, Shit, -), (where, wtf, LOL, -), (LOL, 
//    System.out.println(i.previous());
////    System.out.println(i.previous());
//    i.set("CNM");
//    System.out.println(list2.toStringInternal());
//    
//    System.out.println(i.next());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    System.out.println(i.next());
//    i.set("BYE");
//    System.out.println(list2.toStringInternal());
//    i.add("ILOVEU");
//    System.out.println(list2.toStringInternal());//[(I, Hate, 228, Project), (3, WTF, Okay, die), (CNM, to, -, -), (cry, is, BYE, ILOVEU), (where, wtf, LOL, -), (LOL, omg, -, -)]
////[(I, Hate, 228, Project), (3, WTF, Okay, die), (CNM, to, -, -), (cry, is, BYE, ILOVEU), (where, wtf, LOL, -), (LOL, omg, -, -)]
//    System.out.println(i.previous());//ILOVEU 
//    i.set("EAST");
//    System.out.println(i.next());//EAST
//    System.out.println(list2.toStringInternal());
//    i.set("DAMN");
//    System.out.println(list2.toStringInternal());
//    //[(I, Hate, 228, Project), (3, WTF, Okay, CNM), (OPSi, to, -, -), (cry, EAST, is, -), (BYE, ILOVEU, -, -), (where, wtf, LOL, -), (LOL, omg, -, -)]
//    

	}

}
