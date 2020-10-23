<template>
  <div v-if="fieldLoaded" class="container">
    <h1 v-once class="text-center my-3">{{ field.name }} Field</h1>
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
            <b-card-text>Скважины</b-card-text>
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
      wells: []
    };
  },
  created() {
    this.fetchField();
  },

  methods: {
    wellsLoad() {
      if (!this.wellsLoaded) console.log("Loading wells");
      this.wellsLoaded = true;
    },
    mapLoad() {
      if (!this.mapLoaded) console.log("Loading map");
      this.wellsLoaded = true;
    },
    fetchField() {
      AXIOS.get("/fields/" + this.$route.params.id)
        .then(res => {
          console.log(res)
          this.field = res.data;
          this.fieldLoaded = true;
        })
    },
    fetchWells() {
    },
    saveField() {
      AXIOS.post(`/fields/${this.$route.params.id}`, this.field)
        .then(res => {
          console.log("Field saved");
        })
    }
  }

}
</script>

<style scoped>

</style>
