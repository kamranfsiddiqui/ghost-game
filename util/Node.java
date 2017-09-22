package util;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/** node class to represent a node in a Trie
  * @author Kamran Siddiqui*/
public class Node {
    boolean isWord;
    Map<Character, Node> links;
    List<Character> chars;

    /** Node class constructor makes links  an empty HashMap and exists is set to false */
    public Node() {
        links = new HashMap<Character, Node>();
        isWord = false;
        chars = new ArrayList<Character>();
    }

    public boolean isLeaf() {
      if(this.links.isEmpty())
        return true;
      return false;
    }

    public int relativeHeight() {
      if(this.links.isEmpty()) {
        return 1;
      } else {
        List<Integer> childHeights = new ArrayList<Integer>();
        for(Map.Entry<Character, Node> child: this.links.entrySet()) {
          childHeights.add(child.getValue().relativeHeight());
        }
        return 1 + childHeights.stream().max(Comparator.naturalOrder()).get();
      }      
    
    }

    public Map<Character, Node> links() {
      return this.links;
    }

    public List<Character> chars() {
      return this.chars;
    }

    public boolean isTerminalNode() {
      return this.links.isEmpty();
    }
    

}

