package com.jc.tm;

import com.jc.tm.db.dao.jpa.CommentDao;
import com.jc.tm.db.dao.jpa.TaskDao;
import com.jc.tm.service.ITaskService;
import com.jc.tm.service.TaskServiceImpl;
import com.jc.tm.ui.TaskConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * this main class for launch a whole application
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main implements CommandLineRunner {


  @Autowired
  private TaskConsole console;

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }


  @Override
  public void run(String... args) throws Exception {
    console.launch();
  }

  @Bean
  TaskConsole getConsole(@Autowired ITaskService taskService){
    return new TaskConsole(taskService);
  }

}
