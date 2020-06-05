package main.components;

public class View {

    //View position on the map, top left is 0,0
    public double xPosition = 20;
    public double yPosition = 10;


    public double xRotation = -1;
    public double yRotation = 0;

    public double xPlane = 0;
    public double yPlane = 0.66;

    public boolean left = false, right = false, forward = false, backwards = false;

    private double rotationSpeed;
    private double movementSpeed;
    private double speedDamp = 30;

    public View() {
        this(3, 5);
    }

    public View(double xInit, double yInit) {
        xPosition = xInit;
        yPosition = yInit;
    }

    public void update(double frameTime) {
        rotationSpeed = frameTime / speedDamp;
        movementSpeed = (frameTime * 4) / speedDamp;

        if (left) {
            lookLeft();
        } else if (right) {
            lookRight();
        }

        if (forward) {
            moveForward();
        } else if (backwards) {
            moveBackwards();
        }

    }

    public void lookLeft() {
        rotate(rotationSpeed);
    }

    public void lookRight() {
        rotate(-rotationSpeed);
    }

    //moves "forwards" through the equation
    public void moveForward() {
        moveLinear(movementSpeed);
    }

    //moves "backwards" through the equation
    public void moveBackwards() {
        moveLinear(-movementSpeed);
    }

    //x and y rotation act as the "rise" and "run" of this linear equation
    public void moveLinear(double amount) {
        xPosition += xRotation * amount;
        yPosition += yRotation * amount;
    }

    //applies a rotation matrix to the x and y values for rotation and plane.
    //think of these values as points, points which dictate the direction
    //of the view. as regular points on a cartesian plane we can simply
    //multiply them with our matrix.
    public void rotate(double radians) {
        double oldDirX = xRotation;
        xRotation = xRotation * Math.cos(radians) - yRotation * Math.sin(radians);
        yRotation = oldDirX * Math.sin(radians) + yRotation * Math.cos(radians);
        double oldPlaneX = xPlane;
        xPlane = xPlane * Math.cos(radians) - yPlane * Math.sin(radians);
        yPlane = oldPlaneX * Math.sin(radians) + yPlane * Math.cos(radians);
    }



}

