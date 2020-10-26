import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    field: '',
  },
  mutations: {
    setField(state, name) {
      state.field = name;
    },
  },
  actions: {},
  modules: {},
});
