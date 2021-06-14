package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Parent extends Node {

    private final List<Node> children;

    public Parent(String name, String detail, List<Node> children) {
        super(name, detail);
        this.children = children;
    }
}
