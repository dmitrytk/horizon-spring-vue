<template>
  <b-card-text>
    <!--String-->
    <b-form-group label="Номер" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.name"></b-form-input>
    </b-form-group>
    <b-form-group label="Куст" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.pad"></b-form-input>
    </b-form-group>
    <b-form-group label="Тип" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.type">
      </b-form-input>
    </b-form-group>
    <b-form-group label="Состояние" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.status">
      </b-form-input>
    </b-form-group>
    <!--Numeric-->
    <b-form-group label="Широта" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.lat" type="number">
      </b-form-input>
    </b-form-group>
    <b-form-group label="Долгота" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.lng" type="number">
      </b-form-input>
    </b-form-group>
    <b-form-group label="X" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.x" type="number">
      </b-form-input>
    </b-form-group>
    <b-form-group label="Y" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.y" type="number">
      </b-form-input>
    </b-form-group>
    <b-form-group label="Альтитуда" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.alt" type="number">
      </b-form-input>
    </b-form-group>
    <b-form-group label="Забой" label-cols="4" label-cols-lg="2" label-for="input-default">
      <b-form-input v-model="well.bottom" type="number">
      </b-form-input>
    </b-form-group>
    <b-button variant="primary" @click="sendWell">Сохранить</b-button>
  </b-card-text>
</template>

<script>
export default {
  name: 'WellForm',
  props: {
    well: Object,
  },
  methods: {
    sendWell() {
      this.$emit('sendWell', this.well);
    },
  },
};
</script>

<style scoped>

</style>
