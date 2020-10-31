import http from '@/http-commons';

class BatchService {
  // BASIC
  static import(data) {
    return http.post(`/batch/${data.type}`, data);
  }
}

export default BatchService;
