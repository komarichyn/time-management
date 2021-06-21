package com.jc.tm;

import com.jc.tm.database.dao.CommentDao;
import com.jc.tm.database.dao.CommentDaoImpl;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.helper.DatabaseHelper;
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

  DatabaseHelper dataHelper = DatabaseHelper.getInstance();

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
  TaskDao getTaskDao(){
    return new TaskDaoImpl(dataHelper);
  }
  @Bean
  CommentDao getCommentDao(){
    return new CommentDaoImpl(dataHelper);
  }
  @Bean
  ITaskService getTaskService(@Autowired TaskDao taskDao, @Autowired CommentDao commentDao){
    return new TaskServiceImpl(taskDao, commentDao);
  }

  @Bean
  TaskConsole getConsole(@Autowired ITaskService taskService){
    return new TaskConsole(taskService);
  }

}
