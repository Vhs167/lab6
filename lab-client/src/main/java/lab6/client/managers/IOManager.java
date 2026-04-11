package lab6.client.managers;


import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


/**
 * Класс управляющий вводом
 */
public class IOManager {

    private final Deque<Scanner> scanners = new ArrayDeque<>();
    private final Deque<String> scripts = new ArrayDeque<>();
    private boolean scriptMode = false;


    public IOManager() {
        scanners.push(new Scanner(System.in));
    }

    public String readLine() {
        if (scanners.isEmpty()) return null;

        Scanner current = scanners.peek();

        while (!current.hasNextLine()) {
            scanners.pop();
            if (scanners.isEmpty()) return null;
            current = scanners.peek();
        }

        return current.nextLine();
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public void printError(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m");
    }

    public void setFileInput(String fileName) throws FileNotFoundException {
        scripts.push(fileName);
        scanners.push(new Scanner(new File(fileName)));
    }

    public boolean isScriptMode() {
        return scriptMode;
    }

    public Deque<String> getScripts() {
        return scripts;
    }

    public void setCurrentScript(String fileName) {
        this.scripts.push(fileName);
    }
}
