import static java.lang.System.arraycopy;

class IntList {
    public int head;
    public IntList tail;
    public IntList(int head0, IntList tail0) {
        head = head0;
        tail = tail0;
    }
    public IntList() { this(0, null); }
    public static IntList list(Integer ... args) {
        IntList result = null;
        for(int x : args) {
            result = new IntList(x, result);
        }
        return result;
    }

    public int size() {
        int size = 0;
        IntList p = this;
        while(p != null) {
            p = p.tail;
            size += 1;
        }
        return size;
    }

    @Override
    public boolean equals(Object x) {
        if(!(x instanceof IntList)) {
            return false;
        }
        IntList L = (IntList) x;
        if(size() != L.size()) {
            return false;
        }
        IntList p1 = this;
        IntList p2 = L;
        for(; p1 != null && p2 != null; p1 = p1.tail, p2 = p2.tail) {
            if(p1.head != p2.head) {
                return false;
            }
        }
        return true;
    }
}

class Meme {
    String meme;
    int dankness;
    public Meme(String name, int dankeness) {
        meme = name;
        this.dankness = dankness;
    }
    public static int getWeekestMeme(Meme[] memes) {
        if(memes == null || memes.length == 0) {
            return 0;
        }
        int minDank = Integer.MAX_VALUE;
        for (int i = 0; i < memes.length; i++) {
            minDank = Math.min(minDank,memes[i].dankness);
        }
        return minDank;
    }
}

interface BinaryPredicate {
    boolean test(int a, int b);
}

class Utils{
    public static boolean allPairsCheck(BinaryPredicate p, IntList L) {
        for(IntList l = L; l.tail != null; l = l.tail) {
            if(!p.test(l.head, l.tail.head)) {
                return false;
            }
        }
        return true;
    }

    public static boolean ascending(IntList L) {
        return allPairsCheck (new NonDescending(), L);
    }
}

class NonDescending implements BinaryPredicate {
    public boolean test(int a, int b) {
        return a <= b;
    }
}

public class Main {
    public static void main(String[] args) {
        int x, y, z;
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                x = a;
                y = b;
                for (int i = 0; i < 4; i++) {
                    z = x^y;
                    x = (x & y) << 1;
                    y = z;
                }
                System.out.println("a:" + a + " b:" + b + " result:" + y);
            }
        }
    }
}