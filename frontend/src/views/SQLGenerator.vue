<template>
  <b-container fluid="lg">
    <h2 class="text-center my-3">SQL генератор</h2>
    <b-card>
      <b-row class="mb-3">
        <b-col lg="6">
          <label>Исходные данные</label>
          <b-form-textarea
            v-model="input"
            rows="15"
          ></b-form-textarea>
        </b-col>
        <b-col lg="6">
          <label>Результат</label>
          <b-form-textarea
            v-model="output"
            rows="15"
          ></b-form-textarea>
        </b-col>
      </b-row>
      <b-form class="mb-3" inline>
        <label class="mr-2">Таблица:</label>
        <b-form-input v-model="table"
                      class="mr-2"
                      type="text"
        ></b-form-input>
        <b-form-checkbox v-model="drop"
                         class="mr-2">
          Drop
        </b-form-checkbox>
        <b-form-checkbox v-model="create"
                         class="mr-2">
          Create
        </b-form-checkbox>
        <b-form-checkbox v-model="id"
                         class="mr-2">
          Add id
        </b-form-checkbox>
      </b-form>
      <b-form inline>
        <b-button class="mr-3" variant="primary" @click="generate">Генерировать SQL</b-button>
        <b-button class="mr-3" variant="secondary">Копировать</b-button>
        <b-button class="mr-3" variant="danger" @click="clear">Очистить</b-button>
      </b-form>

    </b-card>
  </b-container>
</template>

<script>

import { csv } from '@/data/mock';
import generateSQL from '../util/sql';

export default {
  data() {
    return {
      input: csv,
      output: '',
      table: 'mytable',
      drop: true,
      create: true,
      id: true,
    };
  },
  methods: {
    generate() {
      this.output = generateSQL(
        this.input,
        this.table,
        this.drop,
        this.create,
        this.id,
      );
    },
    clear() {
      this.input = '';
      this.output = '';
    },
    onCopy() {

    },
  },
};

</script>
