import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import CreateTask from "./CreateTask";
import ShowTasks from "./ShowTasks";

const Routers = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/create-task" element={<CreateTask />}/>
                <Route path="/show-tasks" element={<ShowTasks />}/>
            </Routes>
        </Router>
    )
}

export default Routers;