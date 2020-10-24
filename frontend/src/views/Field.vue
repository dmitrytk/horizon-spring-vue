<template>
  <div v-if="fieldLoaded" class="container">
    <h1 v-once class="text-center my-3">{{ field.name }} месторождение</h1>
    <div>
      <b-card no-body>
        <b-tabs card>
          <b-tab active title="Данные">
            <FieldForm v-bind:field="field" @passField="saveField"/>
          </b-tab>
          <b-tab title="Скважины" @click="fetchWells">
            <div>
              <b-table v-if="wellsLoaded" :fields="fields" :items="wells" class="text-dark" responsive="xl" striped
                       @click="fetchWells">

                <template #cell(name)="data">
                  <!-- `data.value` is the value after formatted by the Formatter -->
                  <a :href="link(data.value)" class="font-weight-bold text-dark">{{ data.value }}</a>
                </template>

              </b-table>
            </div>
          </b-tab>


          <b-tab title="Карта">
            <b-card-text>Карта</b-card-text>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import {AXIOS} from "@/config/http-commons";
import FieldForm from "@/components/form/FieldForm";
import tables from "@/data/databaseTables";

export default {
  name: "Field",
  components: {FieldForm},
  data() {
    return {
      fields: tables.wells,
      fieldLoaded: false,
      wellsLoaded: false,
      mapLoaded: false,
      field: {},
      wells: [],
      items: [
        {age: 40, first_name: 'Dickerson', last_name: 'Macdonald'},
        {age: 21, first_name: 'Larsen', last_name: 'Shaw'},
        {age: 89, first_name: 'Geneva', last_name: 'Wilson'},
        {age: 38, first_name: 'Jami', last_name: 'Carney'}
      ]
    };
  },
  created() {
    this.fetchField();
  },

  methods: {
    fetchField() {
      AXIOS.get(`/fields/${this.$route.params.id}`)
        .then(res => {
          this.field = res.data;
          this.fieldLoaded = true;
        })
    },
    fetchWells() {
      if (!this.wellsLoaded) {
        AXIOS.get(`/fields/${this.$route.params.id}/wells`)
          .then(res => {
            this.wells = res.data;
            this.wellsLoaded = true;
          })
      }
    },
    saveField(data) {
      AXIOS.put(`/fields/${this.$route.params.id}`, data)
        .then(res => {
          this.$toasted.show('Данные сохранены', {
            position: "top-center",
            duration: 1000
          });
        })
    },
    mapLoad() {
      if (!this.mapLoaded) console.log("Loading map");
      this.wellsLoaded = true;
    },
    link(data) {
      const wellId = this.wells.find(el => el.name === data).id;
      return `/wells/${wellId}`;
    },
  },
}
</script>

<style scoped>

</style>
