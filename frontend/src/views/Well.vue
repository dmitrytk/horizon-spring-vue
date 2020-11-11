<template>
  <div :v-if="loaded" class="container">
    <h2 class="text-center my-3">Скважина {{ well.name }},
      {{ field.name }} месторождение </h2>
    <b-breadcrumb>
      <b-breadcrumb-item to="/fields">Месторождение</b-breadcrumb-item>
      <b-breadcrumb-item to="/fields/1">{{ field.name }}</b-breadcrumb-item>
      <b-breadcrumb-item v-once active>{{ well.name }}</b-breadcrumb-item>
    </b-breadcrumb>
    <div>
      <b-card no-body>
        <b-tabs card>
          <b-tab active title="Данные">
            <b-card-text>
              <WellForm v-bind:well="well" @sendWell="update"/>
            </b-card-text>
          </b-tab>

          <InclinometryTab :data="inclinometry" inc-type="wells"/>

          <!--          <b-tab title="Инклинометрия">-->
          <!--            <b-card-text>-->
          <!--              <b-table v-if="inclinometry.length>0" ref="table"-->
          <!--                       :fields="tables.inclinometry" :items="inclinometry"-->
          <!--                       head-variant="dark"-->
          <!--                       responsive-->
          <!--                       sticky-header>-->
          <!--              </b-table>-->
          <!--            </b-card-text>-->
          <!--          </b-tab>-->

          <b-tab title="Диаграмы">
            <b-card-text>
              <test-chart v-if="rateLoaded" :data="rates"/>
            </b-card-text>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import WellForm from '@/components/form/WellForm.vue';
import WellService from '@/services/WellService';
import FieldService from '@/services/FieldService';
import tables from '@/data/databaseTables';
import TestChart from '@/components/chart/TestChart';
import InclinometryTab from '@/components/InclinometryTab.vue';

export default {
  name: 'Well',
  components: { TestChart, InclinometryTab, WellForm },
  data() {
    return {
      tables,
      loaded: false,
      incLoaded: false,
      rateLoaded: false,
      well: {},
      field: {},
      inclinometry: [],
      mer: [],
      rates: {},
      bread: [
        { text: 'Месторождения', to: '/fields' },
        { text: 'Месторождение', to: '/fields/1' },
        { text: this.wellName, active: true },
      ],
    };
  },
  mounted() {
    this.loaded = false;
    this.incLoaded = false;
    this.rateLoaded = false;
    this.fetchWell();
    this.fetchField();
    this.fetchInclinometry();
    this.fetchMer();
    this.fetchRates();
  },
  computed: {
    wellName() {
      return this.well.name;
    },

  },
  methods: {
    fetchWell() {
      WellService.getById(this.$route.params.wellId)
        .then((res) => {
          this.well = res.data;
          this.loaded = true;
        });
    },
    fetchField() {
      FieldService.getById(this.$route.params.fieldId)
        .then((res) => {
          this.field = res.data;
        });
    },
    fetchInclinometry() {
      WellService.getInclinometry(this.$route.params.wellId)
        .then((res) => {
          this.inclinometry = res.data;
          console.log(res.data);
          this.incLoaded = true;
        });
    },
    fetchMer() {
      WellService.getMer(this.$route.params.wellId)
        .then((res) => {
          this.mer = res.data;
        });
    },
    fetchRates() {
      WellService.getRates(this.$route.params.wellId)
        .then((res) => {
          this.rates.dates = res.data.map((el) => el.date);
          this.rates.rates = res.data.map((el) => el.rate);
          this.rates.dynamic = res.data.map((el) => el.dynamic);
          this.rates.static = res.data.map((el) => el.static);
          this.rateLoaded = true;
        });
    },

    update(data) {
      WellService.update(this.$route.params.wellId, data)
        .then(() => {
          this.$toasted.show('Данные сохранены');
        });
    },
    sendWell() {
      this.$emit('sendWell', this.well);
    },

  },

};
</script>

<style scoped>

</style>
