import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import SQLGenerator from '../views/SQLGenerator.vue';
import Fields from "../views/Fields";
import Field from "@/views/Field";
import Well from "@/views/Well";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
  {
    path: '/csv_to_sql',
    name: 'sqlGenerator',
    component: SQLGenerator,
  },
  {
    path: '/fields',
    name: 'fields',
    component: Fields,
  },
  {
    path: '/fields/:id',
    name: 'field',
    component: Field,
  },
  {
    path: '/wells/:id',
    name: 'well',
    component: Well,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
