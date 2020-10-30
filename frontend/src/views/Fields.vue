<template>
  <div class="container">
    <h2 class="my-3 text-center">Месторождения</h2>
    <b-breadcrumb :items="bread"></b-breadcrumb>
    <b-list-group v-if="loaded">
      <b-list-group-item v-for="field in fields" :key="field.id"
                         :to="{ name: 'field', params: { id: field.id }}">{{
          field.name
        }}

      </b-list-group-item>
    </b-list-group>
    <b-button class="mt-3" to="/fields/new" variant="primary">
      Добавить
    </b-button>
  </div>
</template>

<script>
import FieldService from '@/services/FieldService';

export default {
  name: 'Fields',
  data() {
    return {
      loaded: false,
      fields: {},
      bread: [
        { text: 'Месторождения', active: true },
      ],
    };
  },
  created() {
    this.getFields();
  },

  methods: {
    getFields() {
      FieldService.getAll()
        .then((res) => {
          this.fields = res.data;
          this.loaded = true;
        });
    },
  },
};
</script>

<style scoped>

</style>
