import Vue from "vue";
import Vuex from "vuex";
import createVuexPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userName: "",
    userIdx: -1,
    accessToken: "",
    refreshToken: "",
  },
  getters: {
    getUserName: function (state) {
      return state.userName;
    },
    getUserIdx: function (state) {
      return state.userIdx;
    },
    getAccessToken: function (state) {
      return state.accessToken;
    },
    getRefreshToekn: function (state) {
      return state.refreshToken;
    },
  },
  mutations: {
    SET_USER_NAME: (state, username) => {
      state.userName = username;
    },
    SET_USER_IDX: (state, userIdx) => {
      state.userIdx = userIdx;
    },
    SET_ACCESS_TOKEN: (state, accessToken) => {
      state.accessToken = accessToken;
    },
    SET_REFRESH_TOKEN: (state, refreshToken) => {
      state.refreshToken = refreshToken;
    },
  },
  plugins: [
    createVuexPersistedState({
      storage: sessionStorage,
      key: "token",
      paths: ["accessToken", "refreshToken", "userName", "userIdx"],
    }),
  ],
});
