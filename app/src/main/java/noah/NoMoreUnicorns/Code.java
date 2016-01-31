package noah.NoMoreUnicorns;


import java.util.ArrayList;
import java.util.Objects;

public class Code {

    private String codeTitle;
    private ArrayList<String> codeLines;

    private int currentLine;

    public Code() {
        codeLines = new ArrayList<String>();
    }

    public Code(String title) {
        this();
        codeTitle = title;
    }


    public boolean putLine(String line) {
        return codeLines.add(line);
    }

    /*public String getLine(int index) {
        return codeLines.get(index);
    }*/

    public int getLine(String line) {
        for (String string : codeLines) {
            if (string.equals(line)) {
                return codeLines.indexOf(string);
            }
        }
        return -1;
    }

    public void editLine (int index, String string) {
        if (index >= 0 && index <= codeLines.size() && string != null) {
            codeLines.set(index, string);
        }
    }

    public boolean setCurrentLine(int index) {
        /*if (index == 0) {
            index = 1;
        }*/
        if (index == -1) {
            codeLines.set(currentLine,  codeLines.get(currentLine).substring(0, codeLines.get(currentLine).length() - 3));
            currentLine = 0;
            return true;
        }

        if(codeLines.size() < index) {

            return false;
        }

        if (currentLine > 0 || index == 1 || index == 2) {
            codeLines.set(currentLine, codeLines.get(currentLine).substring(0, codeLines.get(currentLine).length() - 3));
        }
        codeLines.set(index, codeLines.get(index) + " <-");
        currentLine = index;
        return true;
    }

    public int getCurrentLine () {
        return currentLine;
    }

    public void setTitle (String title) {
        this.codeTitle = title;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Code code = new Code();
        code.codeTitle = this.codeTitle;
        code.codeLines = this.codeLines;
        return code;
    }

    @Override
    public String toString() {
        String out = null;
        out = codeTitle + ": \n";
        for (String line : codeLines) {
            out += line;
            out += "\n";
            if (Objects.equals(line.substring(line.length() - 1), "{")) {
                out += "\t";
            }
        }
        return out;
    }
}
