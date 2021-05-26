package com.jc.tm;

import com.jc.tm.ui.TaskConsole;
import com.jc.tm.ui.console.MyConsole;

/**
 * this main class for launch a whole application
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        ConsoleApplication consoleApplication = new ConsoleApplication();
//        consoleApplication.start();

        TaskConsole console = new TaskConsole();
        console.start();


    }
}
