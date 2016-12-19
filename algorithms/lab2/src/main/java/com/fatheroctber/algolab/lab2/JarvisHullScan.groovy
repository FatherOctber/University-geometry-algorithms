package com.fatheroctber.algolab.lab2

import com.fatheroctber.algolab.common.AbstractPoint
import static com.fatheroctber.algolab.common.CommonUtils.Looper.*


class JarvisHullScan {

    private static <T extends AbstractPoint> int orientTriangle(T p1, T p2, T p3) {
        return p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y)
    }

    private static <T extends AbstractPoint> boolean isInside(T p1, T p, T p2) {
        return ((p1.x <= p.x) && (p.x <= p2.x) && (p1.y <= p.y) && (p.y <= p2.y));
    }


    public static <T extends AbstractPoint> List<T> jarvisScan(List<T> pointsList) {
        if (pointsList.size() < 3) {
            throw new RuntimeException("Convex hull is not possible")
        }
        //copy source points
        List<T> points = new ArrayList<T>()
        points.addAll(pointsList)
        int basePoint = 0
        for (int i = 1; i < points.size(); ++i) {
            if (points[i].y < points[basePoint].y) {
                basePoint = i
            } else if ((points[i].y == points[basePoint].y) && (points[i].x < points[basePoint].x)) {
                basePoint = i
            }
        }
        List<T> convexHull = new ArrayList<T>()
        convexHull.add(points[basePoint])
        int firstPoint = basePoint
        int currentPoint = basePoint
        loop {
            int nextPoint = (currentPoint + 1) % points.size()
            for (int i = 0; i < points.size(); ++i) {
                int sign = orientTriangle(points[currentPoint], points[nextPoint], points[i])
                if (sign < 0) {
                    nextPoint = i
                } else if (sign == 0) {
                    if (isInside(points[currentPoint], points[nextPoint], points[i])) {
                        nextPoint = i
                    }
                }
            }
            currentPoint = nextPoint
            convexHull.add(points[nextPoint])
        } until { !(currentPoint != firstPoint) }

        return convexHull
    }
}
