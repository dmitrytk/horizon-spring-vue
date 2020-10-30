<template>
  <div class="container">
    <h2 class="my-3 text-center">Новое месторождение</h2>
    <b-card>
      <FieldForm v-bind:field="field" @sendField="save"/>
    </b-card>
  </div>
</template>

<script>
import FieldService from '@/services/FieldService';
import FieldForm from '../components/form/FieldForm.vue';

export default {
  name: 'FieldCreate',
  components: { FieldForm },
  data() {
    return {
      field: {},
    };
  },
  methods: {
    save(data) {
      FieldService.create(data)
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
