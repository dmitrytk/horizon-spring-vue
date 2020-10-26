import axios from 'axios';

const AXIOS = axios.create({
  baseURL: '/api',
  headers: {
    'Content-type': 'application/json',
  },
  'Access-Control-Allow-Origin': '*',
  timeout: 1000,
});

export default AXIOS;
