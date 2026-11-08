package by.it.group551001.kirinskaya.lesson14;
import java.util.*;

public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] first = scanner.nextLine().trim().split("\\s+");
        double D = Double.parseDouble(first[0]);
        int N = Integer.parseInt(first[1]);

        double[][] p = new double[N][3];
        for (int i = 0; i < N; i++) {
            String[] c = scanner.nextLine().trim().split("\\s+");
            p[i][0] = Double.parseDouble(c[0]);
            p[i][1] = Double.parseDouble(c[1]);
            p[i][2] = Double.parseDouble(c[2]);
        }

        int[] parent = new int[N];
        int[] size = new int[N];
        for (int i = 0; i < N; i++) { parent[i] = i; size[i] = 1; }

        double D2 = D * D;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double dx = p[i][0] - p[j][0];
                double dy = p[i][1] - p[j][1];
                double dz = p[i][2] - p[j][2];
                if (dx*dx + dy*dy + dz*dz < D2) {
                    union(parent, size, i, j);
                }
            }
        }

        int[] cnt = new int[N];
        for (int i = 0; i < N; i++) cnt[find(parent, i)]++;

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < N; i++) if (cnt[i] > 0) res.add(cnt[i]);
        res.sort(Collections.reverseOrder());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(res.get(i));
        }
        System.out.println(sb);
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
