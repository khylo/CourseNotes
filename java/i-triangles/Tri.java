import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * See https://icpc.kattis.com/problems/triangles3
 * Soln:  https://www.youtube.com/watch?v=0LqHpmzRHzk&ab_channel=ICPCNews
 * Given grid, count triangles
 * For this 
 */
public class Tri {
    /**
     * The first line of input contains two integers  and  (, ), specifying the picture size, where  is the number of rows of vertices and  is the number of columns. 
     * Following this are 2r-1 lines, each of them having at most 2c-1 characters. 
     * Odd lines contain grid vertices (represented as lowercase x characters) and zero or more horizontal edges, while even lines contain zero or more diagonal edges. Specifically, picture lines with numbers  have vertices in positions  while lines with numbers  have vertices in positions  . All possible vertices are represented in the input (for example, see how Figure 1 is represented in Sample Input 2). Horizontal edges connecting neighboring vertices are represented by three dashes. Diagonal edges are represented by a single forward slash (‘/’) or backslash (‘\’) character. The edge characters will be placed exactly between the corresponding vertices. All other characters will be space characters. Note that if any input line could contain trailing whitespace, that whitespace may be omitted.
     * @param args
     */
    public static void main(String[] args) throws IOException{
        long start = System.currentTimeMillis();
        File input=new File("input.txt");
        if(args.length>0) // No param, so use default
            input=new File(args[0]);
        
        Tri tri = new Tri(Files.readString(input.toPath()));
        System.out.println(tri.solve());   
        System.out.println("time taken "+(System.currentTimeMillis()-start)+"ms"); 
    }

    String grid;
    int rows;
    int cols;

    public Tri(String input){
        String[] inputs = input.split("\n", 2);        
        String[]size = inputs[0].split(" ");
        this.grid=inputs[1];
        this.rows=Integer.parseInt(size[0]);
        this.cols=Integer.parseInt(size[1].trim());
    }
    
    /*
       x---x               x   x
        \ /                 \ / 
         x            or     x
        / \                 / \
       x   x               x---x
       Parse each row, only keep track of interesting positions 
          for even: keep track of places with points (could be top) or lines (could be bottom)). Check if closing triangle
          for odd keep track of lines only if they are close to an already tracked point. Can ignore lines that are not +-1 away from x
       So for abo
     */
    public int solve(){
        int count=0;
        String[] lines = grid.split("\n");
        // Loop thru keeping track of potential triangles
        for(String line: lines){
            if(count%2==0)
                Lines[] lines = parseEven(line);
            else
                parseOdd(line);
            count++;
        }
        return rows*cols;
    }

    
    parseEven(String line){
        List<Point> points=List.of();
        List<Line> lines=List.of();
        for(int i=0;i<line.length();i++){
            String chr = line.substring(i, i+1);
            if(chr.equals("x")){ // Got point.
                if(false){
                    // First Check closes triangle
                } else       // If not keep track     
                    points.add(new Point(row, i));
            }else if(chr.equals("-")){

            }
        }
    }
    parseOdd(String line){

    }
    
}

// AKA vertex
class Point{
    int x, y;
}

class Edge{
    Point p1, p2;
}

class Line{
    Point[] points;
    Edge[] edges;
}

class Triangle{
    Point p1,p2,p3;

    boolean isTriangle(){
        return false;
    }
}

