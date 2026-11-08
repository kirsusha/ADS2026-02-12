package by.it.group551001.kirinskaya.lesson14;
import java.util.Scanner;
import java.util.Arrays;

public class StatesHanoiTowerC {

    static int[] parent;
    static int[] size;
    static int stepCounter;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int moves = (1 << N) - 1;

        parent = new int[moves + 1];
        size = new int[moves + 1];
        for (int i = 1; i <= moves; i++) { parent[i] = i; size[i] = 1; }

        int[] rep = new int[N + 1];
        int[][] towers = new int[3][N];
        int[] heights = new int[3];

        for (int i = 0; i < N; i++) towers[0][i] = N - i;
        heights[0] = N;

        stepCounter = 0;
        hanoi(N, 0, 1, 2, towers, heights, rep);

        int[] cnt = new int[moves + 1];
        for (int i = 1; i <= moves; i++) cnt[find(i)]++;

        int[] res = new int[moves + 1];
        int rc = 0;
        for (int i = 1; i <= moves; i++) if (cnt[i] > 0) res[rc++] = cnt[i];

        Arrays.sort(res, 0, rc);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rc; i++) {
            if (i > 0) sb.append(' ');
            sb.append(res[i]);
        }
        System.out.println(sb);
    }

    static void hanoi(int n, int from, int to, int aux,
                      int[][] towers, int[] heights, int[] rep) {
        if (n == 0) return;
        hanoi(n - 1, from, aux, to, towers, heights, rep);

        int disk = towers[from][heights[from] - 1];
        heights[from]--;
        towers[to][heights[to]] = disk;
        heights[to]++;

        stepCounter++;

        int maxH = Math.max(heights[0], Math.max(heights[1], heights[2]));

        if (rep[maxH] == 0) rep[maxH] = stepCounter;
        else union(rep[maxH], stepCounter);

        hanoi(n - 1, aux, to, from, towers, heights, rep);
    }

    static int find(int x) {
        while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
        return x;
    }

    static void union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return;
        if (size[ra] < size[rb]) { parent[ra] = rb; size[rb] += size[ra]; }
        else { parent[rb] = ra; size[ra] += size[rb]; }
    }
}