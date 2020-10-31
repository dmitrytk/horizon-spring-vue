<template>
  <b-tab title="Инклинометрия" @click="getInclinometry">
    <div>
      <b-table v-if="inc.length>0" ref="table"
               :fields="tables.inclinometry" :items="inc"
               head-variant="dark"
               responsive
               sticky-header>
      </b-table>
    </div>
  </b-tab>
</template>

<script>
import tables from '@/data/databaseTables';
import FieldService from '@/services/FieldService';
import WellService from '@/services/WellService';

export default {
  name: 'InclinometryTab',
  props: {
    incType: String,
    id: String,
  },
  data() {
    return {
      tables,
      inc: [],
      loaded: false,
    };
  },
  methods: {
    getInclinometry() {
      if (!this.loaded) {
        if (this.incType === 'fields') {
          FieldService.getInclinometry(this.id)
            .then((res) => {
              this.inc = res.data;
              this.loaded = true;
            });
        } else if (this.incType === 'wells') {
          WellService.getInclinometry(this.id)
            .then((res) => {
              this.inc = res.data;
              this.loaded = true;
            });
        }
      }
    },
  },
};
</script>

<style scoped>

</style>
