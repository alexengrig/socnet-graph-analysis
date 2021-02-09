package dev.alexengrig.socnetgraphanalysis.vk;

import java.util.Comparator;

public class DeepTaskComparator implements Comparator<Runnable> {

    @Override
    public int compare(Runnable left, Runnable right) {
        if (left instanceof DeepTask && right instanceof DeepTask) {
            DeepTask leftTask = (DeepTask) left;
            DeepTask rightTask = (DeepTask) right;
            return leftTask.getDepth() - rightTask.getDepth();
        }
        return 0;
    }
}
