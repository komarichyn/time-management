package com.jc.tm;

import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.TaskConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * this main class for launch a whole application
 */
@SpringBootApplication
public class Main  {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}
