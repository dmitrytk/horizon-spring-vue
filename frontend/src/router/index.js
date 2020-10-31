import Vue from 'vue';
import VueRouter from 'vue-router';
import Field from '@/views/Field.vue';
import Well from '@/views/Well.vue';
import Import from '@/views/Import.vue';
import CoordinateConverter from '@/views/CoordinateConverter.vue';
import Sandbox from '@/views/Sandbox.vue';
import Fields from '../views/Fields.vue';
import SQLGenerator from '../views/SQLGenerator.vue';
import Home from '../views/Home.vue';
import FieldCreate from '../views/FieldCreate.vue';

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
    path: '/fields/:fieldId/wells/:wellId',
    name: 'well',
    component: Well,
  },
  {
    path: '/sandbox',
    name: 'sandbox',
    component: Sandbox,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
