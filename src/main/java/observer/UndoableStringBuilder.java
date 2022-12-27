package observer;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class implements 5 methods from StringBuilder using its own methods, while improving those with a new
 * undo() method aimed at undoing our last change.
 */
public class UndoableStringBuilder {
    private StringBuilder stringBuilder;
    private Stack<String> undoStack;


    /**
     * Default constructor.
     * Initializes a new StringBuilder instance and a new empty stack instance.
     */
    public UndoableStringBuilder(){
        this.stringBuilder = new StringBuilder();
        this.undoStack = new Stack<>();
    }

    /**
     * Appends str parameter using StringBuilder method to the string buffer of StringBuilder.
     * @param str - String we are willing to append to StringBuilder string buffer.(Will accept null)
     * @return UndoableStringBuilder reference.
     */
    public UndoableStringBuilder append(String str){
        undoStack.add(stringBuilder.toString());
        stringBuilder.append(str);
        return this;
    }
    /**
     * Deletes a portion of the string from index start to index end.
     * @param start - First index to delete from.
     * @param end - Last index to delete from.
     * @return UndoableStringBuilder reference.
     * @throws StringIndexOutOfBoundsException - Whenever one of the indices is negative, end is smaller than start
     * or start is out of the bounds of the StringBuilder string.
     */
    public UndoableStringBuilder delete(int start, int end) throws StringIndexOutOfBoundsException{
        try{
            undoStack.add(stringBuilder.toString());
            stringBuilder.delete(start, end);
        }
        catch (StringIndexOutOfBoundsException e){
            undoStack.pop();
            e.printStackTrace();
            System.err.println("Invalid indices entered.");
        }
        return this;
    }


    /**
     * Insert given string to the StringBuilder string given a beginning offset.
     * @param offset - Starting index from which we insert str.
     * @param str - String we wish to insert.(Will accept null)
     * @return UndoableStringBuilder reference.
     * @throws StringIndexOutOfBoundsException - Whenever offset is less than 0 or greater than the string length.
     */
    public UndoableStringBuilder insert(int offset, String str) throws StringIndexOutOfBoundsException {
        try{
            undoStack.add(stringBuilder.toString());
            stringBuilder.insert(offset, str);
        }
        catch (StringIndexOutOfBoundsException e){
            undoStack.pop();
            e.printStackTrace();
            System.err.println("Invalid offset given");
        }
        return this;
    }


    /**
     * Replaces substring from range [start, end] to the str string.
     * @param start - Starting index from which we replace(included).
     * @param end - Ending index from which we replace(excluded).
     * @param str - String we wish to put inside StringBuilder string(Won't accept null).
     * @return UndoableStringBuilder reference.
     * @throws StringIndexOutOfBoundsException - Whenever we insert negative indices, end is smaller than start,
     * or start is out of bounds of the StringBuilder string range.
     * @throws NullPointerException - Whenever str parameter is null.
     */
    public UndoableStringBuilder replace(int start, int end, String str) throws StringIndexOutOfBoundsException, NullPointerException{
        try{
            undoStack.add(this.stringBuilder.toString());
            stringBuilder.replace(start, end, str);
        }
        catch (StringIndexOutOfBoundsException e){
            undoStack.pop();
            e.printStackTrace();
            System.err.println("Invalid indices given.");
        }
        catch (NullPointerException e){
            undoStack.pop();
            e.printStackTrace();
            System.err.println("Can't replace null!");
        }
        return this;
    }

    /**
     * Reverse StringBuilder string.
     * @return - UndoableStringBuilder reference.
     */
    public UndoableStringBuilder reverse(){
        undoStack.add(this.stringBuilder.toString());
        stringBuilder.reverse();
        return this;
    }

    /**
     * this method will undo the previous change in the string
     * @throws EmptyStackException - If stack is empty. Will initialize string to the empty string.
     */
    public void undo() throws EmptyStackException{
        try{
            stringBuilder = new StringBuilder(undoStack.pop());
        }
        catch (EmptyStackException e){
            stringBuilder = new StringBuilder(); //Whenever we undo an empty stack, declare string to be the empty string.
        }
    }

    /**
     *
     * @return String value of the reference.
     * @throws RuntimeException - Whenever the string is empty.
     */
    @Override
    public String toString() throws RuntimeException{
        try{
            return stringBuilder.toString();
        }
        catch (RuntimeException e){
            e.getStackTrace();
            return "StringBuilder has empty string";
        }
    }
}