import axios from "axios";

const INDEX_URL = "http://localhost:8080/index";
const SHOWTASKS_URL = "http://localhost:8080/show-tasks/page/1";
const CREATETASK_URL = "http://localhost:8080/add-task";
const GETPROJECTS_URL = "http://localhost:8080/create-task";

class TasksService {
    getMainPage() {
        return axios.get(INDEX_URL);
    }
    getShowTasksPage() {
        return axios.get(SHOWTASKS_URL);
    }
    createTask(task) {
        return axios.post(CREATETASK_URL, task);
    }
    getProjects() {
        return axios.get(GETPROJECTS_URL);
    }
}

export default new TasksService();