import React from "react";

const DataTable = ({tasks}) => {
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
            <tr key={task.id}>
              <td>{task.name}</td>
              <td>{task.status}</td>
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
              <td>{task.project}</td>
            </tr>
          )}
          </tbody>
        </table>
        : <h2>You have no tasks to do</h2>
      }
    </div>
  );
}

export default DataTable;