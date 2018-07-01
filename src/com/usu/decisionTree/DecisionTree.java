package com.usu.decisionTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class DecisionTree<A extends Comparable<A>> implements Serializable {

	private static final long serialVersionUID = -8943978335813436575L;

	protected Node<A> root;

	public DecisionTree() {
	}

	public DecisionTree(Node<A> firstNode) {
		this.root = firstNode;
	}

	public abstract void addNode(A obj);
	
	public abstract void addNode1(A obj);

	public abstract Node<A> getNode(A obj);

	public abstract Node<A> getMatchingSuccessNode(A obj);

	public static class Node<B extends Comparable<B>> implements Serializable, Comparable<B> {
		
		private static final long serialVersionUID = -6787095873203913330L;

		List<Node<B>> children;
		B value;

		public Node(List<Node<B>> children, B value) {
			super();
			this.children = children;
			this.value = value;
		}

		public Node(B value) {
			this(new ArrayList<>(), value);
		}

		@Override
		public int compareTo(B obj) {
			return value.compareTo(obj);
		}
		
		public List<String> getChildren() {
			List<String> childs = new ArrayList<String>();
			for (Node<B> node : children) {
				childs.add(node.value.toString());
			}
			
			return childs;
		}
	}
}

class StringDecisionTree extends DecisionTree<String> {

	private static final long serialVersionUID = 1L;

	public StringDecisionTree() {
		super(new Node(new ArrayList<String>(), ""));
	}

	@Override
	public void addNode(String obj) {
		DecisionTree.Node<String> currNode = root, retNode = null;
		for (int i = 0; i < obj.length(); i++) {
			String elem = obj.substring(i, i + 1);
			boolean found = false;
			for (DecisionTree.Node<String> node : currNode.children) {
				int compareVal = node.compareTo(elem);
				if (compareVal == 0) {
					currNode = node;
					found = true;
					break;
				}
			}
			// No nodes match the parameter, currNode is the best match
			if (!found) {
				DecisionTree.Node<String> newNode = new Node<String>(elem);
				currNode.children.add(newNode);
				currNode = newNode;
			}
		}
	}
	
	@Override
	public void addNode1(String obj) {
		DecisionTree.Node<String> currNode = root, retNode = null;
		String elem = obj;
		boolean found = false;
		for (DecisionTree.Node<String> node : currNode.children) {
			int compareVal = node.compareTo(elem);
			if (compareVal == 0) {
				currNode = node;
				found = true;
				break;
			}
		}
		// No nodes match the parameter, currNode is the best match
		if (!found) {
			DecisionTree.Node<String> newNode = new Node<String>(elem);
			currNode.children.add(newNode);
			currNode = newNode;
		}
	}

	@Override
	public DecisionTree.Node<String> getNode(String obj) {
		DecisionTree.Node<String> currNode = root;
		for (int i = 0; i < obj.length(); i++) {
			String elem = obj.substring(i, i + 1);
			boolean found = false;
			for (DecisionTree.Node<String> node : currNode.children) {
				int compareVal = node.compareTo(elem);
				if (compareVal == 0) {
					currNode = node;
					found = true;
					break;
				}
			}
			// No nodes match the parameter, currNode is the best match
			if (!found) {
				return null;
			}
		}
		return currNode;
	}

	@Override
	public DecisionTree.Node<String> getMatchingSuccessNode(String obj) {
		DecisionTree.Node<String> currNode = root;
		for (int i = 0; i < obj.length(); i++) {
			String elem = obj.substring(i, i + 1);
			boolean found = false;
			for (DecisionTree.Node<String> node : currNode.children) {
				int compareVal = node.compareTo(elem);
				if (compareVal == 0) {
					currNode = node;
					found = true;
					break;
				}
			}
			// No nodes match the parameter, currNode is the best match
			if (!found) {
				break;
			}
		}
		return currNode;
	}

}
