import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import SQLGenerator from '../views/SQLGenerator.vue';
import Fields from "../views/Fields";
import Field from "@/views/Field";
import Well from "@/views/Well";
import Import from "@/views/Import";
import CoordinateConverter from "@/views/CoordinateConverter";
import FieldCreate from "../views/FieldCreate";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/import',
    name: 'import',
    component: Import,
  },
  {
    path: '/csv_to_sql',
    name: 'sqlGenerator',
    component: SQLGenerator,
  },
  {
    path: '/converter',
    name: 'converter',
    component: CoordinateConverter,
  },
  {
    path: '/fields',
    name: 'fields',
    component: Fields,
  },
  {
    path: '/fields/new',
    name: 'new_field',
    component: FieldCreate,
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
