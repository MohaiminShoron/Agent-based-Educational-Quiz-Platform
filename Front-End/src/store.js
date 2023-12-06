import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      userId: null,
      username: null,
    };
  },
  mutations: {
    setUser(state, { userId, username }) {
      state.userId = userId;
      state.username = username;
    },
    clearUser(state) {
      state.userId = null;
      state.username = null;
    },
  },
  actions: {
    loginUser({ commit }, { userId, username }) {
      commit('setUser', { userId, username });
    },
    logoutUser({ commit }) {
      commit('clearUser');
      localStorage.removeItem('token'); 
    },
  },
  getters: {
    isLoggedIn: state => !!state.userId,
  },
});
