<template>
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
    <b-button variant="primary" @click="sendWell">Сохранить</b-button>
  </b-card-text>
</template>

<script>
export default {
  name: "WellForm",
  props: {
    field: Object
  },
  methods: {
    sendWell() {
      this.$emit("sendWell", this.field);
    }
  }
}
</script>

<style scoped>

</style>
