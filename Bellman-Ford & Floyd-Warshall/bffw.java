import java.util.*;
import java.io.*;

/*Citations
https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/
https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
*/

class Edge { 
    int src, dest, weight; 
    Edge() { 
        src = dest = weight = 0; 
    } 
}

class BellmanOutput{
    int last_src; 
    int last_weight;
    BellmanOutput(){
        last_src = last_weight = 0;
    }
}

public class bffw {
    
    public static int inf = 99999999;
    int V, E; 
    Edge edge[]; 
    
    // Creates a graphbf with V vertices and E edges 
    bffw(int v, int e) 
    { 
        V = v; 
        E = e; 
        edge = new Edge[e]; 
        for (int i = 0; i < e; ++i) 
            edge[i] = new Edge(); 
    } 
    
    public static void BellmanFord(bffw graphbf, int src) 
    { 
        int V = graphbf.V, E = graphbf.E; 
        int dist[] = new int[V]; 
        BellmanOutput outputs[] = new BellmanOutput[V];
        for(int k = 0; k < V; k++){
            outputs[k] = new BellmanOutput();
        }
        
       
        // Step 1: Initialize distances from src to all other 
        // vertices as INFINITE 
        for (int i = 0; i < V; ++i){
            dist[i] = Integer.MAX_VALUE;  
        }

        dist[src-1] = 0;

        // Step 2: Relax all edges |V| - 1 times. A simple 
        // shortest path from src to any other vertex can 
        // have at-most |V| - 1 edges 
        for (int i = 1; i < V; ++i) { 
            for (int j = 0; j < E; ++j) { 
                int u = graphbf.edge[j].src; 
                int v = graphbf.edge[j].dest; 
                int weight = graphbf.edge[j].weight; 
                if (dist[u-1] != Integer.MAX_VALUE && dist[u-1] + weight < dist[v-1]){
                    dist[v-1] = dist[u-1] + weight; 
                    outputs[v-1].last_src = u-1;
                    outputs[v-1].last_weight = dist[v-1];
                }
            } 
        } 
        
        dist[src-1] = 0; 
        
        
        try{
            fileWrite(outputs);
        }catch(Exception e){
            System.out.println(e);
        }
        
        
    } 

    //method that writes result data into cop3503-asn2-output-Rizk-Alex.txt
    public static void fileWrite(BellmanOutput output[]) throws Exception{
        //Names the new file
        FileWriter writer = new FileWriter("cop3503-asn2-output-Rizk-Alex-bf.txt");

        //Length of the new file
        int len = output.length;

        //Formats the new txt output in the desired layout
        writer.write(len + "\n");
        for(int i = 0; i < len ; i++){
            if(output[i].last_src == 0  && output[i].last_weight == 0){
                writer.write(((i + 1) + " " + "0" + " " + "0" + "\n"));
            }else{
                writer.write((i + 1) + " " + output[i].last_weight + " " + (output[i].last_src + 1) + "\n");
            }
        }
        writer.close();
    }

    static void RunBellmanFord(){
        try{
            //Takes in the txt file to be processed
            Scanner data = new Scanner(new File("cop3503-asn3-input-1.txt"));

            //Nummber of verticies
            int num_v = Integer.parseInt(data.nextLine());

            //source of vertex
            int sv = Integer.parseInt(data.nextLine());

            //Number of edges 
            int edges = Integer.parseInt(data.nextLine());

            bffw graphbf = new bffw(num_v, edges*2);

            //Places the V_S, destinationination, and weight into edgegraphbf
            int s,d,w;
            for(int i = 0; i < edges; i++){
                //Processes the rows of integers
                s = data.nextInt();
                d = data.nextInt();
                w = data.nextInt();
                //edgegraphbf being populated
                graphbf.edge[i*2].src = s;
                graphbf.edge[i*2].dest = d;
                graphbf.edge[i*2].weight = w;
                graphbf.edge[2*i+1].src = d;
                graphbf.edge[2*i+1].dest = s;
                graphbf.edge[2*i+1].weight = w;
            }
            data.close();
            BellmanFord(graphbf, sv);
            
            
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    static void FloydWarshall(int graphfw[][], int num_v){
        int dist[][] = new int[num_v][num_v];
        int i, k, j;

        for (i = 0; i < num_v; i++){
            for (j = 0; j < num_v; j++){
                dist[i][j] = graphfw[i][j];
            }
        }

        for (k = 0; k < num_v; k++) 
        { 
            // Pick all vertices as source one by one 
            for (i = 0; i < num_v; i++) 
            { 
                // Pick all vertices as destination for the 
                // above picked source 
                for (j = 0; j < num_v; j++) 
                { 
                    // If vertex k is on the shortest path from 
                    // i to j, then update the value of dist[i][j] 
                    if (dist[i][k] + dist[k][j] < dist[i][j]) 
                        dist[i][j] = dist[i][k] + dist[k][j]; 
                } 
            } 
        }
        
        try{
        fileWritefw(dist, num_v);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void fileWritefw(int dist[][],int num_v) throws Exception{
        //Names the new file
        FileWriter writer = new FileWriter("cop3503-asn2-output-Rizk-Alex-fw.txt");

        //Length of the new file
        int len = num_v;

        //Formats the new txt output in the desired layout
        writer.write(len + "\n");
        for (int i=0; i<num_v; ++i) 
        { 
            for (int j=0; j<num_v; ++j) 
            { 
                if (dist[i][j]==inf) 
                    writer.write("INF "); 
                else
                    writer.write(dist[i][j]+" "); 
            } 
            writer.write("\n");
        } 
        writer.close();
    }
    static void RunFloydWarshall(){
        try{
            //Takes in the txt file to be processed
            Scanner datafw = new Scanner(new File("cop3503-asn3-input.txt"));

            //Nummber of verticies
            int num_v = Integer.parseInt(datafw.nextLine());

            //source of vertex
            int sv = Integer.parseInt(datafw.nextLine());

            //Number of edges 
            int edges = Integer.parseInt(datafw.nextLine());

            int graphfw[][] = new int[num_v][num_v];

            for(int q = 0; q < num_v; q++){
                for(int w = 0; w < num_v; w++){
                    graphfw[q][w] = (int) inf;
                }
            }

            for(int i = 0; i < edges; i++){
                int s = datafw.nextInt();
                int d = datafw.nextInt();
                int w = datafw.nextInt();  
                
                graphfw[s-1][d-1] = w;
                graphfw[d-1][s-1] = w;
            } 

            //fill diagonal of matrix with 0
            for(int j = 0; j < num_v; j++){
                graphfw[j][j] = 0;
            }

            /*
            for(int o = 0; o < num_v; o++){
                for(int p = 0; p < num_v; p++){
                    System.out.println(graphfw[o][p]);
                }
            }
            */

            FloydWarshall(graphfw, num_v);
            
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void main(String args[]){
        RunBellmanFord();
        RunFloydWarshall();
    }
}