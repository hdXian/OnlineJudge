import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int T;

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {

        T = Integer.parseInt(reader.readLine()); // 테스트 케이스 개수

        for(int i=0; i<T; i++) {
            calc();
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb);
    }

    public static void calc() throws Exception {
        int n = Integer.parseInt(reader.readLine()); // 노드 개수
        List<Integer> preOrder = new LinkedList<>();
        List<Integer> inOrder = new LinkedList<>();

        // 전위순회 결과 저장
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++) preOrder.add(Integer.parseInt(tkn.nextToken()));

        // 중위순회 결과 저장
        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++) inOrder.add(Integer.parseInt(tkn.nextToken()));

        Node root = makeTree(preOrder, inOrder);
        postOrder(root);
        sb.append("\n");
    }

    public static Node makeTree(List<Integer> preOrder, List<Integer> inOrder) {
        if (preOrder.isEmpty() || inOrder.isEmpty())
            return null;

        Integer root = preOrder.get(0); // 전위순회 결과의 첫 원소가 루트 노드임

        int idx = inOrder.indexOf(root); // 중위순회 결과에서의 루트 인덱스에 따라 좌, 우 트리가 결정됨.

        int end = preOrder.size(); // 주어진 preOrder, inOrder 리스트의 길이 (둘이 같아서 하나로 써도 됨)

        List<Integer> leftTree_in = inOrder.subList(0, idx);
        List<Integer> rightTree_in = inOrder.subList(idx+1, end);

        List<Integer> leftTree_pre = preOrder.subList(1, leftTree_in.size()+1);
        List<Integer> rightTree_pre = preOrder.subList(leftTree_in.size()+1, end);

        Node n = new Node(root, null, null);
        n.left = makeTree(leftTree_pre, leftTree_in);
        n.right = makeTree(rightTree_pre, rightTree_in);

        return n;
    }

    public static void postOrder(Node node) {
        if (node.left != null)
            postOrder(node.left);

        if (node.right != null)
            postOrder(node.right);

        sb.append(node.val).append(" ");
    }

    static class Node {
        int val;
        public Node left;
        public Node right;

        public Node(int val, Node l, Node r) {
            this.val = val;
            left = l;
            right = r;
        }

    }

}
