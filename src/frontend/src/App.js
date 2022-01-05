import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Tasks from "./components/Tasks";
import MyHeader from "./components/MyHeader";
import CreateTask from "./components/CreateTask";
import Task from "./components/Task";

function App() {
    return (
        <div>
            <Router>
                <MyHeader/>
                <div className="container">
                    <Routes>
                        <Route exact path="/" element={<Tasks/>}></Route>
                        <Route path="/create-task" element={<CreateTask/>}></Route>
                        <Route path="/show-tasks/page/1" element={<CreateTask/>}></Route>
                        <Route path="/task/:id" element={<Task/>}></Route>
                    </Routes>

                </div>
            </Router>
        </div>

    );
}

export default App;
