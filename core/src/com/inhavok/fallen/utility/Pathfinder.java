package com.inhavok.fallen.utility;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

public final class Pathfinder {
	private final Level level;
	private ArrayList<ArrayList<Node>> nodes;
	@SuppressWarnings("Since15")
	private final PriorityQueue<Node> openList = new PriorityQueue<Node>(new Comparator<Node>() {
		@Override
		public int compare(Node o1, Node o2) {
			if (o1.getF() < o2.getF()) {
				return -1;
			} else if (o1.getF() > o2.getF()) {
				return 1;
			}
			return 0;
		}
	});
	private final ArrayList<Node> closedList = new ArrayList<Node>();
	Pathfinder(final Level level) {
		 this.level = level;
	}
	public Stack<Vector2> getPath(final float startX, final float startY, final float endX, final float endY) {
		nodes = new ArrayList<ArrayList<Node>>();
		openList.clear();
		closedList.clear();
		convertTilesToNodes(level.physicsToTileX(endX), level.physicsToTileY(endY));
		final Node startNode = nodes.get(level.physicsToTileX(startX)).get(level.physicsToTileY(startY));
		final Node endNode = nodes.get(level.physicsToTileX(endX)).get(level.physicsToTileY(endY));
		openList.add(startNode);
		while (!openList.isEmpty()) {
			final Node currentNode = openList.poll();
			closedList.add(currentNode);
			if (currentNode == endNode) {
				final Stack<Vector2> path = new Stack<Vector2>();
				Node pathNode = currentNode;
				while (pathNode != null) {
					path.push(level.tileToPhysicsPosition(pathNode.x, pathNode.y));
					pathNode = pathNode.parent;
				}
				return path;
			}
			checkAdjacentNodes(currentNode);
		}
		throw new NullPointerException();
	}
	private void convertTilesToNodes(final int endX, final int endY) {
		final int[][] tiles = level.getTiles();
		for (int i = 0; i < level.getWidth(); i++) {
			final ArrayList<Node> verticalStrip = new ArrayList<Node>();
			for (int j = 0; j < level.getHeight(); j++) {
				if (tiles[i][j] == 0) {
					verticalStrip.add(null);
				} else {
					verticalStrip.add(new Node(i, j, calculateCost(i, j, endX, endY)));
				}
			}
			nodes.add(verticalStrip);
		}
	}
	private void checkAdjacentNodes(final Node parentNode) {
		rankAdjacentNode(parentNode, parentNode.x + 1, parentNode.y);
		rankAdjacentNode(parentNode, parentNode.x - 1, parentNode.y);
		rankAdjacentNode(parentNode, parentNode.x, parentNode.y + 1);
		rankAdjacentNode(parentNode, parentNode.x, parentNode.y - 1);
	}
	private void rankAdjacentNode(final Node parentNode, final int x, final int y) {
		try {
			final Node adjacentNode = nodes.get(x).get(y);
			if (adjacentNode != null && !closedList.contains(adjacentNode)) {
				final boolean onFrontier = openList.contains(adjacentNode);
				final double newG = parentNode.g + 1;
				if (!onFrontier || newG < adjacentNode.g) {
					adjacentNode.g = newG;
					adjacentNode.parent = parentNode;
				}
				if (onFrontier) {
					openList.remove(adjacentNode);
				}
				openList.add(adjacentNode);
			}
		} catch (final IndexOutOfBoundsException ignore) {
		}
	}
	private double calculateCost(final int nodeOneX, final int nodeOneY, final int nodeTwoX, final int nodeTwoY) {
		return Math.abs(nodeTwoX - nodeOneX) + 1.01 * Math.abs(nodeTwoY - nodeOneY);
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