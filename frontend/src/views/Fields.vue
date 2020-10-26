<template>
  <div class="container">
    <h2 class="my-3 text-center">Месторождения</h2>
    <b-breadcrumb :items="bread"></b-breadcrumb>
    <b-list-group v-if="loaded">
      <b-list-group-item v-for="field in fields" :key="field.id" :to="{ name: 'field', params: { id: field.id }}">{{
          field.name
        }}

      </b-list-group-item>
    </b-list-group>
  </div>
</template>


<script>
import {AXIOS} from "@/config/http-commons";

export default {
  name: 'Fields',
  data() {
    return {
      loaded: false,
      fields: {},
      bread: [
        {text: 'Месторождения', active: true},
      ]
    };
  },
  created() {
    this.fetchFields();
  },

  methods: {
    fetchFields() {
      AXIOS.get("/fields")
        .then(res => {
          console.log(res.data)
          this.fields = res.data;
          this.loaded = true;
        })
    }
  }
};
</script>

<style scoped>

</style>
