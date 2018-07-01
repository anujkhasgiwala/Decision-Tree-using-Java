package com.usu.decisionTree.impurity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entropy calculator. -p log2 p - (1 - p)log2(1 - p) - this is the expected information, in bits, conveyed by somebody
 * telling you the class of a randomly drawn example; the purer the set of examples, the more predictable this message
 * becomes and the smaller the expected information.
 * 
 * @author Anuj Khasgiwala
 *
 */
public class EntropyCalculationMethod {

	public double calculateImpurity(List<String> splitData) {
		List<String> labels = splitData.parallelStream().map(data -> data).distinct().collect(Collectors.toList());
		if (labels.size() > 1) {
			double p = getEmpiricalProbability(splitData, labels.get(0), labels.get(1));
			return -1.0 * p * (Math.log(p)/Math.log(2)) - ((1.0 - p) * (Math.log(1.0 - p)/Math.log(2)));
		} else if (labels.size() == 1) {
			return 0.0; // if only one label data is pure
		} else {
			throw new IllegalStateException("This should never happen. Probably a bug.");
		}
	}

	double getEmpiricalProbability(List<String> splitData, String positive, String negative) {
		return (double)splitData.parallelStream().filter(d -> d.equals(positive)).count() / splitData.size();
	}

	public double calculateInformationGain(List<String> splitData) {
		List<String> labels = splitData.parallelStream().map(data -> data).distinct().collect(Collectors.toList());
		List<Double> entropyList = new ArrayList<>();
		if (labels.size() > 1) {
			for(int i = 0; i < labels.size() - 1; i++) {
				double p = getEmpiricalProbability(splitData, labels.get(i), labels.get(i+1));
				entropyList.add(-1.0 * p * (Math.log(p)/Math.log(2)) - ((1.0 - p) * (Math.log(1.0 - p)/Math.log(2))));
			}
			return calculateImpurity(splitData) - entropyList.stream().mapToDouble(data -> data).average().getAsDouble();
		}
		return 0.0;
	}
}