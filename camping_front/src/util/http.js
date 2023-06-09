import axios from "axios";

import store from "@/store/index.js";

let instance = axios.create({
  baseURL: "http://localhost:9000/camping",
  // withCredentials: true,
});

instance.interceptors.request.use((config) => {
  config.headers["Authorization"] = store.getters.getAccessToken;
  return config;
});
export default instance;
