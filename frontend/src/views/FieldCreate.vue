<template>
  <div class="container">
    <h2 class="my-3 text-center">Новое месторождение</h2>
    <b-card>
      <FieldForm v-bind:field="field" @sendField="saveField"/>
    </b-card>
  </div>
</template>

<script>
import FieldForm from '../components/form/FieldForm.vue';
import AXIOS from '../http-commons';

export default {
  name: 'FieldCreate',
  components: { FieldForm },
  data() {
    return {
      field: {},
    };
  },
  methods: {
    saveField(data) {
      AXIOS.post('/fields', data)
        .then(() => {
          this.$toasted.show('Данные сохранены', {
            position: 'top-center',
            duration: 2000,
          });
          this.$router.push('/fields');
        });
    },
  },
};
</script>

<style scoped>

</style>
