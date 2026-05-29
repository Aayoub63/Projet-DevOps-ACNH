import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user: {
      id: null,
      username: null,
    }
  },
  mutations: {

    login(state, user) {
      state.user = {
        id: user.id,
        username: user.username
      };

      localStorage.token = user.token;
    },

    logout(state) {
      state.user = {
        id: null,
        username: null
      };

      //Nettoyage
      localStorage.clear();
  },
  }
});

export default store