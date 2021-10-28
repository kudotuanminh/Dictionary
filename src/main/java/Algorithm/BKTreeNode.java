package Algorithm;

import java.util.*;
import com.ntm.dictionary.Word;

public class BKTreeNode {
    final Word word;
    final Map<Integer, BKTreeNode> children =
            new HashMap<Integer, BKTreeNode>();

    public BKTreeNode(Word word) {
        this.word = word;
    }

    protected BKTreeNode getChild(int index) {
        return this.children.get(index);
    }

    protected void addChild(int pos, BKTreeNode child) {
        this.children.put(pos, child);
    }

    public ArrayList<Word> search(String input, int maxDistance) {
        int distance = Levenshtein.distance(this.word.getWordTarget(), input);
        ArrayList<Word> matches = new ArrayList<Word>();
        if (distance <= maxDistance)
            matches.add(this.word);
        if (children.size() == 0)
            return matches;
        int i = Math.max(1, distance - maxDistance);
        for (; i <= distance + maxDistance; i++) {
            BKTreeNode child = children.get(i);
            if (child == null)
                continue;
            matches.addAll(child.search(input, maxDistance));
        }
        return matches;
    }
}
