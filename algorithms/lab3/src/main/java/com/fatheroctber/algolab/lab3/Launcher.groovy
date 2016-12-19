package com.fatheroctber.algolab.lab3

import com.fatheroctber.algolab.common.CommonUtils
import com.fatheroctber.algolab.common.PointG
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

class Launcher {

    public static void main(String[] args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Arguments mismatch")
        }
        Graph graph = new SingleGraph("Convex Hull Scan")
        graph.display()
        CommonUtils.standartSleep()

        def numbers = PointG.randomPointArray(args[0] as Integer)
        numbers.each { n ->
            CommonUtils.addPoint(graph, n)
            CommonUtils.smallSleep()
        }

        def hullScan = DivideAndConquerHullScan.divideAndConquerHullScan(numbers)
        CommonUtils.linkVertexes(graph, hullScan, {
            CommonUtils.smallSleep()
        });
    }
}
