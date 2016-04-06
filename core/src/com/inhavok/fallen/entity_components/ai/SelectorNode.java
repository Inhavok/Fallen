package com.inhavok.fallen.entity_components.ai;

public abstract class SelectorNode extends BehaviourNode {
    private final Condition condition;
    private final BehaviourNode trueNode;
    private final BehaviourNode falseNode;
    protected SelectorNode(final Condition condition, final BehaviourNode trueNode, final BehaviourNode falseNode) {
        this.condition = condition;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }
    @Override
    protected void execute() {
        if (condition.met()) {
            if (trueNode != null) {
                trueNode.execute();
            }
        } else if (falseNode != null) {
            falseNode.execute();
        }
    }
    public abstract class Condition {
        public abstract boolean met();
    }
}