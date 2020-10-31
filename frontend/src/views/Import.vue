<template>
  <div class="container">
    <h2 class="text-center my-3">Импорт данных</h2>
    <b-card v-if="loaded">
      <b-form inline>
        <label class="mr-sm-2">Месторождение</label>
        <b-form-select
          v-model="selectedField"
          :options="fields"
          class="mb-2 mr-sm-2 mb-sm-0"
        ></b-form-select>

        <label class="mr-sm-2">Тип данных</label>
        <b-form-select
          v-model="selectedDataType"
          :options="dataTypes"
          class="mb-2 mr-sm-2 mb-sm-0"
        ></b-form-select>
      </b-form>
      <b-form-textarea
        v-if="isVisible"
        v-model="content"
        class="mt-3"
        max-rows="10"
        placeholder="Введите данные"
        rows="10"
      ></b-form-textarea>
      <b-table v-if="!isVisible" ref="table" :items="data"
               class="text-dark mt-3"
               head-variant="dark" responsive sticky-header striped>
      </b-table>

      <b-button class="mt-3 mr-3" variant="info" @click="parse">Parse</b-button>
      <b-button class="mt-3 mr-3" variant="primary" @click="load">Load</b-button>
      <b-button class="mt-3 mr-3" variant="danger" @click="clear">Clear</b-button>
    </b-card>
  </div>
</template>

<script>
import api from '@/services/FieldService';
import getTableData from '@/util/table';

export default {
  name: 'Import',
  data() {
    return {
      loaded: false,
      fields: [],
      dataTypes: [
        { value: 'wells', text: 'Скважины' },
        { value: 'rates', text: 'Режимы' },
        { value: 'mer', text: 'МЭР' },
        { value: 'zones', text: 'Пласты' },
        { value: 'inclinometry', text: 'Инклинометрия' },
      ],
      selectedField: this.$store.state.field,
      selectedDataType: 'wells',
      content: '',
      data: [
        { well: '12R', alt: 45.6 },
        { well: '99r', alt: 45.6 },
      ],
      isVisible: true,
    };
  },
  created() {
    this.fetchFields();
  },
  methods: {
    fetchFields() {
      api.getAll()
        .then((res) => {
          this.fields = res.data.map((field) => field.name);
          this.loaded = true;
        });
    },
    parse() {
      this.data = getTableData(this.content);
      this.isVisible = false;
    },
    load() {
    },
    clear() {
      this.content = '';
      this.data = [];
      this.isVisible = true;
    },
    setField() {
      this.$store.commit('setField', 'New field');
    },
  },
};
</script>

<style scoped>

</style>
