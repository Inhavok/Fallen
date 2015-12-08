package com.inhavok.fallen.components.entity_components.ai;

public final class BehaviourTree {
    private final BehaviourNode root;
    protected BehaviourTree(final BehaviourNode root) {
        this.root = root;
    }
    protected void execute() {
        root.execute();
    }
}