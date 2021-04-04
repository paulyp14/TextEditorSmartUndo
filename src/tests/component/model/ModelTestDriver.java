package tests.component.model;

import base.model.EditContainer;

public class ModelTestDriver {

    public static void main(String[] args) {
        testSeven();

    }

    public static void testSeven() {
        EditContainer container = new EditContainer();
        String[] asArr = {
                "The Toronto Maple Leafs are the best NHL team",
                " and it's not even close!",
                " Go Leafs Go",
                " have the best goal scorer and",
                ". It's time for them to win the Stanley Cup!",
                " Hockey all the way!!"
        };

        int size = 0;
        String s = "";
        for (int i = 0; i < asArr.length; i++) {
            s = asArr[i];
            container.create(s, size, true);
            size += s.length();
        }
        String reconstituted = container.asText();

        container.delete(1);
        container.delete(2);
        container.delete(4);
        container.delete(5);
        reconstituted = container.asText();
        System.out.println();

        System.out.println(container.stringRepr());
    }

    public static void testSix() {
        EditContainer container = new EditContainer();
        String[] asArr = {"The Toronto Maple Leafs are the best NHL team", " and it's not even close!", " Go Leafs Go", " have the best goal scorer and"};
        container.create(asArr[0], 0, true);
        container.create(asArr[2], 45, true);
        container.create(asArr[1], 45, true);
        container.create(asArr[3], 23, true);
        String reconstituted = container.asText();
        container.create(asArr[3], 23, false);
        reconstituted = container.asText();
        container.create("Leafs are", 18, false);
        reconstituted = container.asText();
        container.create("NHL team and it's not even close! Go ", 27,false);
        reconstituted = container.asText();
        System.out.println();
    }

    public static void testFive() {
        EditContainer container = new EditContainer();
        String[] asArr = {"The Toronto Maple Leafs are the best NHL team", " and it's not even close!", " Go Leafs Go", " have the best goal scorer and"};
        container.create(asArr[0], 0, true);
        container.create(asArr[2], 45, true);
        container.create(asArr[1], 45, true);
        container.create(asArr[3], 23, true);
        String reconstituted = container.asText();
        container.create(asArr[3], 23, false);
        reconstituted = container.asText();
        container.create("Leafs are", 18, false);
        reconstituted = container.asText();
        System.out.println();
    }

    public static void testFour() {
        EditContainer container = new EditContainer();
        String[] asArr = {"The Toronto Maple Leafs are the best NHL team", " and it's not even close!", " Go Leafs Go", " have the best goal scorer and"};
        container.create(asArr[0], 0, true);
        container.create(asArr[2], 45, true);
        container.create(asArr[1], 45, true);
        container.create(asArr[3], 23, true);
        String reconstituted = container.asText();
        container.create(asArr[3], 23, false);
        reconstituted = container.asText();
        System.out.println();
    }

    public static void testThree() {
        EditContainer container = new EditContainer();
        String[] asArr = {"The Toronto Maple Leafs are the best NHL team", " and it's not even close!", " Go Leafs Go", " have the best goal scorer and"};
        container.create(asArr[0], 0, true);
        container.create(asArr[2], 45, true);
        container.create(asArr[1], 45, true);
        container.create(asArr[3], 23, true);
        String reconstituted = container.asText();
        System.out.println();
    }

    public static void testTwo() {
        EditContainer container = new EditContainer();
        String[] asArr = {"The Toronto Maple Leafs are the best NHL team", " and it's not even close!", " Go Leafs Go"};
        container.create(asArr[0], 0, true);
        container.create(asArr[2], 45, true);
        container.create(asArr[1], 45, true);
        String reconstituted = container.asText();
        System.out.println();
    }

    public static void testOne() {
        EditContainer container = new EditContainer();

        String myString = "The Toronto Maple Leafs are the best NHL team and it's not even close! Go Leafs Go";
        String[] asArr = {"The Toronto Maple Leafs are the best NHL team", " and it's not even close!", " Go Leafs Go"};
        int size = 0;
        for(int i = 0; i < asArr.length; i++) {
            if (i != 0) {
                size += asArr[i - 1].length();
            }
            container.create(asArr[i], size, true);
        }

        String reconstituted = container.asText();
        System.out.println();
    }
}
