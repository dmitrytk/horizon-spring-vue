<template>
  <div class="container" v-if="fieldLoaded">
    <h2 class="text-center my-3">{{ field.name }} месторождение</h2>
    <b-breadcrumb :items="bread"></b-breadcrumb>
    <div>
      <b-card no-body>
        <b-tabs card>

          <!--Form-->
          <b-tab active title="Данные">
            <FieldForm @sendField="saveField" v-bind:field="field"/>
          </b-tab>

          <!--Wells-->
          <b-tab @click="fetchWells" title="Скважины">
            <div>
              <b-table :fields="fields" :items="wells" @click="fetchWells" class="text-dark"
                       ref="table"
                       responsive striped
                       v-if="wells.length>0">

                <template #cell(name)="data">
                  <!-- `data.value` is the value after formatted by the Formatter -->
                  <a :href="link(data.value)" class="font-weight-bold text-dark">{{ data.value
                    }}</a>
                </template>

              </b-table>
            </div>
            <b-button class="mr-3" to="/import" variant="primary">Загрузить</b-button>
            <b-button @click="deleteWells" variant="danger">Удалить</b-button>
          </b-tab>

          <!--Map-->
          <b-tab title="Карта">
            <b-card-text>Карта</b-card-text>
          </b-tab>

        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
  import {AXIOS} from "@/config/http-commons";
  import FieldForm from "@/components/form/FieldForm";
  import tables from "@/data/databaseTables";

  export default {
    name: "Field",
    components: {FieldForm},
    data() {
      return {
        fields: tables.wells,
        fieldLoaded: false,
        wellsLoaded: false,
        mapLoaded: false,
        field: {},
        wells: [],
        items: [
          {age: 40, first_name: 'Dickerson', last_name: 'Macdonald'},
          {age: 21, first_name: 'Larsen', last_name: 'Shaw'},
          {age: 89, first_name: 'Geneva', last_name: 'Wilson'},
          {age: 38, first_name: 'Jami', last_name: 'Carney'}
        ],
        bread: [
          {text: 'Месторождения', to: {name: 'fields'}},
          {text: '', active: true,},
        ]
      };
    },
    created() {
      this.fetchField();
    },

    methods: {
      fetchField() {
        AXIOS.get(`/fields/${this.$route.params.id}`)
          .then(res => {
            this.field = res.data;
            this.bread[1].text = res.data.name;
            this.fieldLoaded = true;
          })
      },
      fetchWells() {
        if (!this.wellsLoaded) {
          AXIOS.get(`/fields/${this.$route.params.id}/wells`)
            .then(res => {
              this.wells = res.data;
              this.wellsLoaded = true;
            })
        }
      },
      saveField(data) {
        AXIOS.put(`/fields/${this.$route.params.id}`, data)
          .then(res => {
            this.$toasted.show('Данные сохранены', {
              position: "top-center",
              duration: 3000
            });
          })
      },
      deleteWells() {
        this.$bvModal.msgBoxConfirm(`Удалить скважины ?`)
          .then(value => {
            if (value) {
              AXIOS.delete(`/fields/${this.$route.params.id}/wells`)
                .then(res => {
                  this.wells = [];
                  this.$refs.table.refresh();
                  this.$toasted.show('Скважины удалены', {
                    position: "top-center",
                    duration: 3000
                  });
                })
                .catch(err => {
                  console.log(err);
                });
            }
          })
          .catch(err => {
            // An error occurred
          })
      },
      mapLoad() {
        if (!this.mapLoaded) console.log("Loading map");
        this.wellsLoaded = true;
      },
      link(data) {
        const wellId = this.wells.find(el => el.name === data).id;
        return `/wells/${wellId}`;
      },
    },
  }
</script>

<style scoped>

</style>