package by.it.group551001.kirinskaya.lesson13;
import java.util.*;
public class GraphC {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        Map<String, Set<String>> adj = new TreeMap<>();
        Map<String, Set<String>> radj = new TreeMap<>();
        Set<String> allnodes =new TreeSet<>();

        if(!line.trim().isEmpty()){
            String[] edges =line.split(",");
            for (String edge: edges){
                String[] parts = edge.trim().split("->");
                String from = parts[0].trim();
                String to =parts[1].trim();
                allnodes.add(from);
                allnodes.add(to);
                adj.computeIfAbsent(from, k->new TreeSet<>()).add(to);
                adj.computeIfAbsent(to, k->new TreeSet<>());
                radj.computeIfAbsent(to, k->new TreeSet<>()).add(from);
                radj.computeIfAbsent(from, k->new TreeSet<>());

            }
        }

        Set<String> visited =new HashSet<>();
        Deque<String> order = new ArrayDeque<>();
        for (String node: allnodes) {
            if (!visited.contains(node)) {
                dfsorder(node, adj, visited, order);
            }
        }

        visited.clear();
        List<List<String>> sccs = new ArrayList<>();
        while (!order.isEmpty()){
            String node = order.pop();
            if (!visited.contains(node)) {
                List<String> component = new ArrayList<>();
                dfscollect(node, radj, visited, component);
                Collections.sort(component);
                sccs.add(component);
            }
        }

        int n = sccs.size();
        Map<String, Integer> nodetocomp = new HashMap<>();
        for (int i =0; i<n; i++){
            for (String node: sccs.get(i)) {
                nodetocomp.put(node, i);
            }
        }

        int[] inedges = new int[n];
        int[] outedges = new int[n];
        Set<Long> seenedges = new HashSet<>();

        for (Map.Entry<String, Set<String>> e: adj.entrySet()){
            int fromcomp = nodetocomp.get(e.getKey());
            for (String to : e.getValue()) {
                int tocomp = nodetocomp.get(to);
                if (fromcomp!=tocomp){
                    long key =(long) fromcomp*n + tocomp;
                    if (seenedges.add(key)){
                        outedges[fromcomp]++;
                        inedges[tocomp]++;
                    }
                }
            }
        }

        int[] indeg = inedges.clone();
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {return sccs.get(a).get(0).compareTo(sccs.get(b).get(0)); });
        for (int i=0; i<n; i++){
            if (indeg[i] ==0){
                pq.add(i);
            }
        }

        Map<Integer, Set<Integer>> compadj = new HashMap<>();
        for (int i=0; i<n; i++){
            compadj.put(i, new TreeSet<>());
        }
        for (long key: seenedges){
            int fromcomp = (int) (key/n);
            int tocomp = (int)(key%n);
            compadj.get(fromcomp).add(tocomp);
        }
        List<Integer> topoorder = new ArrayList<>();
        while (!pq.isEmpty()){
            int comp = pq.poll();
            topoorder.add(comp);
            for (int next: compadj.get(comp)) {
                indeg[next]--;
                if (indeg[next] ==0){
                    pq.add(next);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i=0; i<topoorder.size(); i++){
            if (i>0) sb.append("\n");
            List<String> comp = sccs.get(topoorder.get(i));
            for (String node: comp) {
                sb.append(node);
            }
        }
        System.out.println(sb.toString());
    }

    private static void dfsorder(String node, Map<String, Set<String>> adj, Set<String> visited, Deque<String> order){
        visited.add(node);
        for (String neighbor: adj.getOrDefault(node, Collections.emptySet())) {
            if (!visited.contains(neighbor)) {
                dfsorder(neighbor, adj, visited, order);
            }
        }
        order.push(node);
    }

    private static void dfscollect(String node, Map<String, Set<String>> radj, Set<String> visited, List<String> component) {
        visited.add(node);
        component.add(node);
        for (String neighbor : radj.getOrDefault(node, Collections.emptySet())) {
            if (!visited.contains(neighbor)) {
                dfscollect(neighbor, radj, visited, component);
            }
        }
    }
}
