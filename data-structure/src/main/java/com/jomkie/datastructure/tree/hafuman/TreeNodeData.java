package com.jomkie.datastructure.tree.hafuman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TreeNodeData<T> implements Comparable<TreeNodeData> {

    /** 左节点 */
    private TreeNodeData<T> left;
    /** 右节点 */
    private TreeNodeData<T> right;


    /**
     * 计算二叉树位置的方法，应该被重写
     * @return 节点的所有操作都与这个值有关
     */
    public abstract int getCode();

    /**
     * 打印节点
     */
    public abstract void printLog();


    @Override
    public int compareTo(TreeNodeData o) {
        return this.getCode() - o.getCode();
    }

    /**
     * 在当前节点为根，根据 code 查找结点
     * @param code
     * @return 查找到的结点
     */
    public TreeNodeData<T> search(int code) {
        System.out.println("搜索" + code);
        if (code == getCode()) {
            return this;
        } else if (code < getCode()) {
            if (left == null) { return null; }
            return left.search(code);
        } else {
            if (right == null) { return null; }
            return right.search(code);
        }
    }

    /**
     * 本方法只能由根开始
     * 从本节点作为一棵树，查找某个节点的父节点
     * @return 查找父节点的对象
     */
    public TreeNodeData<T> searchParent(int code) {
        System.out.println("搜索" + code + "的父级");
        if (code < getCode()) {
            if (left != null) {
                return left.getCode() == code ? this : left.searchParent(code);
            } else {
                return null;
            }
        } else if (code > getCode()) {
            if (right != null) {
                return right.getCode() == code ? this : right.searchParent(code);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public TreeNodeData<T> findMaxNode() {
        TreeNodeData<T> target = this;
        while (target.getRight() != null) {
            target = right;
        }
        return target;
    }
    public TreeNodeData<T> findMinNode() {
        TreeNodeData<T> target = this;
        while (target.getLeft() != null) {
            target = left;
        }
        return target;
    }

    public TreeNodeData<T> findMinOfRight() {
        TreeNodeData<T> target = right.findMinNode();
        return target == null ? this : target;
    }

    public TreeNodeData<T> findMaxOfLeft() {
        TreeNodeData<T> target = left.findMaxNode();
        return target == null ? this : target;
    }

    /**
     * 添加节点
     * @param node 节点
     */
    public void addNode(TreeNodeData<T> node) {
        if (node == null) {
            throw new RuntimeException("不能放入空节点");
        }

        if (node.getCode() < getCode()) {
            if (left == null) {
                left = node;
            } else {
                left.addNode(node);
            }
        } else if (node.getCode() > getCode()) {
            if (right == null) {
                right = node;
            } else {
                right.addNode(node);
            }
        } else {
            throw new RuntimeException("数据位置计算不能重复：" + node.getCode());
        }

    }

    /**
     * 先序遍历
     */
    public void xianxubianli() {
        this.printLog();
        if (getLeft() != null) {
            getLeft().xianxubianli();
        }
        if (getRight() != null) {
            getRight().xianxubianli();
        }
    }

    /**
     * 后序遍历
     */
    public void houxubianli() {
        if (getLeft() != null) {
            getLeft().houxubianli();
        }
        if (getRight() != null) {
            getRight().houxubianli();
        }
        this.printLog();
    }

    /**
     * 中序遍历
     */
    public void zhongxubianli() {
        if (getLeft() != null) {
            getLeft().zhongxubianli();
        }
        this.printLog();
        if (getRight() != null) {
            getRight().zhongxubianli();
        }
    }

    /**
     * 层序遍历
     */
    public void cengxubianli() {
        LinkedList<TreeNodeData<T>> linkedList = new LinkedList<>();
        linkedList.addLast(this);
        cengxubianliTool(linkedList);
    }
    private void cengxubianliTool(LinkedList<TreeNodeData<T>> linkedList) {
        TreeNodeData<T> treeNode;
        while ((treeNode = linkedList.poll()) != null) {
            treeNode.printLog();
            if (treeNode.getLeft() != null) {
                linkedList.addLast(treeNode.getLeft());
            }
            if (treeNode.getRight() != null) {
                linkedList.addLast(treeNode.getRight());
            }
        }
    }



    /**
     * 获取当前树的高度
     * @return 高度值
     */
    public int getHeight() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 获取左子树的高度
     * @return
     */
    public int getLeftHeight() {
        return left == null ? 0 : left.getHeight();
    }

    /**
     * 获取右子树的高度
     * @return
     */
    public int getRightHeight() {
        return right == null ? 0 : right.getHeight();
    }

    /**
     * 删除节点树
     * @param code 预删除的树的顶点的 code 码
     */
    public void delTreeOfNode(int code) {
        if (left != null && left.getCode() == code) {
            left = null;
            return;
        } else if (right != null && right.getCode() == code) {
            right = null;
            return;
        }

        if (left != null) {
            left.delTreeOfNode(code);
        }
        if (right != null) {
            right.delTreeOfNode(code);
        }
    }

    /**
     * 左旋转
     */
    public void leftRotate() {

    }

    /**
     * 右旋转
     */
    public void rightRotate() {

    }




}
