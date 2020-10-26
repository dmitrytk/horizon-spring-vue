<template>
  <div class="container">
    <h2 class="my-3 text-center">Новое месторождение</h2>
    <b-card>
      <FieldForm @sendField="saveField" v-bind:field="field"/>
    </b-card>
  </div>
</template>

<script>
  import FieldForm from "../components/form/FieldForm";
  import {AXIOS} from "../config/http-commons";

  export default {
    name: "FieldCreate",
    components: {FieldForm},
    data() {
      return {
        field: {}
      }
    },
    methods: {
      saveField(data) {
        AXIOS.post(`/fields`, data)
          .then(res => {
            this.$toasted.show('Данные сохранены', {
              position: "top-center",
              duration: 2000
            });
            this.$router.push('/fields');
          })
      },
    }
  }
</script>

<style scoped>

</style>
