<template>
  <div v-if="loaded" class="container">
    <h2 v-once class="text-center my-3">Скважина {{ well.name }}</h2>
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
          <InclinometryTab v-bind:id="this.$route.params.wellId" inc-type="wells"/>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import WellForm from '@/components/form/WellForm.vue';
import WellService from '@/services/WellService';
import FieldService from '@/services/FieldService';
import IncService from '@/services/IncService';
import tables from '@/data/databaseTables';
import InclinometryTab from '@/components/InclinometryTab.vue';

export default {
  name: 'Well',
  components: { InclinometryTab, WellForm },
  data() {
    return {
      tables,
      loaded: false,
      incLoaded: false,
      well: {},
      field: {},
      inclinometry: [],
      bread: [
        { text: 'Месторождения', to: '/fields' },
        { text: 'Месторождение', to: '/fields/1' },
        { text: this.wellName, active: true },
      ],
    };
  },
  created() {
    this.fetchWell();
    this.fetchField();
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
      if (!this.incLoaded) {
        IncService.getWellInclinometry(this.$route.params.wellId)
          .then((res) => {
            this.inclinometry = res.data;
            this.incLoaded = true;
          });
      }
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
