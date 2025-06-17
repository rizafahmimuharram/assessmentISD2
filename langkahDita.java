import java.util.*;

 class Graph {
    Map<String, List<String>> adjList = new HashMap<>();

    public void addRelasi(String langkah1, String langkah2){
        adjList.putIfAbsent(langkah1, new ArrayList<>());
        adjList.putIfAbsent(langkah2, new ArrayList<>());
        adjList.get(langkah1).add(langkah2);
    }

    public void bfs(String start){
        System.out.print("BFS (berdekatan dulu): ");
        System.out.println();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while(!queue.isEmpty()) {
            String langkah = queue.poll();
            System.out.print(langkah + ", ");
            for(String neighbor : adjList.getOrDefault(langkah, List.of())){
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        
        System.out.println();
    }

    public void dfs(String start) {
        System.out.print("DFS (menjauh dulu): ");
        System.out.println();
        Set<String> visited = new HashSet<>();
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(String langkah, Set<String> visited) { 
        visited.add(langkah); 
        System.out.print(langkah + ", "); 
        for (String neighbor : adjList.getOrDefault(langkah, List.of())) { 
            if (!visited.contains(neighbor)) { 
                dfsHelper(neighbor, visited); 
            } 
        } 
    } 

    public void topologicalSort() {
    Map<String, Integer> inDegree = new HashMap<>();
    for (String node : adjList.keySet()) {
        inDegree.putIfAbsent(node, 0);
        for (String neighbor : adjList.get(node)) {
            inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
        }
    }

    Queue<String> queue = new LinkedList<>();
    for (String node : inDegree.keySet()) {
        if (inDegree.get(node) == 0) {
            queue.add(node);
        }
    }

    List<String> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        String node = queue.poll();
        result.add(node);

        for (String neighbor : adjList.getOrDefault(node, List.of())) {
            inDegree.put(neighbor, inDegree.get(neighbor) - 1);
            if (inDegree.get(neighbor) == 0) {
                queue.add(neighbor);
            }
        }
    }

    System.out.println("urutan resep sampai eat:");
    for (int i = 1; i < result.size(); i++) {
        System.out.print(result.get(i));
        if (i < result.size() - 1) System.out.print(" -> ");
    }
    System.out.println();
}

}



public class langkahDita{
    public static void main(String[] args) {
        Graph resep = new Graph();

        resep.addRelasi("bake bread", "serve bread");
        resep.addRelasi("preheat pan", "set plate");
        resep.addRelasi("preheat pan", "add krabby patty");
        resep.addRelasi("set plate", "serve bread");
        resep.addRelasi("pour sauce over patty", "eat");
        resep.addRelasi("add some pickles", "eat");
        resep.addRelasi("add tartar sauce ", "pour sauce over patty");
        resep.addRelasi("set plate", "add some pickles");
        resep.addRelasi("set plate", "serve patty");
        resep.addRelasi("add krabby patty", "add tartar sauce");
        resep.addRelasi("add krabby patty", "serve patty");
        resep.addRelasi("serve patty", "pour sauce over patty");
        resep.addRelasi("serve bread", "eat");
        resep.addRelasi("preheat oven", "bake bread");
        resep.addRelasi("preheat oven", "preheat pan");
        
        resep.bfs("preheat oven");
        System.out.println();
        resep.dfs("preheat oven");
        System.out.println();

        resep.topologicalSort();
    }
}