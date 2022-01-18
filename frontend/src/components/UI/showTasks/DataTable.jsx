import React from "react";

const DataTable = ({tasks}) => {
  // const columns = tasks[0] && Object.keys(tasks[0]);
  return (
    <div className="container">
      <table className="table">
        <thead>
          {/*<tr>{tasks[0] && columns.map((heading) => <th>{heading}</th>)}</tr>*/}
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
        {/*{tasks.map((row) =>*/}
        {/*  <tr>*/}
        {/*    {columns.map((column) =>*/}
        {/*    <td>{row[column]}</td>)}*/}
        {/*  </tr>*/}
        {/*)}*/}
        {tasks.map((task) =>
          tasks.length ? (
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
          ) : (
            <tr>No</tr>
          )
        )}
        </tbody>
      </table>
    </div>
  );
}

export default DataTable;