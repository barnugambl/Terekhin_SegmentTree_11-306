package SegmentTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        RandomGenerations randomGenerations = new RandomGenerations();
        int [] randomArray = randomGenerations.randomArray();
        SegmentTree segmentTree = new SegmentTree(randomArray);
        System.out.println(Arrays.toString(segmentTree.segmentTree)); // дерево в виде массива
        System.out.println(countIterationSum(segmentTree)); // лист суммы итераций
        System.out.println(countIterationAdd(randomArray)); // лист итераций добавления
        System.out.println(countIterationRemove(segmentTree)); // лист итераций удаления
        System.out.println(timeAdd(randomArray)); // лист времени добавления
        System.out.println(timeRemove(segmentTree)); // лист времени удаления
        System.out.println(timeSum(segmentTree)); // лист времени суммирования
        System.out.println(getMiddle(timeAdd(randomArray)) + " Среднее время добавление");
        System.out.println(getMiddle(timeRemove(segmentTree)) + " Среднее время удаления");
        System.out.println(getMiddle(timeSum(segmentTree)) + " Среднее время суммирования");
        System.out.println(getMiddle(countIterationAdd(randomArray)) + " Среднее количество итераций добавления");
        System.out.println(getMiddle(countIterationRemove(segmentTree)) + " Среднее количество итераций удаления");
        System.out.println(getMiddle(countIterationSum(segmentTree)) + " Среднее количество итераций суммирования");
    }

    public static List<List<Long>> timeAdd(int[] randomArray) {
        List<List<Long>> addList = new ArrayList<>();
        SegmentTree segmentTree = new SegmentTree(randomArray.length);
        for (int i = 0; i < randomArray.length; i++) {
            long startTime = System.nanoTime();
            segmentTree.addElement(i, randomArray[i]);
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            List<Long> nestedList = new ArrayList<>(2);
            nestedList.add((long)i);
            nestedList.add(time);
            addList.add(nestedList);
        }
        return addList;
    }

    public static List<List<Long>> timeRemove(SegmentTree segmentTree) {
        List<List<Long>> listRemove = new ArrayList<>();
        RandomGenerations randomGenerations = new RandomGenerations();
        int[] randomArray = randomGenerations.randomValuesInSegmentTree(segmentTree, 1000);
        for (int i = 0; i < randomArray.length; i++) {
            long startTime = System.nanoTime();
            segmentTree.update(randomArray[i], 0);
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            List<Long> nestedList = new ArrayList<>(2);
            nestedList.add((long)segmentTree.getIndex(randomArray[i])); // значение
            nestedList.add(time); // время
            listRemove.add(nestedList);
        }
        return listRemove;
    }

    public static List<List<Long>> timeSum(SegmentTree segmentTree) {
        List<List<Long>> listSum = new ArrayList<>();
        RandomGenerations randomGenerations = new RandomGenerations();
        int[][] randomArraySelection = randomGenerations.randomSelectionInSegmentTree(segmentTree, 100);
        for (int i = 0; i < randomArraySelection.length; i++) {
            long startTime = System.nanoTime();
            segmentTree.query(randomArraySelection[i][0], randomArraySelection[i][1]);
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            List<Long> nestedList = new ArrayList<>(2);
            nestedList.add((long)(Math.abs(randomArraySelection[i][1]-randomArraySelection[i][0])));
            nestedList.add(time);
            listSum.add(nestedList);
        }


        return listSum;
    }


    public static List<List<Integer>> countIterationAdd(int[] randomArray) {
        List<List<Integer>> addList = new ArrayList<>();
        RandomGenerations randomGenerations = new RandomGenerations();
        randomArray = randomGenerations.randomArray();
        SegmentTree segmentTree = new SegmentTree(randomArray.length);
        for (int i = 0; i < randomArray.length; i++) {
            segmentTree.addElement(i, randomArray[i]);
            List<Integer> nestedList = new ArrayList<>(2);
            nestedList.add(segmentTree.getCountIterationAdd());
            nestedList.add(i);
            addList.add(nestedList);
        }
        return addList;
    }

    public static List<List<Integer>> countIterationRemove(SegmentTree segmentTree) {
        List<List<Integer>> removeList = new ArrayList<>();
        RandomGenerations randomGenerations = new RandomGenerations();
        int[] randomArray = randomGenerations.randomValuesInSegmentTree(segmentTree, 1000);
        for (int i = 0; i < randomArray.length; i++) {
            segmentTree.update(randomArray[i], 0);
            List<Integer> nestedList = new ArrayList<>(2);
            nestedList.add(segmentTree.getIndex(randomArray[i]));
            nestedList.add(segmentTree.getCountIterationRemove());
            removeList.add(nestedList);
        }
        return removeList;
    }

    public static List<List<Integer>> countIterationSum(SegmentTree segmentTree) {
        List<List<Integer>> sumList = new ArrayList<>();
        RandomGenerations randomGenerations = new RandomGenerations();
        int[][] randomArraySelection = randomGenerations.randomSelectionInSegmentTree(segmentTree, 100);
        for (int i = 0; i < randomArraySelection.length; i++) {
            segmentTree.query(randomArraySelection[i][0], randomArraySelection[i][1]);
            List<Integer> nestedList = new ArrayList<>(2);
            int lengthSegment = Math.abs(randomArraySelection[i][1] - randomArraySelection[i][0]);
            nestedList.add(lengthSegment);
            nestedList.add(segmentTree.getCountIterationSum());
            sumList.add(nestedList);
        }
        return sumList;
    }


    public static <V extends Number> double getMiddle(List<List<V>> list) {
        double sum = 0;
        for (List<V> nestedList : list) {
            if (!nestedList.isEmpty()) {
                sum += nestedList.get(1).doubleValue();
            }
        }
        return sum / list.size();
    }

    public static <V extends Number>void writeListToTxtFile(List<List<V>> list, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (List<V> nestedList : list) {
                for (V value : nestedList) {
                    writer.write(value + ",");
                }
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static <V extends  Number> List<List<V>> sortList(List<List<V>> list) {
        list.sort(Comparator.comparingLong(nestedList -> nestedList.get(0).longValue()));
        return list;
    }
}


