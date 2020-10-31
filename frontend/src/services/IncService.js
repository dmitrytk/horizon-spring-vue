import http from '@/http-commons';

class IncService {
  // BASIC
  static getFieldInclinometry(fieldId) {
    return http.get(`/fields/${fieldId}/inclinometry`);
  }

  static getWellInclinometry(wellId) {
    return http.get(`/wells/${wellId}/inclinometry`);
  }
}

export default IncService;
