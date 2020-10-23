<template>
  <div v-if="fieldLoaded" class="container">
    <h1 v-once class="text-center my-3">{{ field.name }} месторождение</h1>
    <div>
      <b-card no-body>
        <b-tabs card>
          <b-tab active title="Данные">
            <b-card-text>
              <b-form-group label="Название" label-cols="4" label-cols-lg="2" label-for="input-default">
                <b-form-input id="input-name" v-model="field.name" :placeholder="field.name"></b-form-input>
              </b-form-group>
              <b-form-group label="Тип" label-cols="4" label-cols-lg="2" label-for="input-default">
                <b-form-input id="input-type" v-model="field.type" :placeholder="field.type"></b-form-input>
              </b-form-group>
              <b-form-group label="Расположение" label-cols="4" label-cols-lg="2" label-for="input-default">
                <b-form-input id="input-location" v-model="field.location" :placeholder="field.location"></b-form-input>
              </b-form-group>
              <b-button variant="primary" @click="saveField">Сохранить</b-button>
            </b-card-text>
          </b-tab>


          <b-tab title="Скважины" @click="wellsLoad">
            <div>
              <b-table v-if="wellsLoaded" :items="wells" dark responsive striped @click="fetchWells">

                <template #cell(name)="data">
                  <!-- `data.value` is the value after formatted by the Formatter -->
                  <a :href="link(data.value)" class="text-info">{{ data.value }}</a>
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

export default {
  name: "Field",
  data() {
    return {
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
    wellsLoad() {
      if (!this.wellsLoaded) console.log("Loading wells");
      this.fetchWells();
      this.wellsLoaded = true;
    },
    mapLoad() {
      if (!this.mapLoaded) console.log("Loading map");
      this.wellsLoaded = true;
    },
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
    saveField() {
      AXIOS.post(`/fields/${this.$route.params.id}`, this.field)
        .then(res => {
          console.log("Field saved");
        })
    },
    link(data) {
      const wellId = this.wells.find(el => el.name === data).id;
      return `/wells/${wellId}`;
    },
  },
  computed: {}

}
</script>

<style scoped>

</style>
