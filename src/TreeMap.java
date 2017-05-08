
import java.util.LinkedList;
import java.util.List;

public class TreeMap <K extends Comparable<K>, V> {

	
	private Node root;
	private int nodeCount = 0; 
	public class Node
	{
		protected KeyValuePair keyvalues1;
		protected KeyValuePair keyvalues2;
		protected Node parent, left, middle, right;
		
		public Node(KeyValuePair keyValues1)
		{
			this.keyvalues1 = keyValues1;
		}
		
		public Node(KeyValuePair keyValues1, KeyValuePair keyValues2)
		{
			this.keyvalues1 = keyValues1;
			this.keyvalues2 = keyValues2;
		} 
	}
	public class FourNode extends Node
	{
		protected KeyValuePair keyvalues3;
		protected Node middle2;
		
		public FourNode (KeyValuePair keyValues1, KeyValuePair keyValues2, KeyValuePair keyValues3)
		{
			super(keyValues1, keyValues2);
			this.keyvalues3 = keyValues3;
		}
	}
	
	
	/**
	 * @param key
	 * @return
	 */
	public V get(K key)
	{
		//Search for our key.
		Node searchResult = getNode(key, root, true);
		
		if (searchResult != null)
		{
			//Since it can be either a two or a ThreeNode we need to return the correct value.
			if (searchResult.keyvalues1.key.equals(key))
				return searchResult.keyvalues1.value;
			else
				return searchResult.keyvalues2.value;
		}
		else
			return null;
	}
	
	
	/**
	 * @param key
	 * @param value
	 */
	public void put(K key, V value)
	{
		
		if (value == null)
			throw new IllegalArgumentException("Null values not supported!");
		
		
		if (root == null)
		{
			root = new Node(new KeyValuePair(key, value));
			nodeCount = 1; 
			return;
		}
		
		
		Node foundNode = getNode(key, root, false);
		
		
		if (foundNode.keyvalues2 == null)
		{
			if (key.compareTo(foundNode.keyvalues1.key) < 0)		
			{
		
				foundNode.keyvalues2 = foundNode.keyvalues1;
				foundNode.keyvalues1 = new KeyValuePair(key, value);
			}
			else if (key.equals(foundNode.keyvalues1.key)) 			
				foundNode.keyvalues1.value = value;
			else if (key.compareTo(foundNode.keyvalues1.key) > 0) 	
				foundNode.keyvalues2 = new KeyValuePair(key, value);
			
		}
		else 
		{		

			
			if (foundNode.keyvalues1.key.equals(key))
			{
				foundNode.keyvalues1.value = value;
				return;
			}
			if (foundNode.keyvalues2.key.equals(key))
			{
				foundNode.keyvalues2.value = value;
				return;
			}
			
			FourNode tempFourNode = null;
			
			
			if (key.compareTo(foundNode.keyvalues1.key) <= -1)
				tempFourNode = new FourNode(new KeyValuePair(key, value), foundNode.keyvalues1, foundNode.keyvalues2);
			
			
			else if (key.compareTo(foundNode.keyvalues1.key) >= 1 && key.compareTo(foundNode.keyvalues2.key) <= -1)
				tempFourNode = new FourNode(foundNode.keyvalues1, new KeyValuePair(key, value), foundNode.keyvalues2);
			
			
			else if (key.compareTo(foundNode.keyvalues2.key) >= 1)
				tempFourNode = new FourNode(foundNode.keyvalues1, foundNode.keyvalues2, new KeyValuePair(key, value));
			
			
			put4NodeInTree(foundNode, tempFourNode);
		}
	}

	
	public int size()
	{
		return nodeCount;
	}
	
	
	public Iterable<KeyValuePair> keys()
	{
			LinkedList<KeyValuePair> results = new LinkedList<KeyValuePair>();
		
		
		traverseTree(root, results);

		return results;
	}
	
	
	public Iterable<KeyValuePair> keys(K lo, K hi)
	{
		List<KeyValuePair> treeKeyValues = (LinkedList<KeyValuePair>)keys();
		List<KeyValuePair> result = new LinkedList<KeyValuePair>();
		
		int indexOfLow = indexOfKeyInList(lo, treeKeyValues);
		int indexOfHigh = indexOfKeyInList(hi, treeKeyValues)+1;
		
		if (indexOfLow == -1 || indexOfHigh == -1)
			throw new IllegalArgumentException("Key lo and/or hi not found in the tree.");
		
		result.addAll(treeKeyValues.subList(indexOfLow, indexOfHigh));
		
		return result;
	}
	
	
	public int depth()
	{
		
		int depthCount = 0;
		
		Node curNode = root; 
		
		
		while (curNode != null)
		{
			curNode = curNode.left;
			depthCount++;
		}
		depthCount--; //We counted the "null-level" under the leaf, remove that.
		
		return depthCount;
	}
	

	public int howMuchMore()
	{
		
		int[] totalRes = CountNodeTypes(root);
		
		
		int depth = depth();
		double numElementsInFullTree = 0;
		
		
		for (int n = depth; n >= 0; n--)
			numElementsInFullTree += 2 * Math.pow(3, (depth - n));
		
		
		Double result = numElementsInFullTree - (2 * totalRes[1] + totalRes[0]);
		
		return result.intValue();
	}

	public float density()
	{
		
		int[] totalRes = CountNodeTypes(root);
		
		
		return (float) (totalRes[0]*2 + totalRes[1]*3) / (totalRes[0] + totalRes[1]);
	}


	private int[] CountNodeTypes (Node curNode) {
		
		
		int[] totalRes = new int[2];
		
		//Current node is a... TwoNode.
		if (curNode.keyvalues2 == null)
			totalRes[0]++;
		else //... ThreeNode.
			totalRes[1]++;
		
		//Count the left children if we have any.
		if (curNode.left != null)
		{
			int[] childRes = CountNodeTypes(curNode.left);
			totalRes[0] += childRes[0];
			totalRes[1] += childRes[1];
		}

		//Count the middle children if we have any.
		if (curNode.middle != null) {
			int[] childRes = CountNodeTypes(curNode.middle);
			totalRes[0] += childRes[0];
			totalRes[1] += childRes[1];
		}

		//Count the right children if we have any.
		if (curNode.right != null) {
			int[] childRes = CountNodeTypes(curNode.right);
			totalRes[0] += childRes[0];
			totalRes[1] += childRes[1];
		}

		return totalRes;
	}
	
	
	private Node getNode(K key, Node startNode, boolean returnNullOnMissing)
	{
		//If we should return null on a missing key then the branch we've just gone into will be null if it is where the key should be.
		if (returnNullOnMissing)
		{
			if(startNode == null)
				return null;
		}
		else //If we should find the node the key should be in, we need to check for an empty branch before going down, so we can return before that happens. 
		{
			//Doesn't matter what branch we check for nulls since we have a balanced tree, if any branch is null we have a leaf node.
			if (startNode.left == null)
				return startNode;
		}
		
		//If equal to our first value, this is the same for both TwoNodes and ThreeNodes.
		if (key.equals(startNode.keyvalues1.key))
			return startNode;
		
		//If smaller than the first value search left, this is the same for both TwoNodes and ThreeNodes. 
		if (key.compareTo(startNode.keyvalues1.key) < 0)
			return getNode(key, startNode.left, returnNullOnMissing);
		
		//TwoNode.
		if (startNode.keyvalues2 == null)
		{
			//Must be greater than, search middle cause that's the right child in two nodes.
			return getNode(key, startNode.middle, returnNullOnMissing); //Middle is right child for TwoNodes.
		}
		else //ThreeNode
		{
			//If equal to our second value.
			if (key.equals(startNode.keyvalues2.key))
				return startNode;
			
			//If in the middle, search middle child.
			if (key.compareTo(startNode.keyvalues1.key) > 0 && key.compareTo(startNode.keyvalues2.key) < 0)
				return getNode(key, startNode.middle, returnNullOnMissing);
			else //If greater than our second value, search right.
				return getNode(key, startNode.right, returnNullOnMissing);
		}	
	}
	

	private void put4NodeInTree(Node currentNode, FourNode tmpFourNode)
	{
		//Split the FourNode into a TwoNode.	
		Node splitResult = Convert4to2(tmpFourNode);
		nodeCount++;
		
		//If we've worked our way up to the root, then we don't need to merge, just set the root to the split result.
		if (currentNode == root)
		{
			root = splitResult;
			nodeCount++;
			//Normally splitting produces two new children, and then merging will reduce that by one.
			//Since we are not merging now we need to count the split above one more time.  
		}
		else
		{
			Node parent = currentNode.parent;
		
			//Merge the splitResult with the parent.
			FourNode mergeResult = MergeNodes (parent, splitResult);
			
			//If the merge result is null the parent was a TwoNode, and we are done. It's inserted.
			//If not, we need to merge the new FourNode we have with the parent and repeat.
			if (mergeResult != null)
				put4NodeInTree(parent, mergeResult);
		}		
	}
	
	/**
	 * Splits a FourNode into a TwoNode.
	 * @param inNode The FourNode to split.
	 * @return Returns the root of the new TwoNode.
	 */
	private Node Convert4to2(FourNode inNode)
	{
		
		Node newRoot = new Node (inNode.keyvalues2);
		
		//New left, a, is the left child. New right, c, is the right child.
		Node newLeft = new Node (inNode.keyvalues1);
		Node newRight = new Node (inNode.keyvalues3);
	
		//Set the new children to the NewRoot.
		newRoot.left = newLeft;
		newRoot.middle = newRight;
		
		//Link these to the root node.
		newLeft.parent = newRoot;
		newRight.parent = newRoot;
		
		//Move branch 1, and relink its parent if we have such a branch.
		newLeft.left = inNode.left;
		
		if (newLeft.left != null)
			newLeft.left.parent = newLeft;
		
		//Move branch 2, and relink its parent if we have such a branch.
		newLeft.middle = inNode.middle;
		
		if (newLeft.middle != null)
			newLeft.middle.parent = newLeft;
			
		//Move branch 3, and relink its parent if we have such a branch.
		newRight.left = inNode.middle2;
		
		if (newRight.left != null)
			newRight.left.parent = newRight;
		
		//Move branch 4, and relink its parent if we have such a branch.
		newRight.middle = inNode.right;
		
		if(newRight.middle != null)
			newRight.middle.parent = newRight;
		
		return newRoot;
	}

	
	private void traverseTree (Node curNode, List<KeyValuePair> treeItems)
	{
		//If leaf node.
		if (curNode.left == null)
		{
			//Add first value.
			treeItems.add(curNode.keyvalues1);
			
			//If leaf is ThreeNode, then add second value.
			if (curNode.keyvalues2 != null)
				treeItems.add(curNode.keyvalues2);
		}
		else if (curNode.keyvalues2 == null) //If TwoNode.
		{
			traverseTree(curNode.left, treeItems); //Add lesser values first.
			treeItems.add(curNode.keyvalues1); //Then this value.
			traverseTree(curNode.middle, treeItems); //And greater values.
		}
		else //If ThreeNode.
		{
			traverseTree(curNode.left, treeItems); //Lesser values.
			treeItems.add(curNode.keyvalues1); //Low value.
			traverseTree(curNode.middle, treeItems); //Middle values.
			treeItems.add(curNode.keyvalues2); //High value.
			traverseTree(curNode.right, treeItems); //Higher values.
		}
	}
	
	
	private FourNode MergeNodes(Node treeNode, Node separateNode)
	{		
		//The separate node we are sending in is assumed to be a TwoNode.
		
		//Possible merge result.
		FourNode newFourNode = null;
		
		//If the node in the tree we are merging with is a TwoNode.
		if (treeNode.keyvalues2 == null)
		{
			//The thing we are merging is smaller than the treeNode's key.
			if(separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) <= -1)
			{
				//Move the key/values to the right and insert.
				treeNode.keyvalues2 = treeNode.keyvalues1;
				treeNode.keyvalues1 = separateNode.keyvalues1;
				
				//Move the children to the right and insert the separateNode's children into the tree node.
				treeNode.right = treeNode.middle;
				treeNode.middle = separateNode.middle; //Right child in the separateNode.
				treeNode.left = separateNode.left;
			}
			else if (separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) >= 1)
			{
				//Insert key/values.
				treeNode.keyvalues2 = separateNode.keyvalues1;
				
				//Insert the children.
				treeNode.right = separateNode.middle; //Right node in the separateNode.
				treeNode.middle = separateNode.left;
			}
			
			//Don't forget to relink the parent property after we've moved children around.
			separateNode.middle.parent = treeNode;
			separateNode.left.parent = treeNode;						
		}
		else //If the node in the tree we are merging with is a ThreeNode.
		{
			//If the key in the separate node is smaller than the three node's first key.
			if (separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) <= -1)
			{
				newFourNode = new FourNode(separateNode.keyvalues1, treeNode.keyvalues1, treeNode.keyvalues2);
				
				newFourNode.left = separateNode.left;
				newFourNode.middle = separateNode.middle;
				newFourNode.middle2 = treeNode.middle;
				newFourNode.right = treeNode.right;
			}
			else if (separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) > 0 && separateNode.keyvalues1.key.compareTo(treeNode.keyvalues2.key) < 0)
			{
				newFourNode = new FourNode(treeNode.keyvalues1, separateNode.keyvalues1, treeNode.keyvalues2);
				
				newFourNode.left = treeNode.left;
				newFourNode.middle = separateNode.left;
				newFourNode.middle2 = separateNode.middle;
				newFourNode.right = treeNode.right;
			}
			else //If not smaller or in the middle of our values it must be bigger.
			{
				newFourNode = new FourNode(treeNode.keyvalues1, treeNode.keyvalues2, separateNode.keyvalues1);
				
				newFourNode.left = treeNode.left;
				newFourNode.middle = treeNode.middle;
				newFourNode.middle2 = separateNode.left;
				newFourNode.right = separateNode.middle;
			}
			
			//Relink the children to our FourNode.
			newFourNode.left.parent = newFourNode;
			newFourNode.middle.parent = newFourNode;
			newFourNode.middle2.parent = newFourNode;
			newFourNode.right.parent = newFourNode;			
		}
		
		//If no new FourNode then this will be null. 
		return newFourNode;
	}

	
	private int indexOfKeyInList(K key, List<KeyValuePair> listToSearch)
	{
		for (int i = 0; i < listToSearch.size(); i++)
		{
			KeyValuePair element = listToSearch.get(i);

			if (element !=null && element.equals(new KeyValuePair (key, null)))
				return i;
		}
		return -1; //Not in the list.
	} 
	
	public class KeyValuePair {

		public K key;
		public V value;
		
		public KeyValuePair(K key, V value)
		{
			this.key = key;
			this.value = value;
		}
		
		public boolean equals(Object o)
		{
			//Assume we are comparing to another KeyValuePair with the same K, V.
			KeyValuePair obj = (KeyValuePair) o;
			
			//KeyValuePairs are equal if keys are equal.
			return this.key.equals(obj.key);
		}
		
	}

	
}