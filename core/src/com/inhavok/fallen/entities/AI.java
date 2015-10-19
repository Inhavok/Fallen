package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.states.Level;

import java.util.*;

final class AI {
	private static ArrayList<ArrayList<Node>> nodes;
	private static final ArrayList<Node> evaluatedNodes = new ArrayList<Node>();
	private static final LinkedList<Node> frontier = new LinkedList<Node>();
	private AI() {
	}
	public static LinkedList<Vector2> getPath(final float startX, final float startY, final Vector2 endPoint) {
		evaluatedNodes.clear();
		frontier.clear();
		convertTilesToNodes();
		final Node startNode = nodes.get(Level.physicsToTileX(startX)).get(Level.physicsToTileY(startY));
		evaluatedNodes.add(startNode);
		frontier.add(startNode);
		//Appears to be fine up until this point
		while (!frontier.isEmpty()) {
			final Node currentNode = frontier.pop();
			if (currentNode == nodes.get(Level.physicsToTileX(endPoint.x)).get(Level.physicsToTileY(endPoint.y))) {
				final LinkedList<Vector2> path = new LinkedList<Vector2>();
				Node pathNode = currentNode;
				while (pathNode != null) {
					path.push(Level.tileToPhysicsPosition(pathNode.x, pathNode.y));
					pathNode = pathNode.parent;
				}
				return path;
			}
			checkAdjacentNodes(currentNode);
		}
		return null;
	}
	//Appears to be fine
	private static void convertTilesToNodes() {
		final int[][] tiles = Level.getTiles();
		nodes = new ArrayList<ArrayList<Node>>();
		for (int i = 0; i < Level.getWidth(); i++) {
			final ArrayList<Node> verticalStrip = new ArrayList<Node>();
			for (int j = 0; j < Level.getHeight(); j++) {
				verticalStrip.add(new Node(i, j, tiles[i][j]));
			}
			nodes.add(verticalStrip);
		}
	}
	//Appears to be fine
	private static void checkAdjacentNodes(final Node centerNode) {
		for (int i = Math.max(centerNode.x - 1, 0); i <= Math.min(centerNode.x + 1, Level.getWidth()); i++) {
			for (int j = Math.max(centerNode.y - 1, 0); j <= Math.min(centerNode.y + 1, Level.getHeight()); j++) {
				if (nodes.get(i).get(j).type == 1 && !evaluatedNodes.contains(nodes.get(i).get(j))) {
					evaluatedNodes.add(nodes.get(i).get(j));
					nodes.get(i).get(j).parent = centerNode;
					frontier.add(nodes.get(i).get(j));
				}
			}
		}
	}
	private static final class Node {
		private final int x;
		private final int y;
		private final int type;
		private Node parent;
		private Node(final int x, final int y, final int type) {
			this.x = x;
			this.y = y;
			this.type = type;
		}
	}
}