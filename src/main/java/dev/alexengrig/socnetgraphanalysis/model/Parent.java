package dev.alexengrig.socnetgraphanalysis.model;

import java.util.List;

public class Parent extends Node {

    private final List<Node> children;

    public Parent(String name, List<Node> children) {
        super(name);
        this.children = children;
    }

    public List<Node> getChildren() {
        return children;
    }
}
