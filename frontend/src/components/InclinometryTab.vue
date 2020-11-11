<template>
  <b-tab title="Инклинометрия">
    <b-table v-if="data.length>0" ref="table"
             :fields="fields" :items="data"
             head-variant="dark"
             responsive
             sticky-header>
    </b-table>
    <h3 v-else>Нет данных</h3>
  </b-tab>
</template>

<script>
import tables from '@/data/databaseTables';

export default {
  name: 'InclinometryTab',
  props: {
    incType: String,
    data: Array,
  },
  data() {
    return {
      fields: this.incType === 'fields'
        ? tables.inclinometry
        : tables.inclinometry.filter((el) => el.key !== 'well'),
    };
  },
};
</script>
