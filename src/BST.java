import java.util.ArrayList;

public class BST {
	BSTNode root;
	int size;

	public BST() {
		root = null;
		size = 0;
	}

	public BST(BSTNode root, int size) {
		this.root = root;
		this.size = size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void makeEmpty() {
		root = null;
		size = 0;
	}

	public Iterator find(int v) {
		BSTNode temp = root;

		while (temp != null && temp.data != v) {
			if (v < temp.data) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
		if (temp == null) // not found
			return null;
		return new TreeIterator(temp);
	}

	public Iterator insert(int v) {
		BSTNode parent = null;
		BSTNode temp = root;

		// This first part is almost the same as find,
		// but it has an extra pointer called parent.
		while (temp != null && temp.data != v) {
			if (v < temp.data) {
				parent = temp;
				temp = temp.left;

			} else {
				parent = temp;
				temp = temp.right;

			}
		}

		if (temp == null) {
			BSTNode n = new BSTNode(v, null, null, parent);
			if (parent == null) {
				root = n;
			} else if (v < parent.data) {
				parent.left = n;
			} else {
				parent.right = n;
			}
			size++;
			return new TreeIterator(n);
		} else {
			// we do nothing since
			// we don't want to add duplicated data.
			return null;
		}

	}

	public void remove(int v) {
		BSTNode parent = null;
		BSTNode temp = root;

		TreeIterator i = (TreeIterator) find(v);
		if (i == null) { // not found, we can not remove it
			return;
		}

		temp = i.currentNode;
		parent = temp.parent;

		// otherwise, we remove the value.
		size--;
		if (temp.left == null && temp.right == null) {// both subtrees are empty
			if (parent == null) {
				root = null;
			} else if (parent.left == temp) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		} else if (temp.left == null && temp.right != null) {// only right child
			if (parent == null) {
				root = temp.right;
				root.parent = null;
			} else if (parent.right == temp) {
				BSTNode n = temp.right;
				n.parent = parent;
				parent.right = n;
				// temp.right = null;
				// temp.parent = null;
			} else {// parent.left == temp
				BSTNode n = temp.right;
				n.parent = parent;
				parent.left = n;
			}
		} else if (temp.right == null && temp.left != null) {
			if (parent == null) {
				root = temp.left;
				root.parent = null;
			} else if (parent.right == temp) {
				BSTNode n = temp.left;
				n.parent = parent;
				parent.right = n;
			} else {
				BSTNode n = temp.left;
				n.parent = parent;
				parent.left = n;

			}

		} else {// temp has two subtrees
			BSTNode n = temp.right;
			TreeIterator itr = findMin(n);
			BSTNode minInSubtree = itr.currentNode;

			temp.data = minInSubtree.data;

			BSTNode parentOfMin = minInSubtree.parent;
			if (parentOfMin.left == minInSubtree) {
				parentOfMin.left = minInSubtree.right;

			} else { // parentOfMin.right == minInSubtree
				parentOfMin.right = minInSubtree.right;

			}

			if (minInSubtree.right != null) {
				minInSubtree.right.parent = parentOfMin;
			}

		}
	}

	public TreeIterator findMin(BSTNode n) {
		BSTNode temp = n;
		while (temp.left != null) {
			temp = temp.left;
		}
		TreeIterator itr = new TreeIterator(temp);
		return itr;
	}

	public void printAllData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(this.root.data);
		while (data.size() > 0) {
			int d = data.remove(0);
			System.out.print(d + " ");
			TreeIterator t = (TreeIterator) this.find(d);
			if (t.currentNode.left != null)
				data.add(t.currentNode.left.data);
			if (t.currentNode.right != null)
				data.add(t.currentNode.right.data);
		}
		System.out.println();
	}

	public TreeIterator findMax(BSTNode n) {
		// Implement this method
		if (this.isEmpty()) {
			return null;
		}
		TreeIterator p = new TreeIterator(this.root);
		while (p.hasNext()) {
			try {
				p.next();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public void cloneTree(BST tt) throws Exception {
		// Implement this method
		this.makeEmpty();
		if (tt.isEmpty())
			return;
		var data = new ArrayList<Integer>();
		data.add(tt.root.data);
		while (data.size() > 0) {
			int d = data.remove(0);
			var t = (TreeIterator) tt.find(d);
			this.insert(d);
			if (t.currentNode.left != null)
				data.add(t.currentNode.left.data);
			if (t.currentNode.right != null)
				data.add(t.currentNode.right.data);
		}
		// data.forEach(System.out::println);
	}

	public int findNextData(int n) {
		// Implement this method
		if (this.isEmpty())
			return n;
		var p = (TreeIterator) this.findMin(this.root);
		try {
			while (p.currentNode != null) {
				int temp = p.currentNode.data;
				if (temp == n)
					return p.next();
				else if (temp > n)
					return temp;
				p.next();
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return n;
	}

	public static void main(String[] args) throws Exception {
		// Printing example.
		// You can print how the tree looks to help with debugging.

		BSTNode r = new BSTNode(7);
		BST t = new BST(r, 1);
		t.insert(3);
		t.insert(11);
		t.insert(2);
		t.insert(5);
		t.insert(8);

		BTreePrinter.printNode(t.root);
		t.printAllData();
		BST t1 = new BST();
		t1.cloneTree(t);
		BTreePrinter.printNode(t1.root); // Must be exactly same as t

	}

}
