<template>
  <div v-if="fieldLoaded" class="container">
    <h2 class="text-center my-3">{{ field.name }} месторождение</h2>
    <b-breadcrumb :items="bread"></b-breadcrumb>
    <div>
      <b-card no-body>
        <b-tabs card>

          <!--Form-->
          <b-tab active title="Данные">
            <FieldForm v-bind:field="field" @sendField="update"/>
          </b-tab>

          <!--Wells-->
          <b-tab title="Скважины">
            <div>
              <b-table v-if="wells.length>0" ref="table" :fields="fields" :items="wells"
                       head-variant="dark"
                       responsive
              >
                <template #cell(well)="data">
                  <!-- `data.value` is the value after formatted by the Formatter -->
                  <router-link :to="link(data.value)" class="font-weight-bold text-dark">{{
                      data.value
                    }}
                  </router-link>
                </template>
              </b-table>
            </div>
            <b-button class="mr-3" to="/import" variant="primary">Загрузить</b-button>
            <b-button v-if="wells.length>0" variant="danger" @click="deleteWells">Удалить</b-button>
          </b-tab>

          <InclinometryTab v-if="inclinometry.length>0" :data="inclinometry" inc-type="fields"/>

          <!--Map-->
          <MapTab/>

        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import FieldService from '@/services/FieldService';
import FieldForm from '@/components/form/FieldForm.vue';
import tables from '@/data/databaseTables';
import InclinometryTab from '@/components/InclinometryTab.vue';
import MapTab from '@/components/MapTab.vue';

export default {
  name: 'Field',
  components: { MapTab, InclinometryTab, FieldForm },
  data() {
    return {
      fields: tables.wells,
      fieldLoaded: false,
      wellsLoaded: false,
      incLoaded: false,
      mapLoaded: false,
      field: {},
      wells: [],
      inclinometry: [],
      items: [
        { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
        { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
        { age: 89, first_name: 'Geneva', last_name: 'Wilson' },
        { age: 38, first_name: 'Jami', last_name: 'Carney' },
      ],
      bread: [
        { text: 'Месторождения', to: { name: 'fields' } },
        { text: '', active: true },
      ],
    };
  },

  mounted() {
    this.getField();
    this.getWells();
  },

  methods: {
    getField() {
      FieldService.getById(this.$route.params.id)
        .then((res) => {
          this.field = res.data;
          this.$store.commit('setFieldId', res.data.id);
          this.bread[1].text = res.data.name;
          this.fieldLoaded = true;
        });
    },
    getWells() {
      FieldService.getWells(this.$route.params.id)
        .then((res) => {
          this.wells = res.data;
          this.wellsLoaded = true;
        });
    },
    getInclinometry() {
      FieldService.getInclinometry(this.$route.params.id)
        .then((res) => {
          this.inclinometry = res.data;
          this.incLoaded = true;
        });
    },
    update(data) {
      FieldService.update(this.$route.params.id, data)
        .then(() => {
          this.$toasted.show('Данные сохранены');
        });
    },
    deleteWells() {
      this.$bvModal.msgBoxConfirm('Удалить скважины ?')
        .then((value) => {
          if (value) {
            FieldService.deleteWells(this.$route.params.id)
              .then(() => {
                this.wells = [];
                this.$refs.table.refresh();
                this.$toasted.show('Скважины удалены', {
                  position: 'top-center',
                  duration: 3000,
                });
              })
              .catch((err) => {
                console.log(err);
              });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    mapLoad() {
      if (!this.mapLoaded) console.log('Loading map');
      this.wellsLoaded = true;
    },
    link(data) {
      const wellId = this.wells.find((el) => el.well === data).id;
      return `/fields/${this.$route.params.id}/wells/${wellId}`;
    },
  },
};
</script>

<style scoped>

</style>
