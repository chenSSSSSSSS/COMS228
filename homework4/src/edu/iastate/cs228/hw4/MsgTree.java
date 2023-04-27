package edu.iastate.cs228.hw4;
/**
 * Class to encoding a compressed file 
 * @author Chen Sang 
 *
 */
public class MsgTree 
{
   /**
    * The character saved in each leaf
    */
   public char payloadChar;
   /**
    * left tree of the root
    */
   public MsgTree left;
   /**
    * right tree of the root
    */
   public MsgTree right;
   /**
    * the index number to track the whole tree
    * help to locate where to go in the tree
    */
   private static int staticCharIdx = 0;
   /**
    * total bits for all of the leaf by adding the length of the bits
    */
   private static int totalBit;
   /**
    * total character that we encoded from the tree schema
    */
   private static int totalChar;
   /**
    * The length of binary number that we read from the arch file
    */
   private static int compressedChar;
   /**
    * an uncompressed character is encoded with 16 bits
    */
   private static double unpressedChar = 16.0;
   /**
    * The total characters in the MESSAGE that we print out. 
    */
   private static int characters;
   
  
   /**
    * Constructor builded recursively by taking input string.First to save the character in current tree(root)
    * Stating reading the next char (left tree)in the string, if it's not a internal node then construct left tree with this char
    * otherwise, it is a internal node which means it has left and right children, then pass to the left tree recursively.
    * Do the same thing for the right tree part by increase the index number. 
    * @param encodingString - tree schema from the file 
    */
   public MsgTree(String encodingString)
   {
	   payloadChar = encodingString.charAt(staticCharIdx);   
	   
	   staticCharIdx++;
	   if(encodingString.charAt(staticCharIdx) != '^')
	   {
		   left = new MsgTree(encodingString.charAt(staticCharIdx));
	   }
	   else
	   {
		   left = new MsgTree(encodingString);
	   }
	   
	   staticCharIdx++;
	   if(encodingString.charAt(staticCharIdx)!='^')
	   {
		   right = new MsgTree(encodingString.charAt(staticCharIdx));
	   }
	   else
	   {
		   right = new MsgTree(encodingString);
	   }
   }
   /**
    * Constructor for the leaf node in the tree. The left and right child should be null.
    * @param payloadChar - character(data) for this leaf
    */
   public MsgTree(char payloadChar)
   {
	   this.payloadChar = payloadChar;
	   this.left        = null;
	   this.right       = null;
   }
   /**
    * This method is order to figure out the characters and its encoding information in this file.
    * performs recursive pre-order traversal of the MsgTree and prints all the characters and their bit codes:
    * The tree schema might contains space and new line character. If a node is null, then return.
    * If node contains new line character, then print it out the new line and its encoding. otherwise, 
    * print out the character that saved in the node. To read and left child of the current node, we have to check if 
    * left or right node is null, if not, then we move to the left or right node by recursion.
    * At the same time, we also count how many characters that encoded in this file, and how many bits take to represent each 
    * character.
    * @param root - current node where to start to read
    * @param code - start with a empty string
    */
   public void printCodes(MsgTree root, String code)
   {
	   if (root == null) return;
	   
	   if(root.payloadChar == '\n')   // special case if it is a new line character, then just print it out the new line char
	   {
		   System.out.println("\\n\t\t"+ code); 
		   totalChar += 1;            // track total Character in this file 
		   totalBit += code.length(); //track the total bits in this file by adding each code's length
	   }
	   else if(root.payloadChar != '^')// if it is a leaf, print out the char save in this leaf
	   {
		   System.out.println(root.payloadChar + "\t\t" + code); 
		   totalChar  += 1;
		   totalBit   += code.length();
	   } 
	   if(root.left != null) 
	   {
		   printCodes(root.left,  code + 0);
	   }
	   if(root.right != null)
	   {
		   printCodes(root.right,  code + 1);
	   }
   }
   /**
    * To decode the message. It would print the decoded message to the console
    * The overall output of the program should be the output of printCodes() followed by the output of decode().
    * Start at root in the tree, Repeat until at leaf, Scan one bit Go to left child if 0; else go to right child,
    * Print leaf payloadcharacter that saved in the node.
    * @param codes  - the tree we construct based on the file we just read
    * @param msg    - the binary number from the arch file
    */
   public void decode(MsgTree codes, String msg)
   {
	   System.out.println("Message:");

	   MsgTree tree   = codes;   
	   compressedChar = msg.length();

	   for(int i = 0; i <= msg.length() - 1; i++)
	   {
		   if(msg.charAt(i) == '0') // go left node
		   {
			   if(tree.left != null) tree = tree.left;
		   }
		   else if (msg.charAt(i) == '1') // go right node
		   {
			   if(tree.right != null) tree = tree.right;
		   }
		   if(tree.payloadChar != '^') // if it is a character, print out the character in the node
		   {
			   System.out.print(tree.payloadChar);
			   characters += 1;      // count how many characters in output message
			   tree        = codes;  // resign the tree in order to read the next character(tree start from the root again)
		   }
	   }
   }
   
   /**
    * This is to print out the overall information in this file.
    * The total avg bits/character --- read from printCode
    * The total character that print out in message --- read from decode
    * The sapce that we saved used equation that we get from the pdf. 
    * Compressedchar is the characters that compressed in the arch file.(binary number in arch file)
    * unpressedchar is 16bits, it has to time with character print it in message to get total unpressedChar value.
    */
   public static void printStats()
   {
	   System.out.println();
	   System.out.println("STATISTICS:");
	   System.out.println("Avg bits/char:" + "\t\t" + compressedChar/characters); 
	   System.out.println("Total Character:" + "\t" + characters);
	   double space =  Math.round((1 - compressedChar/(unpressedChar * characters)) * 100);
	   System.out.println("Space savings:" + "\t\t" +  space + "%");
   }
}
