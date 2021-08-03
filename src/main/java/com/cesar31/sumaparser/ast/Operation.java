package com.cesar31.sumaparser.ast;

/**
 *
 * @author cesar31
 */
public class Operation {

    private String type;
    private Integer value;
    private Operation left;
    private Operation right;

    public Operation(String type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public Operation(String type, Operation left, Operation right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    public Operation getLeft() {
        return left;
    }

    public Operation getRight() {
        return right;
    }

    public Integer run() {
        switch (type) {
            case "NUM":
                return this.value;
            case "+":
                return this.left.run() + this.right.run();
            case "*":
                return this.left.run() * this.right.run();
        }

        return null;
    }
}
