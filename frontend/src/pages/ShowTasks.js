import React from 'react';
import ShowTasksTable from '../components/UI/showTasks/ShowTasksTable';

const ShowTasks = ({search}) => {
  return (
    <div>
      <ShowTasksTable search={search}/>
    </div>
  )
}

export default ShowTasks;