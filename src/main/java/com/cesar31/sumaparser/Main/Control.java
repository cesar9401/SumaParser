package com.cesar31.sumaparser.Main;

import com.cesar31.sumaparser.ast.Operation;
import com.cesar31.sumaparser.parser.SumaLex;
import com.cesar31.sumaparser.parser.SumaParser;
import com.cesar31.sumaparser.view.MainView;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class Control {

    public Control() {
        initView();
    }

    private void initView() {
        MainView view = new MainView(this);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public Integer parseInput(String input) {
        SumaLex lex = new SumaLex(new StringReader(input));
        SumaParser parser = new SumaParser(lex);
        Operation ast;
        try {
            ast = (Operation) parser.parse().value;
            getDot(ast);
            return ast.run();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }

    /**
     * obtener string con instrucciones para generar pgn del arbol
     *
     * @param node
     */
    public static void getDot(Operation node) {
        if (node != null) {
            String dot = "digraph ast {\n";
            dot += "label = \"" + postOrden(node) + "\";\n";
            dot += dot(node);
            dot += "}\n";
            generateGraph(dot);
        }
    }

    /**
     * Informaciond de nodos para instruccion dot
     *
     * @param node
     * @return
     */
    public static String dot(Operation node) {
        if (node != null) {
            // Label
            String label = node.getType().equals("NUM") ? String.valueOf(node.getValue()) : node.getType();
            String dot = "node" + node.hashCode() + "[ label = \"" + label + "\"];\n";
            dot += node.getLeft() != null ? "node" + node.hashCode() + " -> node" + node.getLeft().hashCode() + ";\n" : "";
            dot += node.getRight() != null ? "node" + node.hashCode() + " -> node" + node.getRight().hashCode() + ";\n" : "";

            // Hijos
            dot += dot(node.getLeft());
            dot += dot(node.getRight());
            return dot;
        }

        return "";
    }

    /**
     * Recorrido postOrden
     *
     * @param node
     * @return
     */
    public static String postOrden(Operation node) {
        if (node != null) {
            String label = "";

            // Left
            label += postOrden(node.getLeft());
            // Right
            label += postOrden(node.getRight());

            // visit here
            String value = node.getType().equals("NUM") ? String.valueOf(node.getValue()) : node.getType();
            label += " " + value + " ";
            return label;
        }
        return "";
    }

    /**
     * Generar imagen de arbol
     *
     * @param dot
     */
    public static void generateGraph(String dot) {
        try {
            MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
            // Graphviz.fromString(dot).width(600).render(Format.PNG).toFile(new File("tree2.png"));
            Graphviz.fromGraph(g).width(600).render(Format.PNG).toFile(new File("tree.png"));
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
