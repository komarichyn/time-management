import React, {useEffect, useState} from "react";
import TasksService from "../../../services/TasksService";
import DataTable from "./DataTable";

const ShowTasksTable = ({search}) => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    TasksService.getShowTasksPage().then((res) => {
      setTasks(res.data);
    });
  }, [])

  const searchQ = (rows) => {
    return rows.filter((row) => row.name.toLowerCase().indexOf(search.toLowerCase()) > -1);
  }

  return (
    <div>
      <div>
        <DataTable tasks={searchQ(tasks)}/>
      </div>
    </div>
  )
}

export default ShowTasksTable;