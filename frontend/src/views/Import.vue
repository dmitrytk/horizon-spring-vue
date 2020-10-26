<template>
  <div class="container">
    <h2 class="text-center my-3">Импорт данных</h2>
    <b-card v-if="loaded">
      <b-form inline>
        <label class="mr-sm-2">Месторождение</label>
        <b-form-select
          :options="fields"
          :value="fields[0]"
          class="mb-2 mr-sm-2 mb-sm-0"
        ></b-form-select>

        <label class="mr-sm-2">Тип данных</label>
        <b-form-select
          :options="['One', 'Two', 'Three']"
          :value="null"
          class="mb-2 mr-sm-2 mb-sm-0"
        ></b-form-select>
      </b-form>
    </b-card>
  </div>
</template>

<script>
  import {AXIOS} from "../config/http-commons";

  export default {
    name: "Import",
    data() {
      return {
        loaded: false,
        fields: [],
      }
    },
    created() {
      this.fetchFields();
    },
    methods: {
      fetchFields() {
        AXIOS.get("/fields")
          .then(res => {
            console.log(res.data)
            this.fields = res.data.map(field => field.name);
            this.loaded = true;
          })
      }
    }
  }
</script>

<style scoped>

</style>
