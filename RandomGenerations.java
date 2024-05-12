package SegmentTree;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerations {
    public int[] randomArray(){
        Random random = new Random();
        int [] temp = new int[10000];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = random.nextInt(500);
        }
        return temp;
    }
    public int[] randomValuesInSegmentTree(SegmentTree segmentTree, int cntRandomValues){
        int min = 0;
        int max = segmentTree.segmentTree.length;
        int [] temp = new int[cntRandomValues];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = segmentTree.segmentTree[ThreadLocalRandom.current().nextInt(min,max)];
        }
        return temp;
    }

    public int[][] randomSelectionInSegmentTree(SegmentTree segmentTree, int cntRandomValues){
        int min = 0;
        int max = segmentTree.segmentTree.length;
        int [][] randomSectionInSegmentTree = new int[cntRandomValues][2];
        for (int i = 0; i < randomSectionInSegmentTree.length; i++) {
            int start = ThreadLocalRandom.current().nextInt(min,max);
            int end = ThreadLocalRandom.current().nextInt(min,max);
            if (end < start) {
                randomSectionInSegmentTree[i][0] = end;
                randomSectionInSegmentTree[i][1] = start;
            }
            else {
                randomSectionInSegmentTree[i][0] = start;
                randomSectionInSegmentTree[i][1] = end;
            }
        }
        return randomSectionInSegmentTree;
    }

}
