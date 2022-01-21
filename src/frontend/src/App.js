import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {
    BrowserRouter as Router,
    Route,
    Routes
} from 'react-router-dom';
import HomePageTable from "./components/HomePageTable";
import MyHeader from "./components/MyHeader";
import CreateTask from "./components/CreateTask";
import CreateProject from "./components/CreateProject";
import Task from "./components/Task";

function App() {
    return (
        <div>
            <Router>
                <MyHeader/>
                <div className="container">
                    <Routes>
                        <Route exact path="/" element={<HomePageTable/>}/>
                        <Route path="/create-task" element={<CreateTask/>}/>
                        <Route path="/create-new-project" element={<CreateProject/>}/>
                        {/*//TODO route show all tasks*/}
                        <Route path="/show-tasks/page/" element={<HomePageTable/>}/>
                        <Route path="/task/:id" element={<Task/>}/>
                    </Routes>

                </div>
            </Router>
        </div>

    );
}

export default App;
