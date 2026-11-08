package by.it.group551001.kirinskaya.lesson13;
import java.util.*;
public class GraphB {
    public static void main(String[] args){
        Scanner scanner =new Scanner(System.in);
        String line = scanner.nextLine();
        Map<Integer, Set<Integer>> adj = new TreeMap<>();
        Set<Integer> allnodes = new TreeSet<>();

        if (!line.trim().isEmpty()){
            String[] edges = line.split(",");
            for (String edge: edges){
                String[] parts = edge.trim().split("->");
                int from = Integer.parseInt(parts[0].trim());
                int to = Integer.parseInt(parts[1].trim());
                allnodes.add(from);
                allnodes.add(to);
                adj.computeIfAbsent(from, k-> new TreeSet<>()).add(to);
                adj.computeIfAbsent(to, k->new TreeSet<>());
            }
        }

        Map<Integer, Integer> color = new HashMap<>();
        for (int node: allnodes){
            color.put(node, 0);
        }

        boolean hascycle =false;
        for (int node: allnodes){
            if (color.get(node)==0){
                if (dfs(node, adj, color)) {
                    hascycle =true;
                    break;
                }
            }
        }
        System.out.println(hascycle ? "yes" : "no");
    }

    private static boolean dfs(int node, Map<Integer, Set<Integer>> adj, Map<Integer, Integer> color){
        color.put(node, 1);
        for (int neighbor : adj.getOrDefault(node, Collections.emptySet())){
            if (color.get(neighbor)==1){
                return true;
            }
            if (color.get(neighbor) ==0 && dfs(neighbor, adj, color)){
                return true;
            }
        }
        color.put(node, 2);
        return false;
    }
}
