import axios from 'axios'


export const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});
