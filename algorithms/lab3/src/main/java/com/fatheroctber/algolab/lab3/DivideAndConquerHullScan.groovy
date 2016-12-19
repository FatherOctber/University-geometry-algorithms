package com.fatheroctber.algolab.lab3

import com.fatheroctber.algolab.common.AbstractPoint
import com.fatheroctber.algolab.common.CommonUtils

import static com.fatheroctber.algolab.common.CommonUtils.Looper.*


class DivideAndConquerHullScan {

    private static class DivideAndConquerPointComparator<T extends AbstractPoint> implements Comparator<T> {
        @Override
        int compare(T o1, T o2) {
            if (o1.x == o2.x)
                return (o1.y <= o2.y) ? -1 : 1
            else
                return (o1.x < o2.x) ? -1 : 1
        }
    }

    private static <T extends AbstractPoint> Map minAndMax(List<T> points) {
        def minMax = [min: 0, max: 0]
        for (i in 1..points.size()-1) {
            if (points[i].x < points[minMax.min].x) {
                minMax.min = i
            } else {
                if (points[i].x > points[minMax.max].x) {
                    minMax.max = i
                }
            }
        }
        return minMax
    }

    private static <T extends AbstractPoint> List<T> merge(List<T> s1, List<T> s2) {
        def extr1 = minAndMax(s1)
        def left1 = extr1.min
        def right1 = extr1.max
        def extr2 = minAndMax(s2)
        def left2 = extr2.min
        def right2 = extr2.max

        def a1 = right1
        def b1 = left2
        def next1 = (a1 + 1) % s1.size()
        def next2 = b1 - 1
        if (next2 < 0) {
            next2 = s2.size() - 1
        }
        loop {
            while (getTurn(s2[b1], s1[a1], s1[next1]) > 0) {
                a1 = next1
                next1 = (a1 + 1) % s1.size()
            }
            while (getTurn(s1[a1], s2[b1], s2[next2]) < 0) {
                b1 = next2
                next2 = b1 - 1
                if (next2 < 0) {
                    next2 = s2.size() - 1
                }
            }
        } until { !(getTurn(s2[b1], s1[a1], s1[next1]) > 0) }

        // find upper tangent
        def a2 = right1
        def b2 = left2
        next1 = a2 - 1
        if (next1 < 0) {
            next1 = s1.size() - 1
        }
        next2 = (b2 + 1) % s2.size()
        loop {
            while (getTurn(s2[b2], s1[a2], s1[next1]) < 0) {
                a2 = next1
                next1 = a2 - 1
                if (next1 < 0) {
                    next1 = s1.size() - 1
                }
            }
            while (getTurn(s1[a2], s2[b2], s2[next2]) > 0) {
                b2 = next2
                next2 = (b2 + 1) % s2.size()
            }
        } until { !(getTurn(s2[b2], s1[a2], s1[next1]) < 0) }

        // create convex hull from s1 and s2
        List<T> unionConvexHull = new ArrayList<T>()
        for (def i = a1; i != a2; i = ((i + 1) % s1.size())) {
            unionConvexHull.add(s1[i])
        }
        unionConvexHull.add(s1[a2])
        for (def i = b2; i != b1; i = (i + 1) % s2.size()) {
            unionConvexHull.add(s2[i])
        }
        unionConvexHull.add(s2[b1])

        return unionConvexHull
    }

    private static <T extends AbstractPoint> int getTurn(T p0, T p1, T p2) {
        long delta = (p1.x - p0.x) * (p2.y - p0.y) - (p1.y - p0.y) * (p2.x - p0.x)
        return delta > 0 ? 1 : (delta == 0 ? 0 : -1)
    }

    public static <T extends AbstractPoint> List<T> divideAndConquerHullScan(List<T> pointsList) {
        List<T> points = new ArrayList<T>()
        points.addAll(pointsList)
        Collections.sort(points, new DivideAndConquerPointComparator<T>())
        def convexHull = divideAndConquerBaseAlgorithm(points);
        if (convexHull.size() < 3) {
            throw RuntimeException("Size of list must be greater than 3")
        }
        return convexHull;
    }

    private static <T extends AbstractPoint> List<T> divideAndConquerBaseAlgorithm(List<T> points) {
        if (points.size() <= 3) {
            if (points.size() == 3) {
                // make cw order
                if (getTurn(points[1], points[0], points[2]) < 0) {
                    CommonUtils.swap(points[1], points[2])
                }
            }
            return points
        }
        int middle = points.size() / 2
        List<T> s1 = new ArrayList<T>(points.subList(0, middle))
        List<T> s2 = new ArrayList<T>(points.subList(middle, points.size()))
        s1 = divideAndConquerBaseAlgorithm(s1)
        s2 = divideAndConquerBaseAlgorithm(s2)
        return merge(s1, s2)
    }
}
