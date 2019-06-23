
class Coordinate {
    Integer x;
    Integer y;

    /**
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * @param x the x to set
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * @param set x and y
     */
    public void setCoordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public void clear() {
        this.x = null;
        this.y = null;
    }

    boolean isSet() {
        return x != null && y != null;
    }

}