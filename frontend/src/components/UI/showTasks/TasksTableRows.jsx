import React from "react";
import {Badge} from "react-bootstrap";
import SetBadge from "../../../scripts/SetBadge.js";

const TasksTableRows = ({task: {id, name, description, created, status, progress, dueDate, priority, projectName}, deleteTask}) => {
  return (
    <tr>
      <td>{name}</td>
      <td>{status}</td>
      <td>
        <div className="progress">
          <div
            className="progress-bar"
            role="progressbar"
            style={{width: progress + "%"}}
          >
            {progress}
          </div>
        </div>
      </td>
      <td>
      {
        dueDate !== null
          ? <td>{dueDate}</td>
          : <td>Not assigned</td>
      }
      </td>
      <td><Badge bg={SetBadge(priority)}>{priority}</Badge></td>
      {projectName !== null
        ? <td>{projectName["name"]}</td>
        : <td></td>
      }
      <td><button className="btn btn-danger" onClick={() => deleteTask(id)}>Delete</button></td>
    </tr>
  )
}

export default TasksTableRows;