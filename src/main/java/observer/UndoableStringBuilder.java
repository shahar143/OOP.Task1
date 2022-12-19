package observer;
import java.util.Stack;
/**
 * StringBuilder with undo support
 * java.lang.StringBuilder - class with modifier <b>final</b>,
 * so no inheritance, use delegation.
 */
interface Action{
    void undo();
}

/*
Use the class you've implemented in previous assignment
 */
public class UndoableStringBuilder {
    private StringBuilder stringBuilder; // delegate
    /**
     * Operations that are the reverse of those performed.
     * That is, when append is called, it is placed on the stack
     * "delete" operation. When calling undo() it
     * will be executed.    */
    private Stack<Action> actions = new Stack<>();

    // constructor
    public UndoableStringBuilder() {
        stringBuilder = new StringBuilder();
    }

    public UndoableStringBuilder reverse() {
        stringBuilder.reverse();
        Action action = new Action(){
            public void undo() {
                stringBuilder.reverse();
            }
        };
        actions.add(action);
        return this;
    }

    public UndoableStringBuilder append(String str) {
        stringBuilder.append(str);

        Action action = new Action(){
            public void undo() {
                stringBuilder.delete(
                        stringBuilder.length() - str.length(),
                        stringBuilder.length());
            }
        };
        actions.add(action);
        return this;
    }

    public UndoableStringBuilder insert(int offset, String str) {
        stringBuilder.insert(offset, str);
        Action action = new Action(){
            public void undo() {
                stringBuilder.delete(offset, offset+str.length());
            }
        };
        actions.add(action);
        return this;
    }

    public UndoableStringBuilder delete(int start, int end) {
        String deleted = stringBuilder.substring(start, end);
        stringBuilder.delete(start, end);
        Action action = new Action(){
            public void undo() {
                stringBuilder.insert(start, deleted);
            }
        };
        actions.add(action);
        return this;
    }

    public UndoableStringBuilder replace(int start, int end, String str) {
        String deleted = stringBuilder.substring(start, end);
        stringBuilder.replace(start, end, str);
        Action action = new Action(){
            public void undo() {
                stringBuilder.replace(start, start+str.length(), deleted);
            }
        };
        actions.add(action);
        return this;
    }

    public void undo(){
        if(!actions.isEmpty()){
            actions.pop().undo();
        }
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
