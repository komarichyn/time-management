import React, {useEffect, useState} from "react";
import TasksService from "../../../services/TasksService";

const LastFiveElemetns = () => {
  const [tasks, setTasks] = useState([]);
  useEffect(() => {
    TasksService.getMainPage().then((res) => {
      setTasks(res.data);
    });
  }, []);

  return (
    <div className="container">
      <h3>
        Five tasks -<small className="text-muted"> sorted by due date</small>
      </h3>
      <table className="table">
        <thead>
        <tr>
          <th scope="col">Name</th>
          <th scope="col">Status</th>
          <th scope="col">Created</th>
          <th scope="col">Progress</th>
          <th scope="col">Due date</th>
          <th scope="col">Priority</th>
        </tr>
        </thead>
        <tbody>
        {tasks.map((task) =>
          tasks.length ? (
            <tr key={task.id}>
              <td>{task.name}</td>
              <td>{task.status}</td>
              <td>{task.created}</td>
              <td>
                <div className="progress">
                  <div
                    className="progress-bar"
                    role="progressbar"
                    style={{width: task.progress + "%"}}
                    aria-valuemax="100"
                  >
                    {task.progress}
                  </div>
                </div>
              </td>
              <td>{task.dueDate}</td>
              <td>{task.priority}</td>
            </tr>
          ) : (
            <tr>No</tr>
          )
        )}
        </tbody>
      </table>
    </div>
  );
};

export default LastFiveElemetns;