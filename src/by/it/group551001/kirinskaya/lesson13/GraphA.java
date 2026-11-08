package by.it.group551001.kirinskaya.lesson13;
import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        // Parse edges (строковые метки вершин)
        Map<String, Set<String>> adj = new TreeMap<>();
        Set<String> allNodes = new TreeSet<>();

        if (!line.trim().isEmpty()) {
            String[] edges = line.split(",");
            for (String edge : edges) {
                String[] parts = edge.trim().split("->");
                String from = parts[0].trim();
                String to = parts[1].trim();
                allNodes.add(from);
                allNodes.add(to);
                adj.computeIfAbsent(from, k -> new TreeSet<>()).add(to);
                adj.computeIfAbsent(to, k -> new TreeSet<>());
            }
        }


        Map<String, Integer> inDegree = new TreeMap<>();
        for (String node : allNodes) {
            inDegree.put(node, 0);
        }
        for (Map.Entry<String, Set<String>> e : adj.entrySet()) {
            for (String to : e.getValue()) {
                inDegree.merge(to, 1, Integer::sum);
            }
        }

        PriorityQueue<String> pq = new PriorityQueue<>();
        for (Map.Entry<String, Integer> e : inDegree.entrySet()) {
            if (e.getValue() == 0) {
                pq.add(e.getKey());
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            String node = pq.poll();
            result.add(node);
            for (String neighbor : adj.getOrDefault(node, Collections.emptySet())) {
                inDegree.merge(neighbor, -1, Integer::sum);
                if (inDegree.get(neighbor) == 0) {
                    pq.add(neighbor);
                }
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(result.get(i));
        }
        System.out.println(sb.toString());
    }
}