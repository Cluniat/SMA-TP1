package Common;

public class Message {
    private int exp;
    private int dest;
    private int date;
    private String action;
    private int [] pos;

    public Message(int exp, int dest, int date, String action, int[] pos) {
        this.exp = exp;
        this.dest = dest;
        this.date = date;
        this.action = action;
        this.pos = pos;
    }

    public int[] getPos() {
        return pos;
    }


}
