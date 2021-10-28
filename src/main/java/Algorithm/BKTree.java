package Algorithm;

import java.util.*;
import com.ntm.dictionary.Word;

public class BKTree extends Levenshtein {
    private BKTreeNode root;

    private void addInternal(BKTreeNode source, BKTreeNode newNode) {
        if (source.equals(newNode))
            return;
        int distance = distance(source.word.getWordTarget(),
                newNode.word.getWordTarget());
        BKTreeNode bkNode = source.getChild(distance);
        if (bkNode == null) {
            source.addChild(distance, newNode);
        } else
            addInternal(bkNode, newNode);
    }

    public void add(Word node) {
        BKTreeNode newNode = new BKTreeNode(node);
        if (root == null) {
            root = newNode;
        }
        addInternal(root, newNode);
    }

    public ArrayList<Word> search(String input, int maxDist) {
        return root.search(input, maxDist);
    }
}
