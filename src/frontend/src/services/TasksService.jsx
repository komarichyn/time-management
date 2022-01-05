import axios from "axios";

const INDEX_URL = 'http://localhost:8080/time-management/';
const GET_ALL_PROJECTS = 'http://localhost:8080/time-management/get-all-projects';



class TasksService {

    getMainPage() {
        return axios.get(INDEX_URL)
    }

    saveTask(task) {
        return axios.post(INDEX_URL, task);
    }

    // quickCreateTask(task) {
    //     return axios.post(INDEX_URL, task);
    // }
    //
    // getTask(id) {
    //     return axios.post(INDEX_URL + '/' + id);
    // }

    getAllProjects() {
        return axios.get(GET_ALL_PROJECTS);
    }

}

export default new TasksService();