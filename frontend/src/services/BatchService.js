import http from '@/http-commons';

class BatchService {
  static import(data) {
    return http.post(`/batch/${data.type}`, data);
  }

  static export(data) {
    return data;
  }
}

export default BatchService;
