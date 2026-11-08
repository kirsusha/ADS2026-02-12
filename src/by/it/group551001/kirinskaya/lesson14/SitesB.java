package by.it.group551001.kirinskaya.lesson14;
import java.util.*;

public class SitesB {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Integer> idx = new HashMap<>();
        int cap = 16;
        int[] parent = new int[cap];
        int[] size = new int[cap];
        int n = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equals("end")) break;
            String[] parts = line.split("\\+");
            if (parts.length != 2) continue;
            int a = getOrAdd(parts[0].trim(), idx);
            int b = getOrAdd(parts[1].trim(), idx);
            int need = Math.max(a, b) + 1;
            if (need > parent.length) {
                int newCap = parent.length;
                while (newCap < need) newCap <<= 1;
                parent = Arrays.copyOf(parent, newCap);
                size = Arrays.copyOf(size, newCap);
            }
            while (n < need) { parent[n] = n; size[n] = 1; n++; }
            union(parent, size, a, b);
        }

        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) cnt[find(parent, i)]++;

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) if (cnt[i] > 0) res.add(cnt[i]);
        res.sort(Collections.reverseOrder());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(res.get(i));
        }
        System.out.println(sb);
    }

    static int getOrAdd(String s, Map<String, Integer> idx) {
        Integer i = idx.get(s);
        if (i == null) { i = idx.size(); idx.put(s, i); }
        return i;
    }

    static int find(int[] p, int x) {
        while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; }
        return x;
    }

    static void union(int[] p, int[] s, int a, int b) {
        int ra = find(p, a), rb = find(p, b);
        if (ra == rb) return;
        if (s[ra] < s[rb]) { p[ra] = rb; s[rb] += s[ra]; }
        else { p[rb] = ra; s[ra] += s[rb]; }
    }
}
