import React from 'react';
import NavagationBar from '../components/UI/header/NavigationBar';
import CreateTaskForm from '../components/UI/createTask/CreateTaskForm';

const CreateTask = () => {
    return (
        <div>
            <NavagationBar/>
            <CreateTaskForm/>
        </div>
    )
}

export default CreateTask;