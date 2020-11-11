<template>
  <div :v-if="loaded" class="container">
    <h2 class="text-center my-3">Скважина {{ well.well }},
      {{ field.name }} месторождение </h2>
    <b-breadcrumb>
      <b-breadcrumb-item to="/fields">Месторождение</b-breadcrumb-item>
      <b-breadcrumb-item to="/fields/1">{{ field.name }}</b-breadcrumb-item>
      <b-breadcrumb-item active>{{ well.well }}</b-breadcrumb-item>
    </b-breadcrumb>
    <div>
      <b-card no-body>
        <b-tabs card>

          <!--Данные-->
          <b-tab active title="Данные">
            <b-card-text>
              <WellForm :well="well" @sendWell="update"/>
            </b-card-text>
          </b-tab>

          <!--Инклинометрия-->
          <InclinometryTab :data="inclinometry" inc-type="wells"/>

          <!--Диграммы-->
          <b-tab title="Добыча" @click="chartVisited=true">
            <b-card-text>
              <div v-if="chartVisited">
                <well-rate-chart v-if="rates.length>0" id="ratesChart" :data="rates" class="mb-3"/>
                <well-mer-chart v-if="mer.length>0" id="merChart" :data="mer"/>
                <h3 v-else>Нет данных</h3>
              </div>
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
import InclinometryTab from '@/components/InclinometryTab.vue';
import WellMerChart from '@/components/chart/WellMerChart';
import WellRateChart from '@/components/chart/WellRateChart';

export default {
  name: 'Well',
  components: {
    InclinometryTab, WellMerChart, WellRateChart, WellForm,
  },
  data() {
    return {
      tables,
      loaded: false,
      chartVisited: false,
      well: {},
      field: {},
      inclinometry: [],
      mer: [],
      rates: [],
      bread: [
        { text: 'Месторождения', to: '/fields' },
        { text: 'Месторождение', to: '/fields/1' },
        { text: this.wellName, active: true },
      ],
    };
  },
  mounted() {
    this.loaded = false;
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
    rLoad() {
      return Array.isArray(this.rates.dates) && this.rates.dates.length > 0;
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
          this.rates = res.data;
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
