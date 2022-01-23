import React, {useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import CreateTask from "./pages/CreateTask";
import ShowTasks from "./pages/ShowTasks";
import CreateProject from "./pages/CreateProject";
import NavigationBar from "./components/UI/header/NavigationBar";

const Routers = () => {
  const [searchWord, setSearchWord] = useState("");

  return (
    <Router>
      <NavigationBar search={searchWord} onChange={setSearchWord}/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/create-task" element={<CreateTask/>}/>
        <Route path="/create-project" element={<CreateProject/>}/>
        <Route path="/show-tasks" element={<ShowTasks search={searchWord}/>}/>
      </Routes>
    </Router>
  )
}

export default Routers;