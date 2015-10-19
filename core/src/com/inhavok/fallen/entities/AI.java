package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.states.Level;

import java.util.*;

final class AI {
	private static ArrayList<ArrayList<Node>> nodes;
	private static final LinkedList<Node> openList = new LinkedList<Node>();
	private static final ArrayList<Node> closedList = new ArrayList<Node>();
	private AI() {
	}
	public static Stack<Vector2> getPath(final float startX, final float startY, final Vector2 endPoint) {
		openList.clear();
		closedList.clear();
		createNodes(Level.physicsToTileX(endPoint.x), Level.physicsToTileY(endPoint.y), Level.getTiles());
		openList.add(nodes.get(Level.physicsToTileX(startX)).get(Level.physicsToTileY(startY)));
		while (!openList.isEmpty()) {
			Collections.sort(openList, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					if (o1.getF() < o2.getF()) {
						return 1;
					} else if (o1.getF() > o2.getF()) {
						return 2;
					}
					return 0;
				}
			});
			final Node nextNode = openList.getFirst();
			closedList.add(nextNode);
			calculateMovementCost(nextNode.x, nextNode.y, endPoint);
			if (nodes.get(Level.physicsToTileX(endPoint.x)).get(Level.physicsToTileY(endPoint.y)).parent != null) {
				final Stack<Vector2> path = new Stack<Vector2>();
				Node currentNode = nodes.get(Level.physicsToTileX(endPoint.x)).get(Level.physicsToTileY(endPoint.y));
				while (currentNode != null) {
					path.push(Level.tileToPhysicsPosition(currentNode.x, currentNode.y));
					currentNode = currentNode.parent;
				}
				return path;
			}
		}
		return null;
	}
	private static void createNodes(final int endTileX, final int endTileY, final int[][] tiles) {
		final ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();
		for (int i = 0; i < Level.getWidth(); i++) {
			final ArrayList<Node> verticalStrip = new ArrayList<Node>();
			for (int j = 0; j < Level.getHeight(); j++) {
				verticalStrip.add(new Node(i, j, tiles[i][j], Math.sqrt(Math.pow(endTileX - i, 2) + Math.pow(endTileY - j, 2))));
			}
			nodes.add(verticalStrip);
		}
		AI.nodes = nodes;
	}
	//G is calculated incorrectly, should be relative to the startNode not the parentNode
	private static void calculateMovementCost(final int parentX, final int parentY, final Vector2 endPoint) {
		final Node parentNode = nodes.get(parentX).get(parentY);
		for (int i = parentX - 1; i <= parentX + 1; i++) {
			for (int j = parentY - 1; j <= parentY + 1; j++) {
				if (endPoint.x == i && endPoint.y == j) {
					nodes.get(i).get(j).parent = parentNode;
					return;
				}
				if (!(closedList.contains(nodes.get(i).get(j)) && nodes.get(i).get(j).type == 0)) {
					if (openList.contains(nodes.get(i).get(j))) {
						if (parentNode.g + Math.sqrt(Math.pow(parentX - i, 2) + Math.pow(parentY - j, 2)) < nodes.get(i).get(j).g) {
							nodes.get(i).get(j).parent = parentNode;
						}
					} else {
						nodes.get(i).get(j).g = parentNode.g + Math.sqrt(Math.pow(parentX - i, 2) + Math.pow(parentY - j, 2));
						nodes.get(i).get(j).parent = parentNode;
						openList.add(nodes.get(i).get(j));
					}
				}
			}
		}
	}
	private static final class Node {
		private final int x;
		private final int y;
		private final int type;
		private double g;
		private final double h;
		private Node parent;
		private Node(final int x, final int y, final int type, final double h) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.h = h;
		}
		public double getF() {
			return g + h;
		}
	}
}