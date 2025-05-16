import java.util.*;

class Solution {
    
    static class Node implements Comparable<Node> {
        public int num;
        public int x, y;
        public Node left, right;
        
        public Node(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.left = null;
            this.right = null;
        }
        
        @Override
        public int compareTo(Node n) {
            if (this.y == n.y) return Integer.compare(this.x, n.x); // y가 같으면 x축 오름차순
            else return Integer.compare(n.y, this.y); // y축 내림차순
        }
        
    }
    
    static class BST {
        private Node root;
        
        private Node insertNode(Node node, Node val) {
            if (node == null) return val;
            
            if (val.x >= node.x) node.right = insertNode(node.right, val);
            else node.left = insertNode(node.left, val);
            
            return node;
        }
        
        private void preOrder(Node node, List<Integer> result) {
            if (node == null) return;
            
            // V L R
            result.add(node.num);
            preOrder(node.left, result);
            preOrder(node.right, result);
        }
        
        private void postOrder(Node node, List<Integer> result) {
            if (node == null) return;
            
            // L R V
            postOrder(node.left, result);
            postOrder(node.right, result);
            result.add(node.num);
        }
        
        public BST() {
            this.root = null;
        }
        
        public void insert(Node val) {
            root = insertNode(root, val);
        }
        
        public int[] getPreOrder() {
            List<Integer> result = new ArrayList<>();
            preOrder(root, result);
            int[] res = new int[result.size()];
            for(int i=0; i<result.size(); i++) res[i] = result.get(i);
            return res;
        }
        
        public int[] getPostOrder() {
            List<Integer> result = new ArrayList<>();
            postOrder(root, result);
            int[] res = new int[result.size()];
            for(int i=0; i<result.size(); i++) res[i] = result.get(i);
            return res;
        }
        
    }
    
    public int[][] solution(int[][] nodeinfo) {
        // 1. 노드 번호와 좌표로 구성된 클래스를 만든다.
        // 2. y축 내림차순, x축 오름차순 순으로 노드들을 정렬한다.
        Queue<Node> pq = new PriorityQueue<>();
        for(int i=0; i<nodeinfo.length; i++) {
            pq.add(new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]));
        }
        int siz = pq.size();
        
        // 3. 정렬한 노드들을 트리 형태로 만든다.
        BST bt = new BST();
        while(!pq.isEmpty()) bt.insert(pq.poll());
        
        // 4. 전위순회, 후위순회를 돌린다.
        int[][] answer = new int[2][];
        answer[0] = bt.getPreOrder();
        answer[1] = bt.getPostOrder();
        return answer;
    }
    
}