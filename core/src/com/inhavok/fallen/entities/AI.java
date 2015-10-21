package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.states.Level;

import java.util.*;

final class AI {
	private static ArrayList<ArrayList<Node>> nodes;
	private static final PriorityQueue<Node> openList = new PriorityQueue<Node>(new Comparator<Node>() {
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
	private static final ArrayList<Node> closedList = new ArrayList<Node>();
	private AI() {
	}
	public static Stack<Vector2> getPath(final float startX, final float startY, final float endX, final float endY) {
		nodes = new ArrayList<ArrayList<Node>>();
		openList.clear();
		closedList.clear();
		convertTilesToNodes(Level.physicsToTileX(endX), Level.physicsToTileY(endY));
		final Node startNode = nodes.get(Level.physicsToTileX(startX)).get(Level.physicsToTileY(startY));
		final Node endNode = nodes.get(Level.physicsToTileX(endX)).get(Level.physicsToTileY(endY));
		openList.add(startNode);
		while (!openList.isEmpty()) {
			final Node currentNode = openList.poll();
			if (currentNode == endNode) {
				final Stack<Vector2> path = new Stack<Vector2>();
				Node pathNode = currentNode;
				while (pathNode != null) {
					path.push(Level.tileToPhysicsPosition(pathNode.x, pathNode.y));
					pathNode = pathNode.parent;
				}
				return path;
			}
			checkAdjacentNodes(currentNode);
		}
		throw new NullPointerException();
	}
	private static void convertTilesToNodes(final int endX, final int endY) {
		final int[][] tiles = Level.getTiles();
		for (int i = 0; i < Level.getWidth(); i++) {
			final ArrayList<Node> verticalStrip = new ArrayList<Node>();
			for (int j = 0; j < Level.getHeight(); j++) {
				if (tiles[i][j] == 0) {
					verticalStrip.add(null);
				} else {
					verticalStrip.add(new Node(i, j, calculateCost(i, j, endX, endY)));
				}
			}
			nodes.add(verticalStrip);
		}
	}
	private static void checkAdjacentNodes(final Node parentNode) {
		closedList.add(parentNode);
		for (int i = Math.max(parentNode.x - 1, 0); i <= Math.min(parentNode.x + 1, Level.getWidth()); i++) {
			for (int j = Math.max(parentNode.y - 1, 0); j <= Math.min(parentNode.y + 1, Level.getHeight()); j++) {
				final Node currentNode = nodes.get(i).get(j);
				if (currentNode != null && !closedList.contains(currentNode)) {
					if (openList.contains(currentNode)) {
						if (parentNode.g + calculateCost(parentNode.x, parentNode.y, currentNode.x, currentNode.y) < currentNode.g) {
							currentNode.g = parentNode.g + calculateCost(parentNode.x, parentNode.y, currentNode.x, currentNode.y);
							currentNode.parent = parentNode;
						}
					} else {
						currentNode.g = parentNode.g + calculateCost(parentNode.x, parentNode.y, currentNode.x, currentNode.y);
						currentNode.parent = parentNode;
						openList.add(currentNode);
					}
				}
			}
		}
	}
	private static double calculateCost(final int nodeOneX, final int nodeOneY, final int nodeTwoX, final int nodeTwoY) {
		final int nonDiagonalCost = 10;
		final int diagonalCost = 14;
		final int dX = Math.abs(nodeTwoX - nodeOneX);
		final int dY = Math.abs(nodeTwoY - nodeOneY);
		return nonDiagonalCost * (dX + dY) + (diagonalCost - 2 * nonDiagonalCost) * Math.min(dX, dY);
	}
	private static final class Node {
		private final int x;
		private final int y;
		private double g;
		private final double h;
		private Node parent;
		private Node(final int x, final int y, final double h) {
			this.x = x;
			this.y = y;
			this.h = h;
		}
		private double getF() {
			return g + h;
		}
	}
}