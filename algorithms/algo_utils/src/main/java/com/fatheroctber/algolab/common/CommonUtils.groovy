package com.fatheroctber.algolab.common

import org.graphstream.graph.Graph
import org.graphstream.graph.Node

public class CommonUtils {

    public static void standartSleep() {
        Thread.sleep(2000)
    }

    public static void smallSleep() {
        Thread.sleep(100)
    }

    public static <T extends AbstractPoint> Graph addPoint(Graph gr, T point) {
        Node node = gr.addNode(point.toString())
        node.addAttribute("layout.frozen")
        node.addAttribute("xy", point.x, point.y)
        node.setAttribute("ui.style", "fill-color: rgb(200,10,70);")
        return gr
    }

    public static <T extends AbstractPoint> void swap(T p1, T p2) {
        def temp = [x: p1.x, y: p1.y]
        p1.x = p2.x
        p1.y = p2.y

        p2.x = temp.x
        p2.y = temp.y
    }

    public static <T extends AbstractPoint> void linkVertexes(Graph gr, List<T> vertexes, Closure eachVertexClosure) {
        T previous = null
        for (T vertex : vertexes) {
            if (previous != null) {
                gr.addEdge(previous.toString() + vertex.toString(), previous.toString(), vertex.toString())
                previous = vertex
            }
            previous = vertex
            if (eachVertexClosure != null) {
                eachVertexClosure()
            }
        }
        //link with origin
        gr.addEdge(previous.toString() + vertexes[0].toString(), previous.toString(), vertexes[0].toString())
    }

    static class Looper {
        private Closure code

        static Looper loop( Closure code ) {
            new Looper(code:code)
        }

        void until( Closure test ) {
            code()
            while (!test()) {
                code()
            }
        }
    }
}
