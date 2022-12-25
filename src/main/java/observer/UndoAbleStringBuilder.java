package observer;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class implements 5 methods from StringBuilder using its own methods, while improving those with a new
 * undo() method aimed at undoing our last change.
 */
public class UndoAbleStringBuilder extends UndoableStringBuilder {


    /**
     * Default constructor.
     * Initializes a new StringBuilder instance and a new empty stack instance.
     */
    public UndoAbleStringBuilder(){
        super();
    }

    /**
     * Appends str parameter using StringBuilder method to the string buffer of StringBuilder.
     * @param str - String we are willing to append to StringBuilder string buffer.(Will accept null)
     * @return UndoAbleStringBuilder reference.
     */
    public UndoAbleStringBuilder append(String str){
        return (UndoAbleStringBuilder) super.append(str);
    }
    /**
     * Deletes a portion of the string from index start to index end.
     * @param start - First index to delete from.
     * @param end - Last index to delete from.
     * @return UndoAbleStringBuilder reference.
     * @throws StringIndexOutOfBoundsException - Whenever one of the indices is negative, end is smaller than start
     * or start is out of the bounds of the StringBuilder string.
     */
    public UndoAbleStringBuilder delete(int start, int end) throws StringIndexOutOfBoundsException{
        try{
            return (UndoAbleStringBuilder) super.delete(start, end);
        }
        catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
            return this;
        }
    }


    /**
     * Insert given string to the StringBuilder string given a beginning offset.
     * @param offset - Starting index from which we insert str.
     * @param str - String we wish to insert.(Will accept null)
     * @return UndoAbleStringBuilder reference.
     * @throws StringIndexOutOfBoundsException - Whenever offset is less than 0 or greater than the string length.
     */
    public UndoAbleStringBuilder insert(int offset, String str) throws StringIndexOutOfBoundsException {
        try{
            return (UndoAbleStringBuilder) super.insert(offset, str);
        }
        catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
            return this;
        }
    }


    /**
     * Replaces substring from range [start, end] to the str string.
     * @param start - Starting index from which we replace(included).
     * @param end - Ending index from which we replace(excluded).
     * @param str - String we wish to put inside StringBuilder string(Won't accept null).
     * @return UndoAbleStringBuilder reference.
     * @throws StringIndexOutOfBoundsException - Whenever we insert negative indices, end is smaller than start,
     * or start is out of bounds of the StringBuilder string range.
     * @throws NullPointerException - Whenever str parameter is null.
     */
    public UndoAbleStringBuilder replace(int start, int end, String str) throws StringIndexOutOfBoundsException, NullPointerException{
        try{
            return (UndoAbleStringBuilder) super.replace(start, end, str);
        }
        catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
            return this;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return this;
        }
    }

    /**
     * Reverse StringBuilder string.
     * @return - UndoAbleStringBuilder reference.
     */
    public UndoAbleStringBuilder reverse(){
        return (UndoAbleStringBuilder) super.reverse();
    }

    /**
     * this method will undo the previous change in the string
     * @throws EmptyStackException - If stack is empty. Will initialize string to the empty string.
     */
    public void undo() throws EmptyStackException{
        super.undo();
    }

    /**
     *
     * @return String value of the reference.
     * @throws RuntimeException - Whenever the string is empty.
     */
    @Override
    public String toString() throws RuntimeException{
        try{
            return super.toString();
        }
        catch (RuntimeException e){
            e.getStackTrace();
            return "StringBuilder has empty string";
        }
    }
}
