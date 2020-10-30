import http from '@/http-commons';

class FieldService {
  // BASIC
  static getAll() {
    return http.get('/fields');
  }

  static getById(id) {
    return http.get(`/fields/${id}`);
  }

  static create(data) {
    return http.post('/fields', data);
  }

  static update(id, data) {
    return http.put(`/fields/${id}`, data);
  }

  // GET CHILD OBJECTS
  static getWells(id) {
    return http.get(`/fields/${id}/wells`);
  }

  static getCoordinates(id) {
    return http.get(`/fields/${id}/coordinates`);
  }

  static getInclinometry(id) {
    return http.get(`/fields/${id}/inclinometry`);
  }

  static getMer(id) {
    return http.get(`/fields/${id}/mer`);
  }

  static getRates(id) {
    return http.get(`/fields/${id}/rates`);
  }

  // DELETE CHILD OBJECTS
  static deleteWells(id) {
    return http.delete(`/fields/${id}/wells`);
  }

  static deleteCoordinates(id) {
    return http.delete(`/fields/${id}/coordinates`);
  }

  static deleteInclinometry(id) {
    return http.delete(`/fields/${id}/inclinometry`);
  }

  static deleteMer(id) {
    return http.delete(`/fields/${id}/mer`);
  }

  static deleteRates(id) {
    return http.delete(`/fields/${id}/rates`);
  }
}

export default FieldService;
