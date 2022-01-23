import React from "react";
import TasksTableRows from "./TasksTableRows";

const TasksTableTitles = ({tasks, deleteTask}) => {
  return (
    <div className="container">
      {tasks.length ?
        <table className="table">
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Status</th>
            <th scope="col">Progress</th>
            <th scope="col">Due date</th>
            <th scope="col">Priority</th>
            <th scope="col">Projects</th>
            <th scope="col">Delete</th>
          </tr>
          </thead>
          <tbody>
          {tasks.map((task) =>
            <TasksTableRows deleteTask={deleteTask} key={task.id} task={task}/>
          )}
          </tbody>
        </table>
        : <h2>You have no tasks to do</h2>
      }
    </div>
  );
}

export default TasksTableTitles;