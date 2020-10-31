import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    fieldId: null,
  },
  mutations: {
    setFieldId(state, id) {
      state.fieldId = id;
    },
  },
  actions: {},
  modules: {},
});
