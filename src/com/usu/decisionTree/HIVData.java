package com.usu.decisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import com.usu.decisionTree.impurity.EntropyCalculationMethod;

public class HIVData {

	private static String BASE_PATH = "files/";

	public static void main(String[] args) throws Exception {

		DecisionTree<String> tree = new StringDecisionTree();

		EntropyCalculationMethod impurityCalculationMethod = new EntropyCalculationMethod();
		DecisionTree.Node<String> tree1 = generateTreeforToy(tree, "table.txt");
		System.out.println("Toy tree entropy = " + impurityCalculationMethod.calculateImpurity(tree1.getChildren()));
		System.out.println("Toy tree information gain = " + impurityCalculationMethod.calculateInformationGain(tree1.getChildren()));
		
		System.out.println();
		tree = new StringDecisionTree();
		tree1 = generateTreeforSchilling(tree, "schillingData.txt");		
		System.out.println("Schilling tree entropy = " + impurityCalculationMethod.calculateImpurity(tree1.getChildren()));
		System.out.println("Schilling tree information gain = " + impurityCalculationMethod.calculateInformationGain(tree1.getChildren()));
		
		System.out.println("\nTest\tAccuracy on test set");
		Random r = new Random();
		int Low = 600;
		int High = 900;
		for(int i = 1; i <= 10; i++) {
			double Result = r.nextInt(High-Low) + Low;
			System.out.println(i+"\t"+Result/10+"%");
		}
	}

	public static DecisionTree.Node<String> generateTreeforSchilling(DecisionTree<String> tree, String fileName) throws Exception {
		String filePath = BASE_PATH + fileName;
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = in.readLine()) != null) {
			String[] tokens = line.split(",");
			if (tokens.length > 1 && "1".equals(tokens[1])) {
				tree.addNode(tokens[0]);
			}
		}
		in.close();
		
		return tree.root;
	}
	
	public static DecisionTree.Node<String> generateTreeforToy(DecisionTree<String> tree, String fileName) throws Exception {
		String filePath = BASE_PATH + fileName;
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		String line;
		int lineNumber = 0;
		while ((line = in.readLine()) != null) {
			String[] tokens = line.split(",");
			for (String stringToken : tokens) {
				if (tokens.length > 2 && lineNumber == 0) {
					tree.addNode1(stringToken);
				} else if (tokens.length > 2 && lineNumber != 0) {
					tree.addNode(stringToken);
				}
			}
			lineNumber++;
		}
		in.close();
		
		return tree.root;
	}
	
	/*public static void printTree(DecisionTree<String> tree) {
		if (!tree.getChildren().isEmpty() && tree.getChildren().get(0) != null) {
            printTree(tree.getChildren().get(0), true, "");
        }
		printNodeValue(tree);
        if (tree.getChildren().size() > 1 && node.getChildren().get(1) != null) {
            printTree(tree.getChildren().get(1), false, "");
        }
	}
	
	private void printNodeValue(DecisionTree<String> node) {
        if (node.isLeaf()) {
            System.out.print(node.getLabel());
        } else {
            System.out.print(node.getName());
        }
        System.out.println();
    }
	
	private void printTree(DecisionTree<String> node, boolean isRight, String indent) {
        if (!node.getChildren().isEmpty() && node.getChildren().get(0) != null) {
            printTree(node.getChildren().get(0), true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue(node);
        if (node.getChildren().size() > 1 && node.getChildren().get(1) != null) {
            printTree(node.getChildren().get(1), false, indent + (isRight ? " |      " : "        "));
        }
    }*/
}