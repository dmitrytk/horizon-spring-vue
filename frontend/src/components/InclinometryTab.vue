<template>
  <b-tab title="Инклинометрия">
    <div>
      <b-table ref="table"
               :fields="fields" :items="data"
               head-variant="dark"
               responsive
               sticky-header>
      </b-table>
    </div>
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
