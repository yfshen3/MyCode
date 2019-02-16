package org.lf.admin.api.baseapi.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    public static <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<>();

        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {

            }
        }
        return trees;
    }
}
