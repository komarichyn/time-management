import React, {useState, useEffect} from "react";
import TasksService from "../../../services/TasksService";
import DataTable from "./DataTable";

const ShowTasksTable2 = () => {
  const [tasks, setTasks] = useState([]);
  const [q, setQ] = useState([]);

  useEffect(() => {
    TasksService.getShowTasksPage().then((res) => {
      setTasks(res.data);
    });
  }, [])

  const search1 = (rows) => {
    return rows.filter((row) => row.name.toLowerCase().indexOf(q) > -1);
  }

  return (
    <div>
      <div>
        <input type="text" value={q} onChange={(e) => setQ(e.target.value)}/>
      </div>
      <div>
        <DataTable tasks={search1(tasks)}/>
      </div>
    </div>
  )
}

export default ShowTasksTable2;