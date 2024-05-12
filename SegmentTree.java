package SegmentTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegmentTree {
    int[] segmentTree;
    int[] arr;
    int size;
    private int countIterationAdd;

    private int countIterationSum;
    private int countIterationRemove;

    public SegmentTree(int[] arr) {
        this.arr = arr;
        this.size = arr.length;
        int height = (int) Math.ceil(Math.log(size) / Math.log(2));
        int treeSize = 2 * (int) Math.pow(2, height) - 1;
        segmentTree = new int[treeSize];
        buildSegmentTree(0, 0, size - 1);
    }

    public SegmentTree(int size) {
        this.size = size;
        int height = (int) Math.ceil(Math.log(size) / Math.log(2));
        int treeSize = 2 * (int) Math.pow(2, height) - 1;
        segmentTree = new int[treeSize];
    }

    private void buildSegmentTree(int index, int start, int end) {
        if (start == end) {
            segmentTree[index] = arr[start];
            return;
        }
        int mid = start + (end - start) / 2;
        buildSegmentTree(2 * index + 1, start, mid);
        buildSegmentTree(2 * index + 2, mid + 1, end);
        segmentTree[index] = segmentTree[2 * index + 1] + segmentTree[2 * index + 2];
    }

    public int query(int queryStart, int queryEnd) {
        countIterationSum = 0;
        return queryHelper(0, 0, size - 1, queryStart, queryEnd);
    }

    private int queryHelper(int index, int start, int end, int queryStart, int queryEnd) {
        countIterationSum++;
        if (queryStart <= start && queryEnd >= end) {
            return segmentTree[index];
        }
        if (queryStart > end || queryEnd < start) {
            return 0;
        }
        int mid = start + (end - start) / 2;
        return queryHelper(2 * index + 1, start, mid, queryStart, queryEnd)
                + queryHelper(2 * index + 2, mid + 1, end, queryStart, queryEnd);
    }

    public void addElement(int pos, int val) {
        countIterationAdd = 0;
        addElementHelper(0, 0, size - 1, pos, val);

    }

    private void addElementHelper(int index, int start, int end, int pos, int val) {
        countIterationAdd++;
        if (start == end) {
            segmentTree[index] += val;
            return;
        }
        int mid = start + (end - start) / 2;
        if (pos <= mid) {
            addElementHelper(2 * index + 1, start, mid, pos, val);
        } else {
            addElementHelper(2 * index + 2, mid + 1, end, pos, val);
        }
        segmentTree[index] = segmentTree[2 * index + 1] + segmentTree[2 * index + 2];
    }

    public void update(int pos, int val) {
        countIterationRemove = 0;
        updateUtil(0, 0, size - 1, pos, val);

    }

    private void updateUtil(int idx, int left, int right, int pos, int val) {
        countIterationRemove++;
        if (left == right) {
            segmentTree[idx] = val;
        } else {
            int mid = left + (right - left) / 2;
            if (pos <= mid) {
                updateUtil(2 * idx + 1, left, mid, pos, val);
            } else {
                updateUtil(2 * idx + 2, mid + 1, right, pos, val);
            }
            segmentTree[idx] = segmentTree[2 * idx + 1] + segmentTree[2 * idx + 2];
        }

    }

    public int getCountIterationAdd() {
        return countIterationAdd;
    }

    public int getCountIterationSum() {
        return countIterationSum;
    }

    public int getCountIterationRemove() {
        return countIterationRemove;
    }

    public int getIndex(int number){
        for (int i = 0; i < segmentTree.length; i++) {
            if(segmentTree[i] == number) {
                number = i;
            }
        }
        return number;
    }
}




