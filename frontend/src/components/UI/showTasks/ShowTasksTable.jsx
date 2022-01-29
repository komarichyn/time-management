import React, {useEffect, useState} from "react";
import TasksService from "../../../services/TasksService";
import TasksTableTitles from "./TasksTableTitles";

const ShowTasksTable = ({search}) => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    showTasks();
  }, [])

  const showTasks = () => {
    TasksService.getShowTasksPage().then((res) => {
      setTasks(res.data);
    });
  }

  const deleteTask = (taskId) => {
    TasksService.deleteTask(taskId).then((res) => {
      showTasks();
    })
  }

  const setStatus = (id, newStatus) => {
    TasksService.changeStatus(id, newStatus).then((res) => {
      showTasks();
    })
  }

  const searchQ = (rows) => {
    return rows.filter((row) => row.name.toLowerCase().indexOf(search.toLowerCase()) > -1);
  }

  return (
    <div>
      <div>
        <TasksTableTitles deleteTask={deleteTask} tasks={searchQ(tasks)} setStatus={setStatus}/>
      </div>
    </div>
  )
}

export default ShowTasksTable;