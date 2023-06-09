import Vue from "vue";
import VueRouter from "vue-router";

import CampingView from "@/views/CampingView";
import FreeBoardView from "@/views/FreeBoardView.vue";
import MyPageView from "@/views/MyPageView.vue";
import LoginView from "@/views/LoginView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "main",
    component: LoginView,
  },
  {
    path: "/camping",
    name: "camping",
    component: CampingView,
  },
  {
    path: "/board",
    name: "freeboard",
    component: FreeBoardView,
  },
  {
    path: "/mypage",
    name: "mypage",
    component: MyPageView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
