import axios from "axios";

const INDEX_URL = 'http://localhost:8080/time-management/';
const GET_ALL_PROJECTS = 'http://localhost:8080/time-management/get-all-projects';
const CREATE_PROJECT_URL = 'http://localhost:8080/time-management/add-project';
const GET_TASK_URL = 'http://localhost:8080/time-management/task';
const DELETE_TASK_URL = 'http://localhost:8080/time-management/delete-task';


class TasksService {

    getMainPage() {
        return axios.get(INDEX_URL)
    }

    saveTask(task) {
        return axios.post(INDEX_URL, task);
    }

    getTask(id) {
        return axios.get(GET_TASK_URL + '/' + id);
    }

    deleteTask(id) {
        return axios.delete(DELETE_TASK_URL + '/' + id);
    }

    getAllProjects() {
        return axios.get(GET_ALL_PROJECTS);
    }

    createProject(project) {
        return axios.post(CREATE_PROJECT_URL, project);
    }
}

export default new TasksService();