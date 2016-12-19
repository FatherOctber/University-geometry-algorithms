package com.fatheroctber.algolab.lab2

import com.fatheroctber.algolab.common.AbstractPoint
import com.fatheroctber.algolab.common.CommonUtils

class GrahamHullScan {

    static <T extends AbstractPoint> T nextToTop(Stack<T> hull) {
        T p = hull.peek()
        hull.pop();
        T res = hull.peek()
        hull.push(p);
        return res;
    }

    //square of distance
    public static <T extends AbstractPoint> int distSq(T p1, T p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)
    }

    // To find orientation of ordered triplet (p, q, r)
    public static <T extends AbstractPoint> Orientation orientation(T p, T q, T r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
        if (val == 0) {
            return Orientation.COLINEAR  // colinear
        }
        return (val > 0) ? Orientation.CLOCK : Orientation.COUNTERCLOCK // clock or counterclock wise
    }

    public static class GrahamPointComparator<T extends AbstractPoint> implements Comparator<T> {
        private T originPoint;

        public GrahamPointComparator(T origin) {
            this.originPoint = origin
        }

        @Override
        int compare(T o1, T o2) {
            Orientation o = orientation(originPoint, o1, o2)
            if (o == Orientation.COLINEAR)
                return (distSq(originPoint, o2) >= distSq(originPoint, o1)) ? -1 : 1
            else
                return (o == Orientation.COUNTERCLOCK) ? -1 : 1
        }
    }

    public static <T extends AbstractPoint> List<T> grahamScan(List<T> pointsList) {
        if (pointsList.size() < 3) {
            throw new RuntimeException("Convex hull is not possible")
        }
        //copy source points
        List<T> points = new ArrayList<T>()
        points.addAll(pointsList)
        // Find the bottommost point
        int ymin = points[0].y
        int min = 0
        int length = points.size()
        for (int i = 1; i < length; i++) {
            int y = points[i].y
            if ((y < ymin) || (ymin == y && points[i].x < points[min].x)) {
                ymin = points[i].y
                min = i
            }
        }
        CommonUtils.swap(points[0], points[min])
        T origin = points[0]
        Collections.sort(points.subList(1, length), new GrahamPointComparator<T>(origin))

        int m = 1
        for (int i = 1; i < length; i++) {
            while (i < length - 1 && orientation(origin, points[i], points[i + 1]) == Orientation.COLINEAR) {
                i++
            }
            points[m] = points[i]
            m++
        }

        Stack<T> hull = new Stack<T>()
        for (int i = 0; i < 3; i++) {
            hull.push(points[i])
        }
        for (int i = 3; i < m; i++) {
            while (orientation(GrahamHullScan.nextToTop(hull), hull.peek(), points[i]) != Orientation.COUNTERCLOCK) {
                hull.pop()
            }
            hull.push(points[i])
        }

        List<T> result = new ArrayList<T>()
        for (T p : hull) {
            result.add(p)
        }
        return result
    }
}
