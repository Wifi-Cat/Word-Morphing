package morph;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numWords = sc.nextInt();
		List<Node> nodeCollection = new LinkedList<Node>();
		for (int i = 0; i < numWords; i++) {
			nodeCollection.add(new Node(sc.next()));
		}
		// construct the graph
		for (Node node1 : nodeCollection) {
			for (Node node2 : nodeCollection) {
				if (isOneOff(node1.word, node2.word)) {
					node1.adjacent.add(node2);
				}
			}
		}
		// for each pair of words
		int numTransforms = sc.nextInt();
		for (int i = 0; i < numTransforms; i++) {
			String start = sc.next();
			String end = sc.next();
			System.out.println("Pair " + (i + 1) + ": " + start + " " + end);
			Node first = null;
			for (Node node : nodeCollection) {
				if (node.word.equals(start)) {
					first = node;
					break;
				}
			}
			Node last = null;
			for (Node node : nodeCollection) {
				if (node.word.equals(end)) {
					last = node;
					break;
				}
			}
			if (first == null || last == null) {
				System.out.println("NO SEQUENCE\n");
			} else if (first.adjacent.size() == 0 || last.adjacent.size() == 0) {
				System.out.println("NO SEQUENCE\n");
			} else {
				// we have starting and ending candidates
				boolean foundPath = false;
				List<Node> path = getPath(first, last);
				if (path.size() > 0) {
					printPath(path);
					foundPath = true;
				}
				if (!foundPath) {
					System.out.println("NO SEQUENCE\n");
				}
			}
		}
		System.out.println("END OF OUTPUT\n");
		sc.close();
		System.exit(0);
	}

	private static boolean isOneOff(String s1, String s2) {
		int stringLength = 5;
		int difference = 0;
		for (int i = 0; i < stringLength; i++) {
			if (s1.charAt(i) != s2.charAt(i))
				difference++;
		}
		return difference == 1;
	}

	private static List<Node> getPath(Node start, Node end) {
		List<Node> path = new LinkedList<Node>();
		List<Node> discovered = new LinkedList<Node>();
		Stack<Node> s = new Stack<Node>();
		s.push(start);
		while (!s.isEmpty()) {
			Node n = s.pop();
			if (!discovered.contains(n)) {
				discovered.add(n);
				path.add(n);
				if (n.equals(end)) {
					return path;
				}
				for (Node adj : n.adjacent) {
					if (!discovered.contains(adj)) {
						s.push(adj);
					}
				}
			}
		}
		if (!path.get(path.size() - 1).equals(end)
				|| !path.get(0).equals(start)) {
			path.clear();
		}
		return path;
	}

	private static void printPath(List<Node> path) {
		for (Node node : path) {
			System.out.println(node.word);
		}

	}
}

class Node {
	public String word;
	public List<Node> adjacent;

	public Node(String word) {
		this.word = word;
		adjacent = new LinkedList<Node>();
	}

	@Override
	public String toString() {
		return word;
	}
}
