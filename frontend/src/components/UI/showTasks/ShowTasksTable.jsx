import React, {useEffect, useState} from "react";
import TasksService from "../../../services/TasksService";

const ShowTasksTable = () => {
  const [tasks, setTasks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  useEffect(() => {
    TasksService.getShowTasksPage().then((res) => {
      setTasks(res.data);
    });
  }, [tasks.length])

  // useEffect(() => {
  //   const result = tasks.filter(task => task.toLowerCase().includes(searchTerm.toLowerCase()));
  //   setSearchResults(result);
  // }, [searchTerm]);

  return (
    <div className="container">
      <input
        type="text"
        placeholder="Search"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {tasks.filter((val) => {
        if(searchTerm === "") {
          console.log("Empty");
          return val;
        } else if (val.name.toLowerCase().includes(searchTerm.toLowerCase())) {
          console.log("Not empty");
          return val;
        }
      }).map((task, key) => {
        return (
          // <div key={key}>
          //   {val.name}
          // </div>
          <table className="table">
            <tbody>
            {tasks.length ? (
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
          )}
            </tbody>
          </table>
        )
      })}
      {/*{<table className="table">
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
      </table>}*/}
    </div>
  )
}

export default ShowTasksTable;