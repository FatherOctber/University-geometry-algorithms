package com.fatheroctber.algolab.common

public class PointG extends AbstractPoint<Integer> {

    public PointG(Integer x, Integer y) {
       super(x,y)
    }

    @Override
    String toString() {
        return "" + this.x + " " + this.y
    }

    public static def randomPointArray(int maxCount) {
        Random rand = new Random()
        int max = 1000
        int min = 0
        def randomIntegerList = []
        (1..maxCount).each {
            randomIntegerList << new PointG(rand.nextInt(max - min) + min, rand.nextInt(max - min) + min)
        }
        return randomIntegerList
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false
        } else {
            if (other instanceof PointG) {
                PointG that = (PointG) other;
                return this.x == that.x && this.y == that.y;
            } else {
                return false
            }
        }
    }
}
