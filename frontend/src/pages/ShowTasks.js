import React from 'react';
import NavagationBar from '../components/UI/header/NavigationBar';
import ShowTasksTable from '../components/UI/showTasks/ShowTasksTable';

const ShowTasks = () => {
  return (
    <div>
      <NavagationBar/>
      <ShowTasksTable/>
    </div>
  )
}

export default ShowTasks;