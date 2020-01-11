package Common;

public class Message {
    private int exp;
    private int dest;
    private String action;
    private int [] pos;

    public Message(int exp, int dest, String action, int[] pos) {
        this.exp = exp;
        this.dest = dest;
        this.action = action;
        this.pos = pos;
    }

    public int[] getPos() {
        return pos;
    }

    public Message clone()throws CloneNotSupportedException {
        Message clone = new Message(exp, dest, action, pos);
        return clone;
    }


}
